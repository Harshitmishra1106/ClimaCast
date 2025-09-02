package com.example.climacast

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.climacast.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn.setOnClickListener{
            if(binding.cityName.text.toString().isEmpty()){
                Toast.makeText(this, "Please fill the city name", Toast.LENGTH_SHORT).show()
            }
            else{
                val intent = Intent(this, MainActivity2::class.java)

                intent.putExtra("CityName", binding.cityName.text.toString())

                startActivity(intent)
            }
        }
    }
}