package com.grosalex.weathercompose.repository

import com.apollographql.apollo.ApolloClient

object WeatherRepository {
    private val apolloClient = ApolloClient.builder()
        .serverUrl("https://graphql-weather-api.herokuapp.com")
        .build()

    suspend fun getCityByName(name:String, country:String? = null){
        apolloClient.
    }
}