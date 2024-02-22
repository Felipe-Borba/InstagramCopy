package co.tiagoaguiar.course.instagram.common.model

import android.net.Uri
import java.util.UUID

data class Photo(
    val userUUID: String,
    val uri: Uri,
)
