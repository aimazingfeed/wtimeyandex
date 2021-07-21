package com.justai.jaicf.template.scenario
import com.justai.jaicf.template.Coordinate
import org.json.*
import java.net.URL


fun getCoordinate (city: String): Coordinate {
    val url = URL("http://search.maps.sputnik.ru/search?q=$city").readText()
    val data = JSONObject(url)
//    val result = data.getJSONObject("result")
    val result = data.getJSONArray("result")
    val arrayIn = result.getJSONObject(0)
    val position = arrayIn.getJSONObject("position")

    val lat : String = position.getInt("lat").toString()
    val lon : String = position.getInt("lon").toString()
    return Coordinate(lat,lon)
}

fun getTime (lat : String, lon : String): String {
    val url = URL("https://api.sunrise-sunset.org/json?lat=$lat&lng=$lon&date=today").readText()
    val result = JSONObject(url)
    val res = result.getJSONObject("results")
    return "sunrise time is "+ res.getString("sunrise") + "\n" +
            "sunset time is " + res.getString("sunset")
}



//fun getTimeEuro(city: String):String {
////    var cityTime = URL("http://worldtimeapi.org/api/timezone/Europe/$city").readText()
//
//    val cityTime = URL("https://timezoneapi.io/api/timezone/?Europe/$city&token=aFTerCYjbzLTxfAsGmkX").readText()
//    val timeJson = JSONObject(cityTime)
//    val data = timeJson.getJSONObject("data")
//    val datetime = data.getJSONObject("datetime")
//    return datetime.getString("date_time_txt").toString()
//}
//
//fun getTimeAsia(city: String):String {
////    var cityTime = URL("http://worldtimeapi.org/api/timezone/Asia/$city").readText()
//
//    val cityTime = URL("https://timezoneapi.io/api/timezone/?Asia/$city&token=aFTerCYjbzLTxfAsGmkX").readText()
//    val timeJson = JSONObject(cityTime)
//    val data = timeJson.getJSONObject("data")
//    val datetime = data.getJSONObject("datetime")
//    return datetime.getString("date_time_txt").toString()
//}
