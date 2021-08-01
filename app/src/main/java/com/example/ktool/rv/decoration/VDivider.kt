package com.example.ktool.rv.decoration

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

import android.graphics.drawable.Drawable
import java.lang.RuntimeException

inline fun create(
    context: Context,
    create: VDivider.Builder.() -> Unit
): VDivider {
    val builder = VDivider.Builder(context)
    builder.create()
    return builder.build() as VDivider
}

inline fun <reified T : FlexDecoration.Builder> create(
    context: Context,
    create: FlexDecoration.Builder.() -> Unit
): T {
    val builder = T::class.java.constructors.get(0).newInstance(context) as FlexDecoration.Builder
    builder.create()
    return builder.build() as T
}

class VDivider constructor(
    builder: Builder
) : FlexDecoration(builder) {

    private var marginProvider: MarginProvider

    init {
        marginProvider = builder.marginProvider
    }

    override fun getDividerBound(position: Int, parent: RecyclerView, child: View): Rect {
        val bounds = Rect(0, 0, 0, 0)
        val transitionX = child.translationX.toInt()
        val transitionY = child.translationY.toInt()
        val params = child.layoutParams as RecyclerView.LayoutParams
        bounds.top = parent.paddingTop +
                marginProvider.dividerTopMargin(position, parent) + transitionY
        bounds.bottom = parent.height - parent.paddingBottom -
                marginProvider.dividerBottomMargin(position, parent) + transitionY

        val dividerSize: Int = getDividerSize(position, parent)
        val isReverseLayout = isReverseLayout(parent)
        if (dividerType === DividerType.DRAWABLE) {
            // set left and right position of divider
            if (isReverseLayout) {
                bounds.right = child.left - params.leftMargin + transitionX
                bounds.left = bounds.right - dividerSize
            } else {
                bounds.left = child.right + params.rightMargin + transitionX
                bounds.right = bounds.left + dividerSize
            }
        } else {
            // set center point of divider
            val halfSize = dividerSize / 2
            if (isReverseLayout) {
                bounds.left = child.left - params.leftMargin - halfSize + transitionX
            } else {
                bounds.left = child.right + params.rightMargin + halfSize + transitionX
            }
            bounds.right = bounds.left
        }

        if (positionInsideItem) {
            if (isReverseLayout) {
                bounds.left += dividerSize
                bounds.right += dividerSize
            } else {
                bounds.left -= dividerSize
                bounds.right -= dividerSize
            }
        }
        return bounds
    }

    override fun setItemoffset(outRect: Rect, position: Int, parent: RecyclerView) {
        if (positionInsideItem) {
            outRect.set(0, 0, 0, 0);
            return
        }

        if (isReverseLayout(parent)) {
            outRect.set(getDividerSize(position, parent), 0, 0, 0);
        } else {
            outRect.set(0, 0, getDividerSize(position, parent), 0);
        }
    }

    private fun getDividerSize(position: Int, parent: RecyclerView): Int {
        return when {
            paintProvider != null -> {
                paintProvider!!.dividerPaint(position, parent).strokeWidth.toInt()
            }
            paintProvider != null -> {
                sizeProvider!!.dividerSize(position, parent)
            }
            drawableProvider != null -> {
                val drawable: Drawable = drawableProvider!!.drawableProvider(position, parent)
                drawable.intrinsicWidth
            }
            else -> throw RuntimeException("failed to get size")
        }
    }

    interface MarginProvider {
        fun dividerTopMargin(position: Int, parent: RecyclerView): Int
        fun dividerBottomMargin(position: Int, parent: RecyclerView): Int
    }

    class Builder constructor(
        context: Context
    ) : FlexDecoration.Builder(context) {

        var marginProvider = object : MarginProvider {
            override fun dividerTopMargin(position: Int, parent: RecyclerView): Int = 0
            override fun dividerBottomMargin(position: Int, parent: RecyclerView): Int = 0
        }


        fun margin(topMargin: Int, bottomMargin: Int) = marginProvider(object : MarginProvider {

            override fun dividerTopMargin(position: Int, parent: RecyclerView): Int = topMargin

            override fun dividerBottomMargin(position: Int, parent: RecyclerView): Int =
                bottomMargin
        })

        fun margin(verticalMargin: Int) = margin(verticalMargin, verticalMargin)

        fun marginResId(@DimenRes verticalMarginId: Int) =
            marginResId(verticalMarginId, verticalMarginId)

        fun marginResId(@DimenRes top: Int, @DimenRes bottom: Int) = margin(
            context.resources.getDimensionPixelSize(top),
            context.resources.getDimensionPixelSize(bottom),
        )

        fun marginProvider(provider: MarginProvider) = apply {
            marginProvider = provider
        }

        override fun build(): FlexDecoration = VDivider(this)
    }

}