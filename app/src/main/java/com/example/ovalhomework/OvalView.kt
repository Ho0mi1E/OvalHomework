package com.example.ovalhomework

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class OvalView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val ovalPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val linesPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var bigRadius: Float = 0f
    private var smallRadius: Float = 0f
    private var ovalColor: Int = 0
        set(value) {
            ovalPaint.color = value
            field = value
        }
    private var linesColor1: Int = 0
        set(value) {
            linesPaint.color = value
            field = value
        }
    private var thickness: Float = 0f
        set(value) {
            linesPaint.strokeWidth = value
            field = value
        }


    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.OvalView, 0, 0)
            .apply {
                bigRadius = getDimension(R.styleable.OvalView_bigRadius, 100f)
                smallRadius = getDimension(R.styleable.OvalView_smallRadius, 50f)
                ovalColor =
                    getColor(R.styleable.OvalView_ovalColor, context.getColor(R.color.purple_700))
                linesColor1 =
                    getColor(R.styleable.OvalView_linesColor, context.getColor(R.color.white))
                thickness = getDimension(R.styleable.OvalView_thickness,30f)
            }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(bigRadius.toInt(), smallRadius.toInt())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawOval(0f, 0f, bigRadius, smallRadius, ovalPaint)
        canvas?.drawLine(0f, height / 2f, width.toFloat(), height / 2f, linesPaint)
        canvas?.drawLine(width / 2f, 0f, width / 2f, height.toFloat(), linesPaint)
    }
}