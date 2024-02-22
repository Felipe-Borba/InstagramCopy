package co.tiagoaguiar.course.instagram.splash.data

class SplashRepository(
    private val datasource: SplashDataSource
) {
    fun session(callback: SplashCallback) {
        datasource.session(callback)
    }
}