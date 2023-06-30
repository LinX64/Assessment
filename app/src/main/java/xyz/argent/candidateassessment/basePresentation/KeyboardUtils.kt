package xyz.argent.candidateassessment.basePresentation

import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.getSystemService

object KeyboardUtils {

    fun showKeyboard(editText: EditText) {
        if (editText.requestFocus()) {
            editText.context.getSystemService<InputMethodManager>()!!.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        }
    }

}