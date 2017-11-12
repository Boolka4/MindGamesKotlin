package tsybl.mindgames.presentation.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import kotlinx.android.synthetic.main.dialog_finish.*
import tsybl.mindgames.R
import tsybl.mindgames.entities.GameResult

class FinishDialog(context: Context,
                   private val gameResult: GameResult,
                   private val backToMainMenu: () -> Unit,
                   private val playAgain: () -> Unit) : Dialog(context, R.style.DialogSlideAnim) {

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(LayoutInflater.from(context).inflate(R.layout.dialog_finish, null))

    }


    fun init() {
        tvYourScore.text = gameResult.score.toString()
        tvStarCount.text = gameResult.start.toString()
        tvCoinsCount.text = gameResult.coins.toString()
        btnBack.setOnClickListener {
            dismiss()
            backToMainMenu()
        }
        btnAgain.setOnClickListener {
            dismiss()
            playAgain()
        }
    }
}