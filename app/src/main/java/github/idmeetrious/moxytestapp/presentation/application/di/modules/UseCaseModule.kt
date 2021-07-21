package github.idmeetrious.moxytestapp.presentation.application.di.modules

import github.idmeetrious.moxytestapp.domain.usecases.DownloadRepositoryZip
import github.idmeetrious.moxytestapp.domain.usecases.DownloadRepositoryZipImpl
import github.idmeetrious.moxytestapp.domain.usecases.GetUserRepositories
import github.idmeetrious.moxytestapp.domain.usecases.GetUserRepositoriesImpl
import org.koin.dsl.module

val useCaseModule = module {
    factory<GetUserRepositories> { GetUserRepositoriesImpl(get()) }
    factory<DownloadRepositoryZip> { DownloadRepositoryZipImpl(get()) }
}