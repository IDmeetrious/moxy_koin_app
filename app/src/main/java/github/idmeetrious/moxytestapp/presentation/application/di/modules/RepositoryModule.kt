package github.idmeetrious.moxytestapp.presentation.application

import github.idmeetrious.moxytestapp.data.repositories.BaseRepositoryImpl
import github.idmeetrious.moxytestapp.domain.repositories.BaseRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<BaseRepository> { BaseRepositoryImpl(get()) }
}




