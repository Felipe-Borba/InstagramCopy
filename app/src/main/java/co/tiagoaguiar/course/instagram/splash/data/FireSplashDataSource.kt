package co.tiagoaguiar.course.instagram.splash.data

import co.tiagoaguiar.course.instagram.common.model.Database
import com.google.firebase.auth.FirebaseAuth

class FireSplashDataSource : SplashDataSource {
    override fun session(callback: SplashCallback) {
        if (FirebaseAuth.getInstance().uid !== null) {
            callback.onSuccess()
        } else {
            callback.onFailure()
        }
    }
}