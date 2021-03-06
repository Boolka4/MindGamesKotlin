package tsybl.mindgames.presentation.taskList

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_task_list.*
import ru.terrakok.cicerone.Router
import tsybl.mindgames.Constants
import tsybl.mindgames.MyApp
import tsybl.mindgames.R
import tsybl.mindgames.data.TasksRepository
import tsybl.mindgames.data.remote.TasksRemoteRepository
import tsybl.mindgames.entities.Task
import tsybl.mindgames.presentation.BaseView
import tsybl.mindgames.util.SchedulerProvider
import javax.inject.Inject

interface TaskListView : BaseView<TaskListPresenter> {
    fun setLoadingIndicator(active: Boolean)

    fun showTasks(tasks: List<Task>)
    fun showLoadingTasksError()
}

class TaskListFragment : Fragment(), TaskListView {


    private lateinit var mPresenter: TaskListPresenter;
    private lateinit var tasksAdapter: TaskListAdapter

    companion object {
        fun newInstance(): TaskListFragment {
            return TaskListFragment()
        }
    }

    @Inject
    lateinit var router: Router


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater?.inflate(R.layout.fragment_task_list, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initRecyclerView()
        MyApp.appComponent.inject(this)
        mPresenter = TaskListPresenterImpl(TasksRepository(TasksRemoteRepository()), SchedulerProvider(), this)
    }

    override fun onStart() {
        super.onStart()
        mPresenter.subscribe()
    }

    override fun onStop() {
        super.onStop()
        mPresenter.unsubscribe()
    }

    override fun setPresenter(presenter: TaskListPresenter) {
        mPresenter = presenter;
    }

    override fun setLoadingIndicator(active: Boolean) {
        Toast.makeText(activity, "setLoadingIndicator", Toast.LENGTH_LONG).show()
    }

    override fun showTasks(tasks: List<Task>) {
        tasksAdapter.setData(tasks)
    }

    override fun showLoadingTasksError() {
        Toast.makeText(activity, "showLoadingTasksError", Toast.LENGTH_LONG).show()
    }

    private fun initRecyclerView() {
        rvTasks.layoutManager = LinearLayoutManager(activity)
        rvTasks.adapter = tasksAdapter
    }

    private fun initAdapter() {
        tasksAdapter = TaskListAdapter(activity, {

            when (it) {
                1 -> router.navigateTo(Constants.TASK_COMPUTATION_FRAGMENT)
            }
        }, {})

    }


}