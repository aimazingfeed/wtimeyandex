package com.justai.jaicf.template.methods
import com.justai.jaicf.template.classes.Coordinate
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


fun getCoordinate (city: String): Coordinate {
    var url = URL("http://search.maps.sputnik.ru/search?q=$city").readText()
    val data = JSONObject(url)
    val result = data.getJSONArray("result")
    val arrayIn = result.getJSONObject(0)
    val position = arrayIn.getJSONObject("position")
    val lat : String = position.getDouble("lat").toString()
    val lon : String = position.getDouble("lon").toString()
    url = URL("https://maps.googleapis.com/maps/api/timezone/json?timeZoneName&" +
            "location=$lat,$lon&timestamp=1331161200&key=AIzaSyCRb2M6zmsZlz5vqDSVhRjZShvUat6tqjc").readText()
    val timeZoneName = JSONObject(url).getString("timeZoneId")
    val cityName = arrayIn.getString("display_name")
    return Coordinate(lat,lon,timeZoneName, cityName)
}

fun getTime (lat: String, lon: String, timeZoneName: String, cityName: String?): String {
    val url = URL("https://api.sunrise-sunset.org/json?lat=$lat&lng=$lon&date=today").readText()
    val result = JSONObject(url)
    val res = result.getJSONObject("results")
    val timeSet = res.getString("sunset")
    val convertedTimeSet = convertToCustomFormat(timeSet, timeZoneName)
    val timeRise = res.getString("sunrise")
    val convertedTimeRise = convertToCustomFormat(timeRise, timeZoneName)
    return "$cityName \n" +
            "Время восхода — "+ convertedTimeRise + "\n" +
            "Время заката — " + convertedTimeSet
}

fun convertToCustomFormat(dateStr: String?, timeZoneName: String?): String {
    val utc = TimeZone.getTimeZone("UTC")
    val cityTime = TimeZone.getTimeZone(timeZoneName)
    val now = LocalDate.now().toString()
    val date = dateStr +" "+ now
    val sourceFormat = SimpleDateFormat("hh:mm:ss a yyyy-MM-dd")
    val destFormat = SimpleDateFormat("HH:mm")
    sourceFormat.timeZone = utc
    destFormat.timeZone = cityTime
    val convertedDate = sourceFormat.parse(date)
    val result = destFormat.format(convertedDate)
    return result
}


