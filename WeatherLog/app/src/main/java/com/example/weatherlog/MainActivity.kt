package com.example.weatherlog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherlog.adapter.CityListAdapter
import com.example.weatherlog.model.Weather
import com.example.weatherlog.viewmodel.ViewModelProviderFactory

import com.example.weatherlog.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.activity_main.*

const val WEATHER_KEY = "weatherData"
class MainActivity : AppCompatActivity(), CityListAdapter.RecyclerViewItemClick {

    lateinit var weatherViewModel: WeatherViewModel
    private  lateinit var viewModelFactory: ViewModelProviderFactory
    private lateinit var cityListAdapter: CityListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)

        cityListAdapter = CityListAdapter(itemClickListener = this)
        recyclerView.adapter = cityListAdapter

        viewModelFactory = ViewModelProviderFactory(application)
        weatherViewModel = ViewModelProvider(this, viewModelFactory).get(WeatherViewModel::class.java)
        weatherViewModel.getAllWeather().observe(this, Observer{
            cityListAdapter.weatherData = it
            cityListAdapter.notifyDataSetChanged()
        })

        floatingSaveButton.setOnClickListener {
            weatherViewModel.getWeather()
        }
    }

    override fun itemClick(position: Int, item: Weather?) {
        val intent = Intent(this, WeatherDetailsActivity::class.java)
        intent.putExtra(WEATHER_KEY, item)
        startActivity(intent)
    }
}