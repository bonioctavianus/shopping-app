package com.bonioctavianus.android.shopping_app.ui.widget

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class VerticalItemDecorator(
    private val itemSpace: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val itemPosition = parent.getChildAdapterPosition(view)
        if (itemPosition == RecyclerView.NO_POSITION) {
            return
        }
        val itemCount = state.itemCount

        // first position
        if (itemPosition == 0) {
            outRect.set(itemSpace, itemSpace, itemSpace, itemSpace)
            // last position
        } else if (itemCount > 0 && itemPosition == itemCount - 1) {
            outRect.set(itemSpace, 0, itemSpace, itemSpace)
            // between first and last position
        } else {
            outRect.set(itemSpace, 0, itemSpace, itemSpace)
        }
    }
}