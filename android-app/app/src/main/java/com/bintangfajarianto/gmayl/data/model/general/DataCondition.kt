package com.bintangfajarianto.gmayl.data.model.general

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class DataCondition : Parcelable {
    @Parcelize
    object Success : DataCondition()

    @Parcelize
    object Failure : DataCondition()
}
