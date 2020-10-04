package com.example.weatherlog.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherlog.R
import com.example.weatherlog.databinding.WeatherItemBinding
import com.example.weatherlog.model.Weather
import com.example.weatherlog.model.WeatherData
import kotlinx.android.synthetic.main.weather_item.view.*

class CityListAdapter (
    var weatherData: List<Weather>? = null,
    val itemClickListener: RecyclerViewItemClick? = null
) : RecyclerView.Adapter<CityListAdapter.WeatherViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CityListAdapter.WeatherViewHolder =
        WeatherViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.weather_item,
                parent,
                false
            )
        )

    override fun getItemCount(): Int {
        return weatherData?.size ?: 0
    }

    override fun onBindViewHolder(holder: CityListAdapter.WeatherViewHolder, position: Int) {
        holder.weatherItemBinding.weatherData = weatherData?.get(position)!!
        holder.itemView.setOnClickListener {
            itemClickListener?.itemClick(position, weatherData!![position])
        }
    }

    inner class WeatherViewHolder(val weatherItemBinding: WeatherItemBinding): RecyclerView.ViewHolder(weatherItemBinding.root)

    interface RecyclerViewItemClick {
        fun itemClick(position: Int, item: Weather?)
    }
}