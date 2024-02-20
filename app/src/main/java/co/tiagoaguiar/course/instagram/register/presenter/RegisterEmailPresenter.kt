package co.tiagoaguiar.course.instagram.register.presenter

import android.util.Patterns
import co.tiagoaguiar.course.instagram.R
import co.tiagoaguiar.course.instagram.register.RegisterEmail
import co.tiagoaguiar.course.instagram.register.data.RegisterCallback
import co.tiagoaguiar.course.instagram.register.data.RegisterRepository

class RegisterEmailPresenter(
    private var view: RegisterEmail.View?,
    private val repository: RegisterRepository
) : RegisterEmail.Presenter {
    override fun create(email: String) {
        val isValidEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches()

        if (!isValidEmail) {
            view?.displayEmailFailure(R.string.invalid_email)
        } else {
            view?.showProgress(true)
            repository.validate(email, object : RegisterCallback {
                override fun onSuccess() {
                    view?.goToNameAndPasswordScreen(email)
                }

                override fun onFailure(message: String) {
                    view?.onEmailFailure(message)
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