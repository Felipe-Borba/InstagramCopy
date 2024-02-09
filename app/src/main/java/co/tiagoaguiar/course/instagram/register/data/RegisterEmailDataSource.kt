package co.tiagoaguiar.course.instagram.register.data

interface RegisterEmailDataSource {
    fun validate(email:String, callback: RegisterEmailCallback)
}