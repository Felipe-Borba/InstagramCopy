package co.tiagoaguiar.course.instagram.login.view

import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import co.tiagoaguiar.course.instagram.R
import co.tiagoaguiar.course.instagram.common.base.DependencyInjector
import co.tiagoaguiar.course.instagram.common.util.TxtWatcher
import co.tiagoaguiar.course.instagram.databinding.ActivityLoginBinding
import co.tiagoaguiar.course.instagram.login.Login
import co.tiagoaguiar.course.instagram.login.presentation.LoginPresenter
import co.tiagoaguiar.course.instagram.main.view.MainActivity
import co.tiagoaguiar.course.instagram.register.view.RegisterActivity

class LoginActivity : AppCompatActivity(), Login.View {

    private lateinit var binding: ActivityLoginBinding
    override lateinit var presenter: Login.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        presenter = LoginPresenter(this, DependencyInjector.loginRepository())
        setContentView(binding.root)

        with(binding) {
            when (resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    binding.loginImgLogo.imageTintList = ColorStateList.valueOf(Color.WHITE)
                }

                Configuration.UI_MODE_NIGHT_NO -> {}
            }

            loginEditEmail.addTextChangedListener(watcher)
            loginEditEmail.addTextChangedListener(TxtWatcher {
                displayEmailFailure(null)
            })

            loginEditPassword.addTextChangedListener(watcher)
            loginEditPassword.addTextChangedListener(TxtWatcher {
                displayPasswordFailure(null)
            })

            loginBtnEnter.setOnClickListener {
                presenter.login(loginEditEmail.text.toString(), loginEditPassword.text.toString())
            }

            loginTxtRegister.setOnClickListener {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private val watcher = TxtWatcher {
        binding.loginBtnEnter.isEnabled = binding.loginEditEmail.text.toString().isNotEmpty()
                && binding.loginEditPassword.text.toString().isNotEmpty()
    }

    override fun showProgress(enabled: Boolean) {
        binding.loginBtnEnter.showProgress(enabled)
    }

    override fun displayEmailFailure(emailError: Int?) {
        binding.loginEditEmailInput.error = emailError?.let { getString(it) }
    }

    override fun displayPasswordFailure(passwordError: Int?) {
        binding.loginEditPasswordInput.error = passwordError?.let { getString(it) }
    }

    override fun onUserAuthenticated() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun onUserUnauthorized(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}