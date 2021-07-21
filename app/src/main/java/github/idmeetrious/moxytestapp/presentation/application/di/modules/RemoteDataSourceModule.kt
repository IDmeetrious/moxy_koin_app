package github.idmeetrious.moxytestapp.presentation.application.di.modules

import github.idmeetrious.moxytestapp.data.datasource.remote.RemoteDataSource
import github.idmeetrious.moxytestapp.data.datasource.remote.RemoteDataSourceImpl
import github.idmeetrious.moxytestapp.data.mappers.DtoMapper
import org.koin.dsl.module

val remoteDataSourceModule = module {
    factory<RemoteDataSource> { RemoteDataSourceImpl(get(), DtoMapper()) }
}