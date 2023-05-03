package com.bintangfajarianto.gmayl.data.model.general

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DialogData(
    val imageType: DialogImageType = DialogImageType.DEFAULT,
    val titleText: String = "",
    val descriptionText: String = "",
    val positiveButtonText: String = "",
    val negativeButtonText: String = "",
) : Parcelable
