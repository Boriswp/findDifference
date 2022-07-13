package com.immo.findTheDifferences.modules

import android.annotation.SuppressLint
import com.immo.findTheDifferences.Const
import com.immo.findTheDifferences.remote.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Singleton
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @SuppressLint("TimberArgCount")
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = Interceptor { chain ->

            val t1 = System.nanoTime()
            val builder = chain.request()
                .newBuilder()

            val request = builder.build()

            Timber.d(
                String.format(
                    "Sending request %s on %s%n%s",
                    request.url, chain.connection(), request.headers
                )
            )

            val response = chain.proceed(request)
            val t2 = System.nanoTime()

            Timber.d(
                String.format(
                    "Received response for %s in %.1fms%n%s",
                    response.request.url, (t2 - t1) / 1e6, response.headers
                )
            )
            return@Interceptor response
        }

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .followRedirects(false)
            .followSslRedirects(false)
            .build()
    }


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Const.BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiClient(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }
}
