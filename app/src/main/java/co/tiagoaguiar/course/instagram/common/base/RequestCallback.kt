package co.tiagoaguiar.course.instagram.common.base

interface RequestCallback<D> {
    fun onSuccess(data: D)
    fun onFailure(message:String)
    fun onComplete()
}