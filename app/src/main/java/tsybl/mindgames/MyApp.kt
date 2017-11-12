package tsybl.mindgames

import android.app.Application
import tsybl.mindgames.di.AppComponent
import tsybl.mindgames.di.DaggerAppComponent

class MyApp : Application() {

    companion object {
        @JvmStatic lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
      appComponent = DaggerAppComponent.builder().build()
    }


}