package com.justai.jaicf.template.scenario
import org.json.*
import java.net.URL

fun getTimeEuro(city: String):String {
//    var cityTime = URL("http://worldtimeapi.org/api/timezone/Europe/$city").readText()

    val cityTime = URL("https://timezoneapi.io/api/timezone/?Europe/$city&token=aFTerCYjbzLTxfAsGmkX").readText()
    val timeJson = JSONObject(cityTime)
    val data = timeJson.getJSONObject("data")
    val datetime = data.getJSONObject("datetime")
    return datetime.getString("date_time_txt").toString()
}

fun getTimeAsia(city: String):String {
//    var cityTime = URL("http://worldtimeapi.org/api/timezone/Asia/$city").readText()

    val cityTime = URL("https://timezoneapi.io/api/timezone/?Asia/$city&token=aFTerCYjbzLTxfAsGmkX").readText()
    val timeJson = JSONObject(cityTime)
    val data = timeJson.getJSONObject("data")
    val datetime = data.getJSONObject("datetime")
    return datetime.getString("date_time_txt").toString()
}
