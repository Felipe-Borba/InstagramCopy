package co.tiagoaguiar.course.instagram.common.util

import android.text.Editable
import android.text.TextWatcher

//NOTE this is a delegate pattern?
class TxtWatcher(val onTextChanged: (String) -> Unit) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        onTextChanged(s.toString())
    }

    override fun afterTextChanged(s: Editable?) {
    }
}