package tsybl.mindgames.presentation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Window
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.SupportFragmentNavigator
import ru.terrakok.cicerone.commands.Replace
import tsybl.mindgames.Constants
import tsybl.mindgames.MyApp
import tsybl.mindgames.R
import tsybl.mindgames.presentation.taskList.TaskListFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.requestFeature(Window.FEATURE_ACTION_BAR);
        supportActionBar!!.hide();
        setContentView(R.layout.activity_main_container)
        MyApp.appComponent.inject(this)
        navigator.applyCommand(Replace(Constants.TASK_LIST_FRAGMENT, null));

    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    var navigator = object : SupportFragmentNavigator(supportFragmentManager, R.id.main_container) {

        override fun createFragment(screenKey: String?, data: Any?): Fragment {
            when (screenKey) {
                Constants.TASK_LIST_FRAGMENT -> return TaskListFragment.newInstance()
                Constants.TASK_COMPUTATION_FRAGMENT -> return TaskListFragment.newInstance()
            }

            return TaskListFragment.newInstance()
        }

        override fun exit() {
            finish()
        }

        override fun showSystemMessage(message: String?) {
            TODO("not implemented")
        }


    }


}