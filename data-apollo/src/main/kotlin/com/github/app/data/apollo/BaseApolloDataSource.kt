package com.github.app.data.apollo

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Query

/**
 * Base class for Apollo DataSources.
 *
 * This class provides a common [executeQuery] method to interact with the ApolloClient.
 *
 * @param apolloClient The ApolloClient instance to be used for making GraphQL requests.
 */
internal open class BaseApolloDataSource(
    private val apolloClient: ApolloClient,
) {
    protected suspend fun <T : Query.Data> executeQuery(query: Query<T>): T = apolloClient
        .query(query)
        .execute()
        .dataAssertNoErrors
}
