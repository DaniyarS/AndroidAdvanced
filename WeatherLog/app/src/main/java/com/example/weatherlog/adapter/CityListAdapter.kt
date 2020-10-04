package com.example.weatherlog.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherlog.R
import com.example.weatherlog.model.Weather
import kotlinx.android.synthetic.main.weather_item.view.*

class CityListAdapter (
    var weatherData: List<Weather>? = null,
    val itemClickListener: RecyclerViewItemClick? = null
) : RecyclerView.Adapter<CityListAdapter.WeatherViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CityListAdapter.WeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weather_item, parent, false)
        return WeatherViewHolder(view)
    }

    override fun getItemCount(): Int {
        return weatherData?.size ?: 0
    }

    override fun onBindViewHolder(holder: CityListAdapter.WeatherViewHolder, position: Int) {
        weatherData?.get(position)?.let { holder.bind(it) }
    }

    inner class WeatherViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

        private val cityName = itemView.tvCityName
        private val weatherIndex = itemView.tvWeatherIndex
        private val tempIndex = itemView.tvTemperatureIndex
        private val timeIndex = itemView.tvTimeIndex

        fun bind(weatherData: Weather) {
            cityName.text = weatherData.cityName
            weatherIndex.text = weatherData.weather
            tempIndex.text = weatherData.temp
            timeIndex.text = weatherData.time

            view.setOnClickListener {
                itemClickListener?.itemClick(adapterPosition, weatherData)
            }
        }
    }

    interface RecyclerViewItemClick {
        fun itemClick(position: Int, item: Weather?)
    }
}