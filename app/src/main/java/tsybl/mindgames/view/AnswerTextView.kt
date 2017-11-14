package tsybl.mindgames.view

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import tsybl.mindgames.Constants.Companion.ANSWER_ANIMATION_DURATION

class AnswerTextView : TextView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private lateinit var rightAnimation: ValueAnimator
    private lateinit var errorAnimation: ValueAnimator

    fun setColors(default: Int, errorColor: Int, rightColor: Int) {
        rightAnimation = ValueAnimator.ofObject(ArgbEvaluator(), rightColor, default)
        rightAnimation.duration = ANSWER_ANIMATION_DURATION
        rightAnimation.addUpdateListener { animator -> setBackgroundColor(animator.animatedValue as Int) }
        errorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), errorColor, default)
        errorAnimation.duration = ANSWER_ANIMATION_DURATION
        errorAnimation.addUpdateListener { animator -> setBackgroundColor(animator.animatedValue as Int) }
    }

    fun playRight() = rightAnimation.start()
    fun playError() = errorAnimation.start()
}