package com.bintangfajarianto.gmayl.base

import androidx.lifecycle.ViewModel
import com.bintangfajarianto.gmayl.core.RouteDestination
import com.bintangfajarianto.gmayl.core.RouteDestinationHandler
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Result of action that will pass to reducer
interface ActionResult

// UI State
interface ViewState

// User interaction from UI
interface Action

abstract class BaseViewModel<VS : ViewState, A : Action, AR : ActionResult>(
    initialState: VS,
    private val coroutineScope: CoroutineScope,
    private val routeDestinationHandler: RouteDestinationHandler? = null,
) : ViewModel() {
    private val _stateFlow = MutableStateFlow(initialState)
    val stateFlow = _stateFlow.asStateFlow()

    private val _actionFlow: MutableSharedFlow<A> = MutableSharedFlow()

    init {
        coroutineScope.launch {
            _actionFlow.collect { action ->
                Napier.i("Action called: $action", tag = this::class.simpleName)
                val newActionResult = handleOnAction(action)
                handleActionResult(newActionResult)
            }
        }
    }

    fun onAction(action: A) {
        coroutineScope.launch {
            _actionFlow.emit(action)
        }
    }

    fun shouldNavigateTo(actionResult: AR): RouteDestination? {
        val destination = navigateToReducer(actionResult)
        routeDestinationHandler?.sendDestination(destination)

        return destination
    }

    protected abstract suspend fun handleOnAction(action: A): AR
    protected abstract suspend fun reducer(oldState: VS, actionResult: AR): VS
    protected abstract fun navigateToReducer(actionResult: AR): RouteDestination?

    protected suspend fun callEffect(callSuspend: suspend CoroutineScope.() -> AR): Job {
        val effectJob = coroutineScope.launch {
            val newActionResult = callSuspend()
            Napier.d(
                "Effect Call Job status for $newActionResult: ${coroutineContext[Job]?.isCancelled}",
                tag = this::class.simpleName,
            )
            if (coroutineContext[Job]?.isCancelled != true) {
                handleActionResult(newActionResult)
            }
        }

        effectJob.invokeOnCompletion { exception ->
            Napier.d(
                "Effect Call Completed with exception: $exception",
                tag = this::class.simpleName,
            )
        }

        return effectJob
    }

    private suspend fun handleActionResult(newActionResult: AR) {
        Napier.i("Action Result: $newActionResult", tag = this::class.simpleName)
        val newState = reducer(_stateFlow.value, newActionResult)
        Napier.i("New state callEffect: $newState", tag = this::class.simpleName)
        _stateFlow.value = newState
    }
}
