package tsybl.mindgames.presentation.computation

import io.reactivex.disposables.CompositeDisposable
import tsybl.mindgames.data.ComputingDataSource
import tsybl.mindgames.presentation.BasePresenter
import tsybl.mindgames.util.BaseSchedulerProvider


interface ComputationPresenter : BasePresenter {

}

class ComputationPresenterImpl(private val computingDataSource: ComputingDataSource,
                               private val schedulerProvider: BaseSchedulerProvider,
                               private val computationView: ComputationView) : ComputationPresenter {

    private val mCompositeDisposable: CompositeDisposable

    init {
        computationView.setPresenter(this)
        mCompositeDisposable = CompositeDisposable()
    }

    override fun subscribe() {
        mCompositeDisposable.clear()
        val disposable = computingDataSource.getTask(null).subscribe({ task ->
            computationView.showTask(task)
        })
        mCompositeDisposable.add(disposable)
    }

    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }
}