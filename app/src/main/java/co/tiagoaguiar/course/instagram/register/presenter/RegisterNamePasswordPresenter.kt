package co.tiagoaguiar.course.instagram.register.presenter

import co.tiagoaguiar.course.instagram.R
import co.tiagoaguiar.course.instagram.register.RegisterNamePassword
import co.tiagoaguiar.course.instagram.register.data.RegisterCallback
import co.tiagoaguiar.course.instagram.register.data.RegisterRepository

class RegisterNamePasswordPresenter(
    private var view: RegisterNamePassword.View?,
    private val repository: RegisterRepository
) : RegisterNamePassword.Presenter {
    override fun create(email: String, name: String, password: String, confirm: String) {
        val isNameValid = name.length > 3
        val isValidPassword = password.length >= 8
        val isConfirmValid = password == confirm

        if (isNameValid) {
            view?.displayNameFailure(null)
        } else {
            view?.displayNameFailure(R.string.invalid_name)
        }

        if (isValidPassword) {
            view?.displayPasswordFailure(null)
        } else {
            view?.displayPasswordFailure(R.string.invalid_password)
        }

        if (isConfirmValid) {
            view?.displayConfirmFailure(null)
        } else {
            view?.displayConfirmFailure(R.string.password_not_equal)
        }

        if (isNameValid && isValidPassword && isConfirmValid) {
            view?.showProgress(true)
            repository.create(email, name, password, object : RegisterCallback {
                override fun onSuccess() {
                    view?.onCreateSuccess(name)
                }

                override fun onFailure(message: String) {
                    view?.onCreateFailure(message)
                }

                override fun onComplete() {
                    view?.showProgress(false)
                }
            })
        }
    }


    override fun onDestroy() {
        view = null
    }
}