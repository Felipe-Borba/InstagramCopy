package co.tiagoaguiar.course.instagram.register.data

class RegisterRepository(private val dataSource: RegisterDataSource) {
    fun validate(email: String, callback: RegisterCallback) {
        dataSource.validate(email, callback)
    }

    fun create(email: String, name: String, password: String, callback: RegisterCallback) {
         dataSource.create(email, name, password, callback)
    }
}