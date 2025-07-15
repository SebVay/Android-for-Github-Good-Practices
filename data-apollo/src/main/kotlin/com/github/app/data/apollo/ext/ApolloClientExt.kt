package com.github.app.data.apollo.ext

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Query
import com.apollographql.apollo.exception.ApolloException

/**
 * Executes an Apollo query and returns the data, asserting no errors.
 *
 * This is a suspending extension function for `ApolloClient`.
 * It simplifies the common pattern of executing a query and immediately
 * accessing its data, while also ensuring that the GraphQL response
 * does not contain any errors.
 * If errors are present, it will throw an exception.
 *
 * @param T The type of the `Query.Data` expected as a result.
 * @param query The `Query` instance to execute.
 * @return The `data` part of the GraphQL response.
 * @throws ApolloException if the network request fails or if the GraphQL response contains errors.
 */
internal suspend fun <T : Query.Data> ApolloClient.executeQuery(query: Query<T>): T {
    return query(query)
        .execute()
        .dataAssertNoErrors
}
