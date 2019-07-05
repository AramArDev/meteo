package com.example.meteo

// pour recuperation des donnees de la openweathermap
data class MetoData(val weather: Array<WeatherData>,
                    val main: MainData)

data class WeatherData(val description: String,
                  val icon: String)


data class MainData(val temp: Float,
                    val pressure: Int,
                    val humidity: Int)



// pour utilisation dans le programme
data class Meteo(val description: String,
                 val temperature: Float,
                 val humidite: Int,
                 val pression: Int,
                 val iconUrl: String)
