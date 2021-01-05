package com.grosalex.weathercompose.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.grosalex.weathercompose.WeatherQuery
import com.grosalex.weathercompose.type.ConfigInput
import com.grosalex.weathercompose.type.Language
import com.grosalex.weathercompose.type.Unit

object WeatherRepository {
    private val apolloClient = ApolloClient.builder()
        .serverUrl("https://graphql-weather-api.herokuapp.com")
        .build()

    private val configInput = ConfigInput(
        units = Input.optional(Unit.METRIC),
        lang = Input.optional(Language.FR)
    )

    suspend fun getCityByName(name: String): WeatherQuery.GetCityByName? {
        return apolloClient.query(WeatherQuery(name = name, config = configInput)).await().data?.getCityByName
    }
}