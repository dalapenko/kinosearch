package tech.dalapenko.network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import tech.dalapenko.network.BuildConfig
import tech.dalapenko.network.api.KinoApi
import tech.dalapenko.network.adapter.NetworkResponseCallAdapterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val authInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("X-API-KEY", BuildConfig.KINO_AUTH_TOKEN)
                .build()

            chain.proceed(request)
        }
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    fun provideRetrofitBuilder(
        okHttpClient: OkHttpClient
    ): Retrofit.Builder {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .client(okHttpClient)
            .addCallAdapterFactory(NetworkResponseCallAdapterFactory())
            .addConverterFactory(Json.asConverterFactory(contentType))
    }

    @Provides
    fun provideKinoApi(
        retrofitBuilder: Retrofit.Builder
    ): KinoApi {
        return retrofitBuilder
            .baseUrl(API_BASE_URL)
            .build()
            .create(KinoApi::class.java)
    }
}

private const val API_BASE_URL = "https://kinopoiskapiunofficial.tech/api/"
