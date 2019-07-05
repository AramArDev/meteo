package com.example.meteo

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity;
import com.example.meteo.App.Companion.weatherService
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_chercher.setOnClickListener { view ->
            getMeteo(edit_ville.text.toString())
        }
    }

    // methode pour recuperer la meteo en fonction de la ville
    fun getMeteo(ville: String) {
        val call = weatherService.getWeather("${ville},fr")
        call.enqueue(object : Callback<MetoData> {
            override fun onFailure(call: Call<MetoData>?, t: Throwable?) {
                Log.i(TAG, "updateWeatherForCity -> onFailure = ${call.toString()}")
                error()
            }

            override fun onResponse(call: Call<MetoData>?, response: Response<MetoData>?) {
                response?.body()?.let {
                    Log.i(TAG, "updateWeatherForCity -> onResponse")
                    val meteo = meteoDataToMeteo(it)
                    updateUi(meteo)
                }
            }
        })
    }

    fun meteoDataToMeteo(metoData: MetoData): Meteo {
        val weather = metoData.weather.first()
        return Meteo(
            description = weather.description,
            temperature = metoData.main.temp,
            humidite = metoData.main.humidity,
            pression = metoData.main.pressure,
            iconUrl = "https://openweathermap.org/img/w/${weather.icon}.png"
        )
    }

    // mise a jour les views
    fun updateUi(meteo: Meteo) {
        text_ville.text = edit_ville.text
        text_temperature.text = "${meteo.temperature.toInt()} °C"
        text_description.text = meteo.description
        text_humidite.text = "${meteo.humidite.toString()} %"
        text_pression.text = "${meteo.pression.toString()} hPa"

        Picasso.get()
            .load(meteo.iconUrl)
            .placeholder(R.drawable.ic_cloud_off_black_24dp)
            .into(image_meteo)

        relative_layout.visibility = View.VISIBLE
    }

    fun error() {
        Toast.makeText(MainActivity@this, "Un probleme est survenu !!!", Toast.LENGTH_LONG).show()
    }
}
