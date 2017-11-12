package tsybl.mindgames.di

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Singleton


@Module
class NavigationModule {

    var cicerone: Cicerone<Router> = Cicerone.create()

    @Provides
    @Singleton
    internal fun provideRouter(): Router {
        return cicerone.getRouter()
    }

    @Provides
    @Singleton
    internal fun provideNavigatorHolder(): NavigatorHolder {
        return cicerone.getNavigatorHolder()
    }
}