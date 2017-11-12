package tsybl.mindgames.util

import io.reactivex.Scheduler

/**
 * Created by Boolka4 on 01.11.2017.
 */
interface BaseSchedulerProvider {

    fun computation(): Scheduler

    fun io(): Scheduler

    fun ui(): Scheduler
}