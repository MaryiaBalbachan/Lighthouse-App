package org.wit.lighthouse.models
import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LighthouseModel(var id: Long = 0,
                           var title: String = "",
                           var description: String = "",
                           var image: Uri = Uri.EMPTY): Parcelable
