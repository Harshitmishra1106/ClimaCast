package com.example.climacast

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.climacast.dataModels.WeatherModel
import com.example.climacast.databinding.ActivityMain2Binding
import com.example.climacast.interfaceApi.WeatherService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private val apiKey = "e6bd8dabc0ea5a62c9e4098f901eef46"
    private lateinit var weatherService: WeatherService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)

        setContentView(binding.root)
        val cityName = intent.getStringExtra("CityName")
        weatherService = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherService::class.java)

        binding.textViewCity.text = cityName.toString()

        val response = weatherService.getWeather(cityName.toString(), apiKey, "metric")
        response.enqueue(object: Callback<WeatherModel> {
            override fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>) {
                val responseBody = response.body()
                if(response.isSuccessful && responseBody != null){
                    val temp = responseBody.main.temp
                    val humidity = responseBody.main.humidity
                    val windSpeed  = responseBody.wind.speed
                    val sunrise  = responseBody.sys.sunrise
                    val sunset  = responseBody.sys.sunset
                    val seaLevel  = responseBody.main.pressure
                    val tempMax = responseBody.main.temp_max
                    val tempMin = responseBody.main.temp_min
                    val condition = responseBody.weather.firstOrNull()?.main?: "unknown"

                    binding.textViewTemperature.text = "$temp°C"
                    binding.humidity.text = "$humidity°C"
                    binding.wind.text = "$windSpeed m/s"
                    binding.sunrise.text = "$sunrise"
                    binding.sunset.text = "$sunset"
                    binding.sea.text = "$seaLevel hPa"
                    binding.maxTemp.text = "$tempMax°C"
                    binding.minTemp.text = "$tempMin°C"
                    binding.condition.text = "$condition"
                }
            }

            override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
                Toast.makeText(this@MainActivity2, "Unable to get Information", Toast.LENGTH_SHORT).show()
            }

        })


        binding.btn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}