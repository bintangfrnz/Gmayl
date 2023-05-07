package com.bintangfajarianto.gmayl.data.model.general

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataMessageCondition(
    val dataCondition: DataCondition,
    val message: String,
) : Parcelable
