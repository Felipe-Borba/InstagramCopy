package co.tiagoaguiar.course.instagram.register

import androidx.annotation.StringRes
import co.tiagoaguiar.course.instagram.common.base.BasePresenter
import co.tiagoaguiar.course.instagram.common.base.BaseView

interface RegisterEmail {
    interface View : BaseView<Presenter> {
        fun displayEmailFailure(@StringRes emailError: Int?)
        fun showProgress(enabled: Boolean)
        fun onEmailFailure(message: String)
        fun goToNameAndPasswordScreen(email: String)

    }

    interface Presenter : BasePresenter {
        fun create(email: String)
    }
}