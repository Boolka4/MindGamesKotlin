package tsybl.mindgames.data

import io.reactivex.Flowable
import tsybl.mindgames.entities.ComputingTask

interface ComputingDataSource {
    fun getTask(previousTask: ComputingTask): Flowable<ComputingTask>
}