package co.tiagoaguiar.course.instagram.main.view

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import co.tiagoaguiar.course.instagram.R
import co.tiagoaguiar.course.instagram.camera.view.CameraFragment
import co.tiagoaguiar.course.instagram.common.extension.replaceFragment
import co.tiagoaguiar.course.instagram.databinding.ActivityMainBinding
import co.tiagoaguiar.course.instagram.home.view.HomeFragment
import co.tiagoaguiar.course.instagram.profile.view.ProfileFragment
import co.tiagoaguiar.course.instagram.register.view.RegisterPhotoFragment
import co.tiagoaguiar.course.instagram.search.view.SearchFragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var homeFragment: Fragment
    private lateinit var searchFragment: Fragment
    private lateinit var cameraFragment: Fragment
    private lateinit var profileFragment: Fragment
    private lateinit var currentFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
            window.statusBarColor = ContextCompat.getColor(this, R.color.gray)
        }

        setSupportActionBar(binding.mainToolbar)

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_insta_camera)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        homeFragment = HomeFragment()
        searchFragment = SearchFragment()
        cameraFragment = CameraFragment()
        profileFragment = ProfileFragment()

        currentFragment = homeFragment
        supportFragmentManager.beginTransaction().apply {
            add(R.id.main_fragment, searchFragment, "3").hide(searchFragment)
            add(R.id.main_fragment, cameraFragment, "2").hide(cameraFragment)
            add(R.id.main_fragment, profileFragment, "1").hide(profileFragment)
            add(R.id.main_fragment, homeFragment, "0")
            commit()
        }

        binding.mainBottomNav.setOnNavigationItemSelectedListener(this)
        binding.mainBottomNav.selectedItemId = R.id.menu_bottom_home
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_bottom_home -> {
                if (currentFragment == homeFragment) return false
                supportFragmentManager.beginTransaction().hide(currentFragment).show(homeFragment).commit()
                currentFragment = homeFragment
                scrollToolbarEnabled(false)
            }

            R.id.menu_bottom_search -> {
                if (currentFragment == searchFragment) return false
                supportFragmentManager.beginTransaction().hide(currentFragment).show(searchFragment).commit()
                currentFragment = searchFragment
                scrollToolbarEnabled(false)
            }

            R.id.menu_bottom_add -> {
                if (currentFragment == cameraFragment) return false
                supportFragmentManager.beginTransaction().hide(currentFragment).show(cameraFragment).commit()
                currentFragment = cameraFragment
                scrollToolbarEnabled(false)
            }

            R.id.menu_bottom_profile -> {
                if (currentFragment == profileFragment) return false
                supportFragmentManager.beginTransaction().hide(currentFragment).show(profileFragment).commit()
                currentFragment = profileFragment
                scrollToolbarEnabled(true)
            }
        }

//        currentFragment?.let {
//            replaceFragment(R.id.main_fragment, it)
//        }

        return true
    }

    private fun scrollToolbarEnabled(enable: Boolean) {
        val params = binding.mainToolbar.layoutParams as AppBarLayout.LayoutParams
        val coordinatorParams = binding.mainAppbar.layoutParams as CoordinatorLayout.LayoutParams
        if (enable) {
            params.scrollFlags =
                AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
            coordinatorParams.behavior = AppBarLayout.Behavior()
        } else {
            params.scrollFlags = 0
            coordinatorParams.behavior = null
        }
        binding.mainAppbar.layoutParams = coordinatorParams
    }
}