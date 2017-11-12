package tsybl.mindgames.data

import io.reactivex.Flowable
import tsybl.mindgames.entities.Task

interface TasksDataSource {

    fun getTasks(): Flowable<List<Task>>
}