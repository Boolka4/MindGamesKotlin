package tsybl.mindgames.presentation.computation

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import tsybl.mindgames.data.ComputingDataSource
import tsybl.mindgames.entities.ComputingTask
import tsybl.mindgames.presentation.BasePresenter
import tsybl.mindgames.util.BaseSchedulerProvider
import java.util.*


interface ComputationPresenter : BasePresenter {
    fun onStartDialogDismiss()
    fun onBtnRightClick()
    fun onBtnWrongClick()
    fun onFinishDialogTryAgain()
    fun onFinishDialogBackToMainMenu()
    fun onTimerTick()
    fun onTimerFinish()
}

class ComputationPresenterImpl(private val computingDataSource: ComputingDataSource,
                               private val schedulerProvider: BaseSchedulerProvider,
                               private val computationView: ComputationView) : ComputationPresenter {


    private val mCompositeDisposable: CompositeDisposable
    private var progressPosition = 0
    private var record = 0
    private var currentBestScore = 0
    private var currentTask: ComputingTask
    private lateinit var tasksDisposable: Disposable

    init {
        computationView.setPresenter(this)
        currentTask = ComputingTask("qq", true)
        mCompositeDisposable = CompositeDisposable()
    }

    override fun subscribe() {
        mCompositeDisposable.clear()
        startNewGame()
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }

    override fun onStartDialogDismiss() {
        computationView.startTimer()
        updateData(currentTask)
    }

    override fun onTimerTick() {
        progressPosition++
        computationView.setProgress(progressPosition)
    }

    override fun onTimerFinish() {
        progressPosition++
        updateProgress(progressPosition)
        updateBackgroundAnimations(false)
        computationView.endGame()
    }

    private fun setAnswer(answer: Boolean) {
        if (answer) {
            updateBackgroundAnimations(true)
            record++
        } else {
            updateBackgroundAnimations(false)
            computationView.endGame()
        }
    }

    override fun onBtnRightClick() {
        setAnswer(currentTask.isRight)
        updateData(currentTask)
    }

    override fun onBtnWrongClick() {
        setAnswer(!currentTask.isRight)
        updateData(currentTask)
    }

    override fun onFinishDialogTryAgain() {
        startNewGame()
    }

    override fun onFinishDialogBackToMainMenu() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun updateProgress(progress: Int) {
        computationView.setProgress(progress)
    }

    private fun startNewGame() {
        computationView.startGame(16500, 25)
        progressPosition = 0
        record = 0
        computationView.updateBestScoreVisibility(false)
        computationView.showScoreCount(record.toString())
        updateProgress(progressPosition)
    }

    private fun updateBackgroundAnimations(isRight: Boolean) {
        computationView.updateBackgroundAnimations(isRight)
    }

    private fun updateData(task: ComputingTask) {
        tasksDisposable = computingDataSource
                .getTask(task)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe({ task ->
                    computationView.showTask(task.question)
                })
        mCompositeDisposable.add(tasksDisposable)
        computationView.showScoreCount(record.toString())
        if (record > currentBestScore) {
            updateBestScoreVisibility(true)
        }
    }

    private fun updateBestScoreVisibility(visibility: Boolean) {
        computationView.updateBestScoreVisibility(visibility)
    }


}