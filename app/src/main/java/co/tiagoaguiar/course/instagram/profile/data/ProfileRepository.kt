package co.tiagoaguiar.course.instagram.profile.data

import co.tiagoaguiar.course.instagram.common.base.RequestCallback
import co.tiagoaguiar.course.instagram.common.model.Post
import co.tiagoaguiar.course.instagram.common.model.UserAuth

//TODO posso deixar esse repository sendo uma implementação do DataSource?
class ProfileRepository(private val dataSource: ProfileDataSource) : ProfileDataSource {
    override fun fetchUserProfile(userUUID: String, callback: RequestCallback<UserAuth>) {
        dataSource.fetchUserProfile(userUUID, callback)
    }

    override fun fetchUserPosts(userUUID: String, callback: RequestCallback<List<Post>>) {
        dataSource.fetchUserPosts(userUUID, callback)
    }
}