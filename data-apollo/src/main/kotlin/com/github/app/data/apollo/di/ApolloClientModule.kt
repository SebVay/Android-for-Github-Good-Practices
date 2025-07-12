package com.github.app.data.apollo.di

import com.apollographql.apollo.ApolloClient
import com.github.app.data.apollo.BuildConfig
import com.github.app.data.apollo.R
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal val apolloClientModule = module {
    single<ApolloClient> {
        val serverUrl = get<ServerUrl>()
        val headerAuth = get<HeaderXToken>()

        ApolloClient.Builder()
            .serverUrl(serverUrl.value)
            .addHttpHeader(HEADER_AUTH, headerAuth.value)
            .build()
    }

    single { ServerUrl(androidContext().getString(R.string.server_url)) }

    single { HeaderXToken("Bearer " + BuildConfig.GITHUB_TOKEN) }
}

@JvmInline
private value class ServerUrl(val value: String)

@JvmInline
internal value class HeaderXToken(val value: String)

private const val HEADER_AUTH = "Authorization"
