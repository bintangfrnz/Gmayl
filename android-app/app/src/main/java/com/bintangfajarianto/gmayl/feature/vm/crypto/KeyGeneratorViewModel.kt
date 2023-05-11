package com.bintangfajarianto.gmayl.feature.vm.crypto

import com.bintangfajarianto.gmayl.base.BaseViewModel
import com.bintangfajarianto.gmayl.core.RouteDestination
import com.bintangfajarianto.gmayl.core.RouteDestinationHandler
import com.bintangfajarianto.gmayl.data.model.general.DataCondition
import com.bintangfajarianto.gmayl.data.model.general.DataMessageCondition
import com.bintangfajarianto.gmayl.data.model.general.DialogData
import com.bintangfajarianto.gmayl.domain.usecase.crypto.DeleteKeyPairUseCase
import com.bintangfajarianto.gmayl.domain.usecase.crypto.GenerateKeyPairUseCase
import com.bintangfajarianto.gmayl.domain.usecase.crypto.GetKeyPairUseCase
import com.bintangfajarianto.gmayl.domain.usecase.crypto.GetKeyPairUseCaseActionResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

class KeyGeneratorViewModel(
    coroutineScope: CoroutineScope,
    routeDestinationHandler: RouteDestinationHandler,
    private val deleteKeyPairUseCase: DeleteKeyPairUseCase,
    private val generateKeyPairUseCase: GenerateKeyPairUseCase,
    private val getKeyPairUseCase: GetKeyPairUseCase,
) : BaseViewModel<KeyGeneratorViewState, KeyGeneratorAction, KeyGeneratorActionResult>(
    initialState = KeyGeneratorViewState(),
    coroutineScope = coroutineScope,
    routeDestinationHandler = routeDestinationHandler,
) {
    override suspend fun handleOnAction(action: KeyGeneratorAction): KeyGeneratorActionResult =
        when (action) {
            KeyGeneratorAction.InitKeyPair -> {
                callEffect {
                    delay(500L)
                    getKeyPair()
                }
                KeyGeneratorActionResult.ShowFetching
            }
            KeyGeneratorAction.OnClickGenerateNewKey -> {
                callMultipleEffects(
                    listOf(
                        {
                            delay(500L)
                            generateNewKey()
                        },
                        {
                            delay(1000L)
                            KeyGeneratorActionResult.SetDataCondition(
                                dataMsgCondition = DataMessageCondition(
                                    dataCondition = DataCondition.Success,
                                    message = "New key successfully generated",
                                ),
                            )
                        }
                    )
                )
                KeyGeneratorActionResult.ShowLoading
            }
            KeyGeneratorAction.OnClickResetKey -> KeyGeneratorActionResult.ShowDeleteDialog
            KeyGeneratorAction.OnDismissDialog -> KeyGeneratorActionResult.DismissDialog
            KeyGeneratorAction.OnDismissSnackBar -> KeyGeneratorActionResult.SetDataCondition(null)
            is KeyGeneratorAction.OnReceiveDataCondition -> KeyGeneratorActionResult.SetDataCondition(action.dataMsgCondition)
            KeyGeneratorAction.OnSubmitResetKey -> {
                callEffect {
                    delay(500L)
                    KeyGeneratorActionResult.SetDataCondition(
                        dataMsgCondition = DataMessageCondition(
                            dataCondition = DataCondition.Failure,
                            message = "Old key successfully removed",
                        ),
                    )
                }
                deleteKeyPair()
            }
        }

    private suspend fun getKeyPair(): KeyGeneratorActionResult =
        when (val result = getKeyPairUseCase.invoke(Unit)) {
            is GetKeyPairUseCaseActionResult.Success -> KeyGeneratorActionResult.SetKeyPair(result.key)
            GetKeyPairUseCaseActionResult.Failed -> KeyGeneratorActionResult.DoNothing
        }

    private suspend fun generateNewKey(): KeyGeneratorActionResult =
        KeyGeneratorActionResult.SetKeyPair(generateKeyPairUseCase.invoke(Unit).key)

    private suspend fun deleteKeyPair(): KeyGeneratorActionResult {
        deleteKeyPairUseCase.invoke(Unit)
        return KeyGeneratorActionResult.SetKeyPair(null to null)
    }

    override suspend fun reducer(
        oldState: KeyGeneratorViewState,
        actionResult: KeyGeneratorActionResult,
    ): KeyGeneratorViewState = KeyGeneratorViewState(
        isInitKeyPair = true,
        privateKey = oldState.privateKeyReducer(actionResult),
        publicKey = oldState.publicKeyReducer(actionResult),
        navigateTo = shouldNavigateTo(actionResult),
        fetching = fetchingReducer(actionResult),
        loading = loadingReducer(actionResult),
        dialogData = dialogDataReducer(actionResult),
        dataMsgCondition = dataConditionReducer(actionResult),
    )

    private fun KeyGeneratorViewState.privateKeyReducer(actionResult: KeyGeneratorActionResult): String? =
        when (actionResult) {
            is KeyGeneratorActionResult.SetKeyPair -> actionResult.key.first
            else -> privateKey
        }

    private fun KeyGeneratorViewState.publicKeyReducer(actionResult: KeyGeneratorActionResult): String? =
        when (actionResult) {
            is KeyGeneratorActionResult.SetKeyPair -> actionResult.key.second
            else -> publicKey
        }

    private fun fetchingReducer(actionResult: KeyGeneratorActionResult): Boolean =
        when (actionResult) {
            KeyGeneratorActionResult.ShowFetching -> true
            else -> false
        }

    private fun loadingReducer(actionResult: KeyGeneratorActionResult): Boolean =
        when (actionResult) {
            KeyGeneratorActionResult.ShowLoading -> true
            else -> false
        }

    private fun dialogDataReducer(actionResult: KeyGeneratorActionResult): DialogData? =
        when (actionResult) {
            KeyGeneratorActionResult.ShowDeleteDialog -> DialogData(
                titleText = "Delete Key?",
                descriptionText = "You can immediately generate a new key without deleting the old key first",
                positiveButtonText = "Proceed",
                negativeButtonText = "Cancel",
            )
            else -> null
        }

    private fun dataConditionReducer(actionResult: KeyGeneratorActionResult): DataMessageCondition? {
        return when (actionResult) {
            is KeyGeneratorActionResult.SetDataCondition -> actionResult.dataMsgCondition
            else -> null
        }
    }

    override fun navigateToReducer(actionResult: KeyGeneratorActionResult): RouteDestination? = null
}
