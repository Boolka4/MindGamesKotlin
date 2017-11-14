package tsybl.mindgames.presentation.dialogs

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.TextView
import kotlinx.android.synthetic.main.dialog_start_game.*
import tsybl.mindgames.R

class StartGameDialog(context: Context) : Dialog(context, R.style.StartGameDialog) {

    init {
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(LayoutInflater.from(context).inflate(R.layout.dialog_start_game, null))
    }

    fun start() {
        countDown(tvNumber, 3)
    }

    private fun countDown(tv: TextView, count: Int) {
        if (count == 0) {
            dismiss()
            tv.text = ""
            return
        }
        tv.text = count.toString()
        val animation = AlphaAnimation(1.0f, 0.0f)
        animation.duration = 750
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {
                countDown(tv, count - 1)
            }


            override fun onAnimationRepeat(animation: Animation) {

            }
        })
        tv.startAnimation(animation)
    }
}