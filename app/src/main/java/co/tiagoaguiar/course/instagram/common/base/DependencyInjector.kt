package co.tiagoaguiar.course.instagram.common.base

import co.tiagoaguiar.course.instagram.home.data.FeedMemoryCache
import co.tiagoaguiar.course.instagram.home.data.HomeDataSourceFactory
import co.tiagoaguiar.course.instagram.home.data.HomeRepository
import co.tiagoaguiar.course.instagram.login.data.FakeDataSource
import co.tiagoaguiar.course.instagram.login.data.LoginRepository
import co.tiagoaguiar.course.instagram.profile.data.PostMemoryCache
import co.tiagoaguiar.course.instagram.profile.data.ProfileDataSourceFactory
import co.tiagoaguiar.course.instagram.profile.data.ProfileFakeRemoteDataSource
import co.tiagoaguiar.course.instagram.profile.data.ProfileMemoryCache
import co.tiagoaguiar.course.instagram.profile.data.ProfileRepository
import co.tiagoaguiar.course.instagram.register.data.FakeRegisterDataSource
import co.tiagoaguiar.course.instagram.register.data.RegisterRepository
import co.tiagoaguiar.course.instagram.splash.data.FakeLocalDataSource
import co.tiagoaguiar.course.instagram.splash.data.SplashRepository

object DependencyInjector {
    fun loginRepository(): LoginRepository {
        return LoginRepository(FakeDataSource())
    }

    fun registerEmailRepository(): RegisterRepository {
        return RegisterRepository(FakeRegisterDataSource())
    }

    fun splashRepository(): SplashRepository {
        return SplashRepository(FakeLocalDataSource())
    }

    fun profileRepository(): ProfileRepository {
        return ProfileRepository(
            ProfileDataSourceFactory(
                ProfileMemoryCache,
                PostMemoryCache
            )
        )
    }

    fun homeRepository(): HomeRepository {
        return HomeRepository(
            HomeDataSourceFactory(
                FeedMemoryCache
            )
        )
    }
}