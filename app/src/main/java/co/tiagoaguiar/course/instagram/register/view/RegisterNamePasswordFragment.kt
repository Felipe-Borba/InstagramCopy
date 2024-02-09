package co.tiagoaguiar.course.instagram.register.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import co.tiagoaguiar.course.instagram.R
import co.tiagoaguiar.course.instagram.common.base.BasePresenter
import co.tiagoaguiar.course.instagram.databinding.FragmentRegisterNamePasswordBinding
import co.tiagoaguiar.course.instagram.register.RegisterNamePassword

class RegisterNamePasswordFragment : Fragment(R.layout.fragment_register_name_password), RegisterNamePassword.View {

    private var binding: FragmentRegisterNamePasswordBinding? = null
    override lateinit var presenter: BasePresenter
//    private var fragmentAttachListener: FragmentAttachListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterNamePasswordBinding.bind(view)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
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
