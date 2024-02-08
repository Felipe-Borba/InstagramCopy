package co.tiagoaguiar.course.instagram.login.presentation

import android.util.Patterns
import co.tiagoaguiar.course.instagram.R
import co.tiagoaguiar.course.instagram.common.model.UserAuth
import co.tiagoaguiar.course.instagram.login.Login
import co.tiagoaguiar.course.instagram.login.data.LoginCallback
import co.tiagoaguiar.course.instagram.login.data.LoginRepository

class LoginPresenter(
    private var view: Login.View?,
    private val repository: LoginRepository
) : Login.Presenter {
    override fun login(email: String, password: String) {
        val isValidEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isValidPassword = password.length <= 8
        val isAllValid = isValidPassword && isValidEmail

        if (!isValidEmail) {
            view?.displayEmailFailure(R.string.invalid_email)
        } else {
            view?.displayPasswordFailure(null)
        }

        if (!isValidPassword) {
            view?.displayPasswordFailure(R.string.invalid_password)
        } else {
            view?.displayPasswordFailure(null)
        }

        if (isAllValid) {
            view?.showProgress(true)
            repository.login(email, password, object : LoginCallback {
                override fun onSuccess(userAuth: UserAuth) {
                    view?.onUserAuthenticated()
                }

                override fun onFailure(message: String) {
                    view?.onUserUnauthorized(message)
                }

                override fun onComplete() {
                    view?.showProgress(false);
                }
            })
        }
    }

    override fun onDestroy() {
        view = null
    }
}