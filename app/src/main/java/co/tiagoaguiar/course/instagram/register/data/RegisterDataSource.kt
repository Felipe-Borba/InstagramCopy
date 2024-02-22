package co.tiagoaguiar.course.instagram.register.data

import android.net.Uri

interface RegisterDataSource {
    fun validate(email:String, callback: RegisterCallback)

    fun create(email:String, name:String, password:String, callback: RegisterCallback)

    fun updateUser(photoUri: Uri, callback: RegisterCallback)
}