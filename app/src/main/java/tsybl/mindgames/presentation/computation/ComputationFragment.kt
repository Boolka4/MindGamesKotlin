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
import tsybl.mindgames.R
import tsybl.mindgames.entities.ComputingTask
import tsybl.mindgames.entities.GameResult
import tsybl.mindgames.presentation.BaseView
import tsybl.mindgames.presentation.dialogs.FinishDialog
import tsybl.mindgames.presentation.dialogs.StartGameDialog
import java.util.*

interface ComputationView : BaseView<ComputationPresenter> {
    fun startGame()
    fun showTask(task: ComputingTask)
    fun endGame()
}

class ComputationFragment : Fragment(), DialogInterface.OnDismissListener, ComputationView {


    companion object {
        fun newInstance(): ComputationFragment {
            return ComputationFragment()
        }
    }

    private lateinit var countDownTimer: CountDownTimer
    private var progressPosition = 0
    private var isTaskRunning = false
    private lateinit var startDialog: StartGameDialog
    private var record = 0
    private var currentBestScore = 0
    private lateinit var currentTask: ComputingTask
//    @Inject
//    lateinit var router: Router


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater?.inflate(R.layout.fragment_computing, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startDialog = StartGameDialog(activity)
        startDialog.setOnDismissListener(this)
        currentTask = ComputingTask("qq", true, 1, 4, 6, 1, "qr")
        initViews()
        startGame()
    }

    override fun onStart() {
        super.onStart()
        //  mPresenter.subscribe()
    }

    override fun onStop() {
        super.onStop()
        //   mPresenter.unsubscribe()
    }

    override fun onDismiss(dialog: DialogInterface?) {
        countDownTimer.start()
        updateData(currentTask)
    }

    override fun setPresenter(presenter: ComputationPresenter) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showTask(task: ComputingTask) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    //    override fun setPresenter(presenter: TaskListContract.Presenter) {
//        mPresenter = presenter;
//    }
    override fun startGame() {
        btnAnswerRight.isClickable = true
        btnAnswerWrong.isClickable = true
        tvQuestion.text = ""
        tvQuestion.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorAccent))
        countDownTimer = object : CountDownTimer(16500, 25) {

            override fun onTick(millisUntilFinished: Long) {
                progressPosition++
                progressTime.setProgress(progressPosition)

            }

            override fun onFinish() {
                progressPosition++
                progressTime.setProgress(progressPosition)
                updateBackground(false)
                endGame()
            }
        }
        startDialog.show()
        startDialog.start()
        progressPosition = 0
        record = 0
        progressTime.setProgress(progressPosition)
    }

    override fun endGame() {
        countDownTimer.cancel()
        val isNewRecord = true
        val result = GameResult(1, 4, 5, isNewRecord)
        val finishDialog = FinishDialog(activity, result,
                {

                },
                {
                    startGame()
                })
        finishDialog.show()
        finishDialog.init()


    }

    private fun initViews() {
        btnAnswerRight.setOnClickListener {
            setAnswer(currentTask.isRight)
            updateData(currentTask)
        }
        btnAnswerWrong.setOnClickListener {
            setAnswer(!currentTask.isRight)
            updateData(currentTask)
        }

    }

    private fun setAnswer(answer: Boolean) {
        if (answer) {
            updateBackground(true)
            record++
        } else {
            updateBackground(false)
            endGame()
        }
    }

    private fun updateBackground(isRight: Boolean) {
        if (isRight) {
            if (!isTaskRunning) {
                tvQuestion.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorRight))
                Thread(Runnable {
                    try {
                        Thread.sleep(400)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    } finally {
                        activity.runOnUiThread({
                            if (!isTaskRunning) {
                                tvQuestion.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorAccent))
                            }
                        })
                    }
                }).start()
            }
        } else {
            isTaskRunning = true
            tvQuestion.setBackgroundColor(ContextCompat.getColor(activity, R.color.colorWrong))
            btnAnswerRight.isClickable = false
            btnAnswerWrong.isClickable = false
        }
    }

    private fun updateData(task: ComputingTask) {
        tvScoreCount.setText(record.toString())
        ComputingTaskGenerator.generate(task, 1, Random())
        tvQuestion.text = task.question
        if (record > currentBestScore) {
            ivBestScore.setVisibility(View.VISIBLE)
        }
    }

}