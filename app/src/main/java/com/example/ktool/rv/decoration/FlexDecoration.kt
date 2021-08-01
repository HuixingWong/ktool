package com.example.ktool.rv.decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class FlexDecoration constructor(
    builder: Builder
) : RecyclerView.ItemDecoration() {
    val ATTRS = IntArray(android.R.attr.listDivider)
    val DEFAULT_SIZE = 2

    protected enum class DividerType {
        DRAWABLE, PAINT, COLOR
    }

    protected var dividerType = DividerType.DRAWABLE
    var visibilityProvider: VisibilityProvider
    protected var paintProvider: PaintProvider? = null
    protected var colorProvider: ColorProvider? = null
    protected var drawableProvider: DrawableProvider? = null
    protected var sizeProvider: SizeProvider? = null
    protected var showLast: Boolean = false
    protected var positionInsideItem: Boolean = false
    private var paint: Paint? = null

    init {
        if (builder.paintProvider != null) {
            dividerType = DividerType.PAINT
            paintProvider = builder.paintProvider
        } else if (builder.colorProvider != null) {
            dividerType = DividerType.COLOR
            colorProvider = builder.colorProvider
            paint = Paint()
            setSizeProvider(builder)
        } else {
            dividerType = DividerType.DRAWABLE
            if (builder.drawableProvider == null) {
                setDefaultDrawableProvider(builder)
            } else {
                drawableProvider = builder.drawableProvider
            }
            sizeProvider = builder.sizeProvider
        }
        visibilityProvider = builder.visibilityProvider
        showLast = builder.showLastDivider
        positionInsideItem = builder.positionInsideItem
    }

    private fun setDefaultDrawableProvider(builder: Builder) =
        builder.context.obtainStyledAttributes(ATTRS).let { a ->
            a.getDrawable(0)?.let { d ->
                drawableProvider = object : DrawableProvider {
                    override fun drawableProvider(
                        position: Int,
                        parent: RecyclerView
                    ): Drawable {
                        return d
                    }
                }
            }
            a.recycle()
        }

    private fun setSizeProvider(builder: Builder) = apply {
        sizeProvider = builder.sizeProvider ?: object : SizeProvider {
            override fun dividerSize(position: Int, parent: RecyclerView): Int {
                return DEFAULT_SIZE
            }
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val adapter = parent.adapter ?: return
        val itemCount = adapter.itemCount
        val lastDivideroffset = getLastDividerOffset(parent)
        val validChildCount = parent.childCount
        var lastChildPosition = -1
        for (i in 0 until validChildCount) {
            parent.getChildAt(i).run {
                val childPosition = parent.getChildAdapterPosition(this)
                if (childPosition < lastChildPosition) {
                    return@run
                }
                lastChildPosition = childPosition
                if (!showLast && childPosition >= itemCount - lastDivideroffset) {
                    return@run
                }
                if (wasDividerAlreadyDrawn(childPosition, parent)) {
                    return@run
                }
                val groupIndex = getGroupIndex(childPosition, parent)
                if (visibilityProvider.shouldShowDivider(childPosition, parent)) {
                    return@run
                }
                val bounds = getDividerBound(groupIndex, parent, this)
                when (dividerType) {
                    DividerType.DRAWABLE -> {
                        drawableProvider!!.drawableProvider(groupIndex, parent).run {
                            setBounds(bounds)
                            draw(c)
                        }
                    }
                    DividerType.COLOR -> {
                        paint?.apply {
                            setColor(colorProvider!!.dividerColor(groupIndex, parent))
                            strokeWidth = sizeProvider!!.dividerSize(groupIndex, parent).toFloat()
                            drawLine(bounds, c)
                        }
                    }
                    DividerType.PAINT -> {
                        paint = paintProvider!!.dividerPaint(groupIndex, parent)
                        drawLine(bounds, c)
                    }
                }
            }
        }
        super.onDraw(c, parent, state)
    }

    private fun drawLine(rect: Rect, c: Canvas) {
        paint?.let {
            c.drawLine(
                rect.left.toFloat(),
                rect.top.toFloat(),
                rect.right.toFloat(),
                rect.bottom.toFloat(),
                it
            )
        }
    }

    fun isReverseLayout(parent: RecyclerView) =
        (parent.layoutManager as? LinearLayoutManager)?.reverseLayout ?: false


    /**
     * just for grid layout
     */
    fun getLastDividerOffset(parent: RecyclerView) = 1

    fun wasDividerAlreadyDrawn(position: Int, parent: RecyclerView) = false

    fun getGroupIndex(position: Int, parent: RecyclerView) = position

    abstract fun getDividerBound(position: Int, parent: RecyclerView, child: View): Rect

    abstract fun setItemoffset(outRect: Rect, position: Int, parent: RecyclerView)

    abstract class Builder constructor(
        val context: Context
    ) {
        var paintProvider: PaintProvider? = null
        var colorProvider: ColorProvider? = null
        var drawableProvider: DrawableProvider? = null
        var sizeProvider: SizeProvider? = null
        var visibilityProvider = object : VisibilityProvider {
            override fun shouldShowDivider(position: Int, parent: RecyclerView): Boolean = false
        }
        var showLastDivider: Boolean = false
        var positionInsideItem: Boolean = false

        fun paint(paint: Paint): Builder = paintProvider(object : PaintProvider {
            override fun dividerPaint(position: Int, parent: RecyclerView): Paint {
                return paint
            }
        })

        fun paintProvider(provider: PaintProvider) = apply {
            paintProvider = provider
        }

        fun color(color: Int) = colorProvider(object : ColorProvider {
            override fun dividerColor(position: Int, parent: RecyclerView): Int {
                return color
            }
        })

        fun colorResId(@ColorRes colorId: Int) =
            color(ContextCompat.getColor(context, colorId))

        fun colorProvider(provider: ColorProvider) = apply {
            colorProvider = provider
        }

        fun drawable(@DrawableRes id: Int) = ContextCompat.getDrawable(context, id)?.let {
            drawable(it)
        }

        fun drawable(drawable: Drawable) = drawableProvider(object : DrawableProvider {
            override fun drawableProvider(position: Int, parent: RecyclerView): Drawable {
                return drawable
            }
        })

        fun drawableProvider(provider: DrawableProvider) = apply {
            drawableProvider = provider
        }

        fun sizeRes(@DimenRes id: Int) = size(context.resources.getDimensionPixelSize(id))

        fun size(size: Int) = sizeProvider(object : SizeProvider {
            override fun dividerSize(position: Int, parent: RecyclerView): Int {
                return size
            }
        })

        fun sizeProvider(provider: SizeProvider) = apply {
            sizeProvider = provider
        }

        fun visibilityProvider(provider: VisibilityProvider) = apply {
            visibilityProvider = provider
        }

        fun showLastDivider() = apply {
            showLastDivider = true
        }

        fun positionInsideItem(positionInsideItem: Boolean) = apply {
            this.positionInsideItem = positionInsideItem
        }

        abstract fun build(): FlexDecoration

    }

    interface VisibilityProvider {
        fun shouldShowDivider(position: Int, parent: RecyclerView): Boolean
    }

    interface PaintProvider {
        fun dividerPaint(position: Int, parent: RecyclerView): Paint
    }

    interface ColorProvider {
        fun dividerColor(position: Int, parent: RecyclerView): Int
    }

    interface DrawableProvider {
        fun drawableProvider(position: Int, parent: RecyclerView): Drawable
    }

    interface SizeProvider {
        fun dividerSize(position: Int, parent: RecyclerView): Int
    }
}


