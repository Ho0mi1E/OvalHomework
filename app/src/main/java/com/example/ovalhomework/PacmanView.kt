package com.example.ovalhomework

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class PacmanView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    companion object {
        private const val STROKE_WIDTH = 5f
        private const val MARGIN_COFF = 0.8f
        private const val TIME_ANIMATION = 2700L
        private const val ANGLE_MOUTH_UP = 270f
        private const val ANGLE_MOUTH_DOWN = 45f
    }

    private var pacmanRadius = 0f
    private var centerPacmanX = 0f
    private var centerPacmanY = 0f
    private var centerEyeX = 0f
    private var centerEyeY = 0f
    private var eyeRadius = 0f
    private var sweepAngle = 359.9f
    private var startAngle = 0f

    private val ovalForArc: RectF by lazy {
        RectF(centerPacmanX - pacmanRadius, centerPacmanY - pacmanRadius, centerPacmanX + pacmanRadius, centerPacmanY + pacmanRadius)
    }

    private val pacmanBackgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        .apply {
            color = context.getColor(R.color.black)
            strokeWidth = STROKE_WIDTH
            style = Paint.Style.STROKE
        }

    private val pacmanEyePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var pacmanColor: Int = 0
        set(value) {
            pacmanBackgroundPaint.color = value
            field = value
        }

    private var eyeColor: Int = 0
        set(value) {
            pacmanEyePaint.color = value
            field = value
        }

    private val animationMouthUp: ValueAnimator
        get() = ValueAnimator.ofFloat(startAngle, ANGLE_MOUTH_UP, startAngle)
            .apply {
                duration = TIME_ANIMATION
                repeatCount = ValueAnimator.INFINITE
                addUpdateListener {
                    startAngle = it.animatedValue as Float
                    invalidate()
                }
            }

    private val animationMouthDown: ValueAnimator
        get() = ValueAnimator.ofFloat(sweepAngle, ANGLE_MOUTH_DOWN, sweepAngle)
            .apply {
                repeatCount = ValueAnimator.INFINITE
                duration = TIME_ANIMATION
                addUpdateListener {
                    sweepAngle = it.animatedValue as Float
                    invalidate()
                }
            }

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.PacmanView, 0, 0)
            .apply {
                pacmanColor =
                    getColor(R.styleable.PacmanView_pacmanColor, context.getColor(R.color.yellow))
                eyeColor =
                    getColor(R.styleable.PacmanView_eyeColor, context.getColor(R.color.black))
            }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        centerPacmanX = width / 2f
        centerPacmanY = height / 2f
        pacmanRadius = width / 2f * MARGIN_COFF
        eyeRadius = pacmanRadius * 0.1f
        centerEyeY = height / 4f
        centerEyeX = width / 2f - eyeRadius
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawArc(ovalForArc, startAngle, sweepAngle, true, pacmanBackgroundPaint)
        canvas.drawArc(ovalForArc, startAngle, sweepAngle, true, strokePaint)
        canvas.drawCircle(centerEyeX, centerEyeY, eyeRadius, pacmanEyePaint)
    }

    fun startAnimation() {
        animationMouthUp.start()
        animationMouthDown.start()
    }
}