package com.bonioctavianus.android.shopping_app.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.bonioctavianus.android.shopping_app.R
import kotlinx.android.synthetic.main.view_error.view.*

class ErrorView(context: Context, attributeSet: AttributeSet) : FrameLayout(context, attributeSet) {

    init {
        View.inflate(context, R.layout.view_error, this)
    }

    fun setErrorMessage(message: String?) {
        text_error_message.text = message
    }
}