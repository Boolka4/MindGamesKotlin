package tsybl.mindgames.presentation.taskList

import tsybl.mindgames.entities.Task
import tsybl.mindgames.presentation.BasePresenter
import tsybl.mindgames.presentation.BaseView

interface TaskListContract {
    interface View : BaseView<Presenter> {
        fun setLoadingIndicator(active: Boolean)

        fun showTasks(tasks: List<Task>)
        fun showLoadingTasksError()
    }

    interface Presenter : BasePresenter {

    }
}