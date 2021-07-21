package github.idmeetrious.moxytestapp.presentation.application.di.modules

import github.idmeetrious.moxytestapp.data.network.AuthRequests
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { provideRetrofit() }
    factory { provideAuthApi(get()) }
}

fun provideRetrofit(): Retrofit =
    Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

fun provideAuthApi(retrofit: Retrofit): AuthRequests {
    return retrofit.create(AuthRequests::class.java)
}