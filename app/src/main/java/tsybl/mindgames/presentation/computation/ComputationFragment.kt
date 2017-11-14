package tsybl.mindgames.presentation.computation

import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_computing.*
import kotlinx.android.synthetic.main.fragment_computing.view.*
import tsybl.mindgames.R
import tsybl.mindgames.R.id.*
import tsybl.mindgames.data.ComputationRepository
import tsybl.mindgames.data.NumbersGenerator
import tsybl.mindgames.entities.GameResult
import tsybl.mindgames.presentation.BaseView
import tsybl.mindgames.presentation.dialogs.FinishDialog
import tsybl.mindgames.presentation.dialogs.StartGameDialog
import tsybl.mindgames.util.SchedulerProvider

interface ComputationView : BaseView<ComputationPresenter> {
    fun startGame(time: Long, tick: Long)
    fun startTimer()
    fun showTask(text: String)
    fun showScoreCount(count: String)
    fun setProgress(progressPosition: Int)
    fun updateBackgroundAnimations(isRight: Boolean)
    fun updateBestScoreVisibility(visibility: Boolean)
    fun endGame()
}

class ComputationFragment : Fragment(), DialogInterface.OnDismissListener, ComputationView {


    companion object {
        fun newInstance(): ComputationFragment {
            return ComputationFragment()
        }
    }

    private lateinit var mPresenter: ComputationPresenter;
    private lateinit var countDownTimer: CountDownTimer

    private lateinit var startDialog: StartGameDialog


//    @Inject
//    lateinit var router: Router


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater?.inflate(R.layout.fragment_computing, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startDialog = StartGameDialog(activity)
        startDialog.setOnDismissListener(this)

        initViews()
        //  MyApp.appComponent.inject(this)
        mPresenter = ComputationPresenterImpl(ComputationRepository(NumbersGenerator(1)), SchedulerProvider(), this)
    }

    override fun onStart() {
        super.onStart()
        mPresenter.subscribe()
    }

    override fun onStop() {
        super.onStop()
        mPresenter.unsubscribe()
    }

    override fun onDismiss(dialog: DialogInterface?) {
        mPresenter.onStartDialogDismiss()
    }

    override fun setPresenter(presenter: ComputationPresenter) {
        mPresenter = presenter
    }

    override fun setProgress(progressPosition: Int) {
        progressTime.progress = progressPosition
    }

    override fun updateBackgroundAnimations(isRight: Boolean) {
        if (isRight) {
            tvQuestion.playRight()
        } else {
            tvQuestion.playError()
        }
    }

    override fun showTask(text: String) {
        tvQuestion.text = text

    }

    override fun showScoreCount(count: String) {
        tvScoreCount.text = count
    }


    override fun updateBestScoreVisibility(visibility: Boolean) {
        ivBestScore.visibility = if (visibility) View.VISIBLE else View.INVISIBLE
    }


    override fun startGame(time: Long, tick: Long) {
        btnAnswerRight.isClickable = true
        btnAnswerWrong.isClickable = true
        tvQuestion.text = ""
        tvQuestion.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorAccent))
        countDownTimer = object : CountDownTimer(time, tick) {

            override fun onTick(millisUntilFinished: Long) {
                mPresenter.onTimerTick()
            }

            override fun onFinish() {
                mPresenter.onTimerFinish()
            }
        }
        startDialog.show()
        startDialog.start()

    }

    override fun startTimer() {
        countDownTimer.start()
    }


    override fun endGame() {
        countDownTimer.cancel()
        btnAnswerRight.isClickable = false
        btnAnswerWrong.isClickable = false
        val isNewRecord = true
        val result = GameResult(1, 4, 5, isNewRecord)
        val finishDialog = FinishDialog(activity, result,
                {
                    mPresenter.onFinishDialogBackToMainMenu()
                },
                {
                    mPresenter.onFinishDialogTryAgain()
                })
        finishDialog.show()
        finishDialog.init()


    }

    private fun initViews() {
        tvQuestion.setColors(ContextCompat.getColor(activity, R.color.colorAccent), ContextCompat.getColor(activity, R.color.colorWrong), ContextCompat.getColor(activity, R.color.colorRight))
        btnAnswerRight.setOnClickListener {
            mPresenter.onBtnRightClick()
        }
        btnAnswerWrong.setOnClickListener {
            mPresenter.onBtnWrongClick()
        }

    }
}