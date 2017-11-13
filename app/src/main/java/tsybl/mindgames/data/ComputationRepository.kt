package tsybl.mindgames.data

import io.reactivex.Flowable
import tsybl.mindgames.entities.ComputingTask
import tsybl.mindgames.presentation.computation.ComputingTaskGenerator
import java.util.*

class ComputationRepository : ComputingDataSource {
    override fun getTask(previousTask: ComputingTask?): Flowable<ComputingTask> {
        ComputingTaskGenerator.generate(previousTask, 1, Random())
        return Flowable.just(previousTask)
    }
}