package co.tiagoaguiar.course.instagram.home

import co.tiagoaguiar.course.instagram.common.base.BasePresenter
import co.tiagoaguiar.course.instagram.common.base.BaseView

interface Home {

    interface Presenter : BasePresenter {
    }

    interface View : BaseView<Presenter> {
    }
}