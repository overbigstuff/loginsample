package com.shirokov.loginsample.model

import com.google.gson.annotations.SerializedName

class CityWeather {
    @SerializedName("name")
    var name: String = ""

    @SerializedName("main")
    var weather: Weather? = null

    override fun toString(): String {
        return name + ", " + weather?.toString()
    }

}

class Weather {
    @SerializedName("temp")
    var temp: Float = 0.0F

    override fun toString(): String {
        return if (temp > 0) "+$temp" else "$temp"
    }
}