package co.tiagoaguiar.course.instagram.register.data

import android.net.Uri

class RegisterRepository(private val dataSource: RegisterDataSource) {
    fun validate(email: String, callback: RegisterCallback) {
        dataSource.validate(email, callback)
    }

    fun create(email: String, name: String, password: String, callback: RegisterCallback) {
         dataSource.create(email, name, password, callback)
    }

    fun updateUser(photoUri: Uri, callback: RegisterCallback) {
        dataSource.updateUser(photoUri, callback)
    }
}