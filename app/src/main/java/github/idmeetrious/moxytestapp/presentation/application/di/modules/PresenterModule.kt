package github.idmeetrious.moxytestapp.presentation.application.di.modules

import github.idmeetrious.moxytestapp.presentation.presenters.RepositoryListPresenter
import org.koin.dsl.module

val presenterModule = module {
    factory { RepositoryListPresenter() }
}