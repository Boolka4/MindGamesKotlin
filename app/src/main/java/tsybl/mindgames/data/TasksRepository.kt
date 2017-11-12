package tsybl.mindgames.data

import io.reactivex.Flowable
import tsybl.mindgames.entities.Task


class TasksRepository(private val tasksRemoteDataSource: TasksDataSource) : TasksDataSource {
    override fun getTasks(): Flowable<List<Task>> {
        return tasksRemoteDataSource
                .getTasks()
    }
}