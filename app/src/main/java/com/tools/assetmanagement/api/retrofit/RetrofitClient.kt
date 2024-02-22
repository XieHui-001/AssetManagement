package com.tools.assetmanagement.api.retrofit

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.tools.assetmanagement.App
import com.tools.assetmanagement.BuildConfig
import com.tools.assetmanagement.api.ApiService
import com.tools.assetmanagement.api.interceptor.LoggingInterceptor

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.Proxy
import java.util.concurrent.TimeUnit

private val cookieJar: PersistentCookieJar by lazy {
    PersistentCookieJar(
        SetCookieCache(),
        SharedPrefsCookiePersistor(App.instance)
    )
}

val okHttpClient: OkHttpClient by lazy {
    OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .proxy(Proxy.NO_PROXY)  //禁止使用代理，防止Fiddler抓包
        .cookieJar(cookieJar)
        .addInterceptor(LoggingInterceptor())
        .sslSocketFactory(SSLContextSecurity.createIgnoreVerifySSL("TLS"))
        .build()
}

private val retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BuildConfig.API_DOMAIN)
    .build()

val apiService: ApiService by lazy { retrofit.create(ApiService::class.java) }