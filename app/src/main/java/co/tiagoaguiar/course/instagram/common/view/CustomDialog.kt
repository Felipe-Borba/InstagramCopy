package co.tiagoaguiar.course.instagram.common.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import co.tiagoaguiar.course.instagram.R
import co.tiagoaguiar.course.instagram.databinding.DialogCustomBinding

class CustomDialog(context: Context) : Dialog(context) {

    private lateinit var binding: DialogCustomBinding

    //    private lateinit var dialogLinearLayout: LinearLayout
    private lateinit var txtButtons: Array<TextView>

    //    private lateinit var txtTitle: TextView
    private var titleId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogCustomBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun setTitle(titleId: Int) {
        this.titleId = titleId
    }


    // TODO acho que eu prefiro fazer para o addButton add apenas um text, se quiser mais opções chama mais de uma vez
    // TODO por causa do listener que depois vai tem que fazer um elseif ou switch feião
    fun addButton(vararg texts: Int, listener: View.OnClickListener) {
        txtButtons = Array(texts.size) {
            TextView(context)
        }

        texts.forEachIndexed { i, txtId ->
            val textView = txtButtons[i]
            textView.id = txtId
            textView.setText(txtId)
            textView.setOnClickListener {
                listener.onClick(it)
                dismiss()
            }
        }
    }

    override fun show() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.show()

        titleId?.let {
            binding.dialogTitle.setText(it)
        }

        for (txtButton in txtButtons) {
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.setMargins(30, 50, 30, 50)
            binding.dialogContainer.addView(txtButton, layoutParams)
        }
    }
}