package tsybl.mindgames.di

import dagger.Component
import tsybl.mindgames.presentation.MainActivity
import tsybl.mindgames.presentation.taskList.TaskListFragment
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(NavigationModule::class))
interface AppComponent {
    fun inject(activity: MainActivity)
}