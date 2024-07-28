package com.example.todaynan.utils

import android.content.Context
import com.example.todaynan.data.entity.Location
import com.opencsv.CSVReader
import java.io.InputStream
import java.io.InputStreamReader


object CSVUtils {

    fun readLocations(
        context: Context,
        fileName: String
    ): List<Location> {
        val locations = mutableListOf<Location>()
        try {
            // CSV 파일을 assets 폴더에서 읽어온다
            val inputStream: InputStream = context.assets.open(fileName)
            val reader = CSVReader(InputStreamReader(inputStream))
            var nextLine: Array<String>?

            while (reader.readNext().also { nextLine = it } != null) {
                // CSV 파일의 각 줄에서 도시, 구, 동 정보를 추출한다
                if (nextLine!!.size >= 3) {
                    val cityName = nextLine!![0]
                    val districtName = nextLine!![1]
                    val dongName = nextLine!![2]
                    locations.add(Location(cityName, districtName, dongName))
                }
            }
            reader.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return locations
    }

}