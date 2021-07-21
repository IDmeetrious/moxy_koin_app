package github.idmeetrious.moxytestapp.presentation.application

import android.app.Application
import github.idmeetrious.moxytestapp.presentation.application.di.modules.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    appModule,
                    networkModule,
                    presenterModule,
                    remoteDataSourceModule,
                    repositoryModule,
                    useCaseModule
                )
            )
        }
    }
}