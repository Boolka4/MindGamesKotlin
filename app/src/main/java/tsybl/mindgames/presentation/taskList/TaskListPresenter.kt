package tsybl.mindgames.presentation.taskList

import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import tsybl.mindgames.data.TasksDataSource
import tsybl.mindgames.presentation.BasePresenter
import tsybl.mindgames.util.BaseSchedulerProvider

interface TaskListPresenter : BasePresenter {

}

class TaskListPresenterImpl(private val tasksDataSource: TasksDataSource,
                            private val schedulerProvider: BaseSchedulerProvider,
                            private val mTaskListView: TaskListView) : TaskListPresenter {
    private val mCompositeDisposable: CompositeDisposable

    init {
        mTaskListView.setPresenter(this)
        mCompositeDisposable = CompositeDisposable()
    }

    override fun subscribe() {
        mCompositeDisposable.clear()
        val disposable = tasksDataSource
                .getTasks()
                .flatMap { Flowable.fromIterable(it) }
                .toList()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                        { tasks ->
                            mTaskListView.showTasks(tasks)
                            mTaskListView.setLoadingIndicator(false)
                        },
                        { _ -> mTaskListView.showLoadingTasksError() })
        mCompositeDisposable.add(disposable)
    }


    override fun unsubscribe() {
        mCompositeDisposable.clear()
    }
}