package me.rankov.kaboom.country.list

import me.rankov.kaboom.BaseInteractor
import me.rankov.kaboom.country.Country
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

class CountriesListLoadInteractor : BaseInteractor("https://restcountries.eu") {
    private val countriesService = retrofit.create(CountriesService::class.java)

    interface CountriesService {
        @GET("/rest/v2/all")
        fun getAllCountries(@QueryMap options: Map<String, String>): Call<List<Country>>
    }

    fun loadCountries(callback: (List<Country>) -> Unit) {
        val map = HashMap<String, String>()
        map["fields"] = "name;flag;population;latlng"
        val call = countriesService.getAllCountries(map)
        call.enqueue(object : Callback<List<Country>> {
            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                println(t)
            }

            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                val countries = mutableListOf<Country>()
                response.body()?.forEach {
                    countries.add(Country(it.name, it.flag, it.population, it.latlng))
                }
                callback(countries)
            }
        })
    }
}