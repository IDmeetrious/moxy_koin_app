package github.idmeetrious.moxytestapp.presentation.application.di.modules

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appModule = module {
    single { androidApplication().applicationContext }
}