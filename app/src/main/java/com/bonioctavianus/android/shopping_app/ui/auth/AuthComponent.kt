package com.bonioctavianus.android.shopping_app.ui.auth

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.bonioctavianus.android.shopping_app.R

class AuthComponent(context: Context, attributeSet: AttributeSet) :
    FrameLayout(context, attributeSet) {

    init {
        View.inflate(context, R.layout.component_auth, this)
    }
}