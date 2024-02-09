package co.tiagoaguiar.course.instagram.register.view

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import co.tiagoaguiar.course.instagram.R
import co.tiagoaguiar.course.instagram.common.base.DependencyInjector
import co.tiagoaguiar.course.instagram.common.util.TxtWatcher
import co.tiagoaguiar.course.instagram.databinding.FragmentRegisterEmailBinding
import co.tiagoaguiar.course.instagram.register.RegisterEmail
import co.tiagoaguiar.course.instagram.register.presenter.RegisterEmailPresenter

class RegisterEmailFragment : Fragment(R.layout.fragment_register_email), RegisterEmail.View {

    private var binding: FragmentRegisterEmailBinding? = null

    override lateinit var presenter: RegisterEmail.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterEmailBinding.bind(view)

        presenter = RegisterEmailPresenter(this, DependencyInjector.registerEmailRepository())

        binding?.let {
            with(it) {
                loginTxtRegister.setOnClickListener {
                    activity?.finish()
                }

                registerEditEmail.addTextChangedListener(TxtWatcher {
                    registerBtmNext.isEnabled = registerEditEmail.text.toString().isNotEmpty()
                    displayEmailFailure(null)
                })

                registerBtmNext.setOnClickListener {
                    presenter.create(registerEditEmail.text.toString())
                }
            }
        }
    }

    override fun displayEmailFailure(emailError: Int?) {
        binding?.registerEditEmailInput?.error = emailError?.let { getString(it) }
    }

    override fun showProgress(enabled: Boolean) {
        binding?.registerBtmNext?.showProgress(enabled)
    }

    override fun onEmailFailure(message: String) {
        binding?.registerEditEmailInput?.error = message
    }

    override fun goToNameAndPasswordScreen(email: String) {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        binding = null
        presenter.onDestroy()
        super.onDestroy()
    }
}
