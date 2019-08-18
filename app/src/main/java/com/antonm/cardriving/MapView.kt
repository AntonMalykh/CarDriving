package com.antonm.cardriving

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.activity_automobile.view.*

class MapView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr) {

    private val markRadius = 80f
    private var mark: Pair<Float, Float>? = null
    private val markPaint: Paint =
        Paint(Paint.ANTI_ALIAS_FLAG)
            .apply { color = Color.GREEN }

    init{
        setWillNotDraw(false)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        mark?.let {
            canvas?.drawCircle(
                it.first,
                it.second,
                markRadius,
                markPaint
            )
        }
    }

    fun setMark(x: Float, y: Float) {
        mark = Pair(x, y)
        invalidate()
    }

    fun clearMark() {
        mark = null
        invalidate()
    }

}