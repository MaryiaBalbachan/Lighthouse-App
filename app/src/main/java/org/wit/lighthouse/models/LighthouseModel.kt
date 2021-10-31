package org.wit.lighthouse.models
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LighthouseModel(var title: String = "",
                           var description: String = ""): Parcelable
