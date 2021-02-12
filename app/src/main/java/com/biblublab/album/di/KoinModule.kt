package com.biblublab.album.di

import androidx.room.Room
import com.biblublab.album.features.album.AlbumViewModel
import com.biblublab.album.features.gallery.GalleryViewModel
import com.biblublab.data.local.AlbumDataBase
import com.biblublab.data.remote.AlbumApi
import com.biblublab.data.repository.AlbumMapper
import com.biblublab.data.repository.AlbumRepositoryImpl
import com.biblublab.domain.AlbumRepository
import com.biblublab.domain.common.AppCoroutineDispatchers
import com.biblublab.domain.usecase.FetchDataUseCase
import com.biblublab.domain.usecase.ObserveAlbumsUseCase
import com.biblublab.domain.usecase.ObservePhotoUseCase
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dbModule = module {
    single { Room.databaseBuilder(androidApplication(), AlbumDataBase::class.java, AlbumDataBase.DB_NAME).build()}
    single { get<AlbumDataBase>().albumDao() }
}

val networkModule = module{
    single { provideLoggingInterceptor() }
    single { providesOkHttpClientBuilder(get()) }
    single { providesRetrofit(get()) }
    factory { provideRetrofitApi(get()) }
}

val dispatchersModule = module {
    single {
        AppCoroutineDispatchers(
            io = Dispatchers.IO,
            computation = Dispatchers.Default,
            main = Dispatchers.Main
        )
    }
}

val useCaseModule = module{
    factory { FetchDataUseCase(albumRepository = get(), appCoroutineDispatchers = get()) }
    factory { ObserveAlbumsUseCase(albumRepository = get(), appCoroutineDispatchers = get()) }
    factory { ObservePhotoUseCase(albumRepository = get(), appCoroutineDispatchers = get()) }
}

val albumModule = module{
    single { AlbumMapper() }
    single<AlbumRepository> { AlbumRepositoryImpl(albumDao = get(), albumApi = get(), albumMapper = get()) }
    viewModel { AlbumViewModel(fetchDataUseCase = get(), observeAlbumsUseCase = get()) }
    viewModel { GalleryViewModel(observePhotoUseCase = get()) }
}

private fun provideLoggingInterceptor() : HttpLoggingInterceptor =
    HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }


private fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()


private fun provideRetrofitApi(retrofit: Retrofit) : AlbumApi =
    retrofit.create(AlbumApi::class.java)

private fun providesOkHttpClientBuilder(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
    OkHttpClient.Builder()
        .apply {
            loggingInterceptor.also {
                addInterceptor(it)
            }
        }.build()