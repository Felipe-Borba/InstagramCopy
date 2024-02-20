package co.tiagoaguiar.course.instagram.register

import androidx.annotation.StringRes
import co.tiagoaguiar.course.instagram.common.base.BasePresenter
import co.tiagoaguiar.course.instagram.common.base.BaseView

interface RegisterNamePassword {
    interface View : BaseView<Presenter> {
        fun showProgress(enabled: Boolean)
        fun displayNameFailure(@StringRes error: Int?)
        fun displayPasswordFailure(@StringRes error: Int?)
        fun displayConfirmFailure(@StringRes error: Int?)
        fun onCreateFailure(message: String)
        fun onCreateSuccess(name: String)
    }

    interface Presenter : BasePresenter {
        fun create(email: String, name: String, password: String, confirm: String)
    }
}
