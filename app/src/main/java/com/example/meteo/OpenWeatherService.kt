package com.example.meteo

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "8b5f20c98cf561c7e04da277b22617d0"

// pour recuperer la meteo depuis le webservice openweathermap
interface OpenWeatherService {

    // https://api.openweathermap.org/data/2.5/weather?units=metric&lang=fr&q=Paris,fr&appid=8b5f20c98cf561c7e04da277b22617d0
    @GET("data/2.5/weather?units=metric&lang=fr")
    fun getWeather(@Query("q") cityName: String,
                   @Query("appid") apiKey: String = API_KEY) : Call<MetoData>

}