package co.tiagoaguiar.course.instagram.register.data

class RegisterEmailRepository(private val dataSource: RegisterEmailDataSource) {
    fun validate(email: String, callback: RegisterEmailCallback) {
        dataSource.validate(email, callback)
    }
}