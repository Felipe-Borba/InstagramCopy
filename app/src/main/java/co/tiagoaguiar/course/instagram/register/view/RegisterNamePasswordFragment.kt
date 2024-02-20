package co.tiagoaguiar.course.instagram.register.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import co.tiagoaguiar.course.instagram.R
import co.tiagoaguiar.course.instagram.common.base.BasePresenter
import co.tiagoaguiar.course.instagram.common.base.DependencyInjector
import co.tiagoaguiar.course.instagram.common.util.TxtWatcher
import co.tiagoaguiar.course.instagram.databinding.FragmentRegisterNamePasswordBinding
import co.tiagoaguiar.course.instagram.register.RegisterNamePassword
import co.tiagoaguiar.course.instagram.register.presenter.RegisterNamePasswordPresenter

class RegisterNamePasswordFragment : Fragment(R.layout.fragment_register_name_password), RegisterNamePassword.View {

    private var binding: FragmentRegisterNamePasswordBinding? = null

    override lateinit var presenter: RegisterNamePassword.Presenter
    private var fragmentAttachListener: FragmentAttachListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterNamePasswordBinding.bind(view)
        presenter = RegisterNamePasswordPresenter(this, DependencyInjector.registerEmailRepository())

        val email = arguments?.getString(KEY_EMAIL) ?: throw IllegalStateException("$KEY_EMAIL is required")

        binding?.let {
            with(it) {
                registerEditName.addTextChangedListener(watcher)
                registerEditPassword.addTextChangedListener(watcher)
                registerEditConfirm.addTextChangedListener(watcher)

                registerEditName.addTextChangedListener(TxtWatcher {
                    displayNameFailure(null)
                })
                registerEditPassword.addTextChangedListener(TxtWatcher {
                    displayPasswordFailure(null)
                })
                registerEditConfirm.addTextChangedListener(TxtWatcher {
                    displayConfirmFailure(null)
                })

                registerNameBtmNext.setOnClickListener {
                    presenter.create(
                        email = email,
                        name = registerEditName.text.toString(),
                        password = registerEditPassword.text.toString(),
                        confirm = registerEditConfirm.text.toString(),
                    )
                }

                registerTxtLogin.setOnClickListener {
                    activity?.finish()
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentAttachListener) {
            fragmentAttachListener = context
        }
    }

    override fun showProgress(enabled: Boolean) {
        binding?.registerNameBtmNext?.showProgress(enabled)
    }

    override fun displayNameFailure(error: Int?) {
        binding?.registerEditNameInput?.error = error?.let { getString(it) }
    }

    override fun displayPasswordFailure(error: Int?) {
        binding?.registerEditPasswordInput?.error = error?.let { getString(it) }
    }

    override fun displayConfirmFailure(error: Int?) {
        binding?.registerEditConfirmInput?.error = error?.let { getString(it) }
    }

    override fun onCreateFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onCreateSuccess(name: String) {
        fragmentAttachListener?.goToWelcomeScreen(name)
    }

    private val watcher = TxtWatcher {
        binding?.let {
            with(it) {
                registerNameBtmNext.isEnabled = registerEditName.text.toString().isNotEmpty()
                        && registerEditPassword.text.toString().isNotEmpty()
                        && registerEditConfirm.text.toString().isNotEmpty()
            }
        }
    }

    override fun onDestroy() {
        binding = null
        presenter.onDestroy()
        super.onDestroy()
    }

    companion object {
        const val KEY_EMAIL = "key_email"
    }
}
