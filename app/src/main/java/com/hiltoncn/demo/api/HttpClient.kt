package com.hiltoncn.demo.api

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.interceptor.ApolloInterceptor
import com.apollographql.apollo3.network.okHttpClient
import okhttp3.OkHttpClient

object HttpClient {
    private var apolloClient: ApolloClient? = null

    fun client(): ApolloClient? {
        return apolloClient
    }

    /**
     *  Initialization HttpClient
     */
    fun initClient() {
        if (apolloClient != null) {
            return
        }
        apolloClient = ApolloClient.Builder()
            .okHttpClient(OkHttpClient().newBuilder().build())
            .serverUrl(ApiConst.BASE_URL).build()
    }
}