package com.justai.jaicf.template.scenario
import com.justai.jaicf.template.Coordinate
import org.json.*
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


fun getCoordinate (city: String): Coordinate {
    var url = URL("http://search.maps.sputnik.ru/search?q=$city").readText()
    val data = JSONObject(url)
    val result = data.getJSONArray("result")
    val arrayIn = result.getJSONObject(0)
    val position = arrayIn.getJSONObject("position")
    val lat : String = position.getDouble("lat").toString()
    val lon : String = position.getDouble("lon").toString()
    url = URL("https://maps.googleapis.com/maps/api/timezone/json?timeZoneName&location=$lat,$lon&timestamp=1331161200&key=AIzaSyCRb2M6zmsZlz5vqDSVhRjZShvUat6tqjc").readText()
    val timeZoneName = JSONObject(url).getString("timeZoneId")
    return Coordinate(lat,lon,timeZoneName)
}

fun getTime (lat : String, lon : String, timeZoneName : String): String {
    val url = URL("https://api.sunrise-sunset.org/json?lat=$lat&lng=$lon&date=today").readText()
    val result = JSONObject(url)
    val res = result.getJSONObject("results")
    val timeSet = res.getString("sunset")
    val convertedTimeSet = convertToCustomFormat(timeSet, timeZoneName)
    val timeRise = res.getString("sunrise")
    val convertedTimeRise = convertToCustomFormat(timeRise, timeZoneName)
    return "Время восхода "+ convertedTimeRise + "\n" +
            "Время заката " + convertedTimeSet
}

fun convertToCustomFormat(dateStr: String?, timeZoneName: String?): String {
    val utc = TimeZone.getTimeZone("UTC")
    val sourceFormat = SimpleDateFormat("hh:mm:ss a")
    val destFormat = SimpleDateFormat("HH:mm:ss")
    sourceFormat.timeZone = utc
    destFormat.timeZone = TimeZone.getTimeZone(timeZoneName)
    val convertedDate = sourceFormat.parse(dateStr)
    val result = destFormat.format(convertedDate)
    return result
}

//fun getTimeEuro(city: String):String {
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
