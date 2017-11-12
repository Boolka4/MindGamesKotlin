package tsybl.mindgames.data.remote

import io.reactivex.Flowable
import tsybl.mindgames.data.TasksDataSource
import tsybl.mindgames.entities.Task
import tsybl.mindgames.entities.TaskType

class TasksRemoteRepository : TasksDataSource {
    var tasks: ArrayList<Task> = ArrayList()

    init {
        tasks.add(Task("COLOR RUSH", false, 0, TaskType.colorRush))
        tasks.add(Task("Computing", false, 0, TaskType.computing))
        tasks.add(Task("Reaction Tap", false, 0, TaskType.reactionTap))
        tasks.add(Task("ShulteNumbers", false, 0, TaskType.schulteNumbers))
        tasks.add(Task("Memory", false, 0, TaskType.memory))
    }

    override fun getTasks(): Flowable<List<Task>> {
        return Flowable.fromArray(tasks)
    }
}