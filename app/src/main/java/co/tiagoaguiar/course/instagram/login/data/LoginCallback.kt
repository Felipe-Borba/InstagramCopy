package co.tiagoaguiar.course.instagram.login.data

import co.tiagoaguiar.course.instagram.common.model.UserAuth

interface LoginCallback {
    fun onSuccess()
    fun onFailure(message:String)
    fun onComplete()
}