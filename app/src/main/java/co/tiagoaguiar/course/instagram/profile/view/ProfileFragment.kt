package co.tiagoaguiar.course.instagram.profile.view

import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import co.tiagoaguiar.course.instagram.R
import co.tiagoaguiar.course.instagram.common.base.BaseFragment
import co.tiagoaguiar.course.instagram.common.base.DependencyInjector
import co.tiagoaguiar.course.instagram.common.model.Post
import co.tiagoaguiar.course.instagram.common.model.UserAuth
import co.tiagoaguiar.course.instagram.databinding.FragmentProfileBinding
import co.tiagoaguiar.course.instagram.profile.Profile
import co.tiagoaguiar.course.instagram.profile.presenter.ProfilePresenter
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener

class ProfileFragment : BaseFragment<FragmentProfileBinding, Profile.Presenter>(
    R.layout.fragment_profile, FragmentProfileBinding::bind
), Profile.View, OnNavigationItemSelectedListener {

    override lateinit var presenter: Profile.Presenter

    private val adapter = PostAdapter()
    private var uuid: String? = null

    override fun setupPresenter() {
        presenter = ProfilePresenter(this, DependencyInjector.profileRepository())
    }

    override fun setupViews() {
        binding?.profileRv?.layoutManager = GridLayoutManager(requireContext(), 3)
        binding?.profileRv?.adapter = adapter
        binding?.profileNavTabs?.setOnNavigationItemSelectedListener(this)

        binding?.profileBtnEditProfile?.setOnClickListener {
            when (it.tag) {
                true -> {
                    binding?.profileBtnEditProfile?.text = getString(R.string.follow)
                    binding?.profileBtnEditProfile?.tag = false
                    presenter.followUser(uuid, false)
                }

                false -> {
                    binding?.profileBtnEditProfile?.text = getString(R.string.unfollow)
                    binding?.profileBtnEditProfile?.tag = true
                    presenter.followUser(uuid, true)
                }
            }
        }

        uuid = arguments?.getString(KEY_USER_ID)
        presenter.fetchUserProfile(uuid)
    }

    override fun getMenu(): Int {
        return R.menu.menu_profile
    }

    override fun showProgress(enabled: Boolean) {
        binding?.profileProgress?.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    override fun displayUserProfile(user: Pair<UserAuth, Boolean?>) {
        val (userAuth, following) = user

        binding?.profileTxtPostCount?.text = userAuth.postCount.toString()
        binding?.profileTxtFollowingCount?.text = userAuth.followingCount.toString()
        binding?.profileTxtFollowersCount?.text = userAuth.followersCount.toString()
        binding?.profileTxtUsername?.text = userAuth.name
        binding?.profileTxtBio?.text = "TODO"
        binding?.profileImgIcon?.setImageURI(userAuth.photo)

        binding?.profileBtnEditProfile?.text = when (following) {
            null -> getString(R.string.edit_profile)
            true -> getString(R.string.unfollow)
            false -> getString(R.string.follow)
        }

        binding?.profileBtnEditProfile?.tag = following

        presenter.fetchUserPosts(uuid)
    }

    override fun displayRequestFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun displayEmptyPosts() {
        binding?.profileTxtEmpty?.visibility = View.VISIBLE
        binding?.profileRv?.visibility = View.GONE
    }

    override fun displayFullPosts(posts: List<Post>) {
        binding?.profileTxtEmpty?.visibility = View.GONE
        binding?.profileRv?.visibility = View.VISIBLE
        adapter.items = posts
        adapter.notifyDataSetChanged()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_profile_grid -> {
                binding?.profileRv?.layoutManager = GridLayoutManager(requireContext(), 3)
            }

            R.id.menu_profile_list -> {
                binding?.profileRv?.layoutManager = LinearLayoutManager(requireContext())
            }
        }
        return true
    }

    companion object {
        const val KEY_USER_ID = "key_user_id"
    }
}
