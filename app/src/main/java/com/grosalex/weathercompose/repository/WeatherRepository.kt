package com.grosalex.weathercompose.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.grosalex.weathercompose.WeatherQuery

object WeatherRepository {
    private val apolloClient = ApolloClient.builder()
        .serverUrl("https://graphql-weather-api.herokuapp.com")
        .build()

    suspend fun getCityByName(name:String, country:String? = null): WeatherQuery.GetCityByName? {
        val result = apolloClient.query(WeatherQuery(name = name)).await().data?.getCityByName
        return result
    }
}