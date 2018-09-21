package me.rankov.hth.country

import me.rankov.hth.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class CountriesListLoadInteractor {

    val client = OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG)
                    HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })
            .build()

    val retrofit = Retrofit.Builder()
            .baseUrl("https://restcountries.eu")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val countriesService = retrofit.create(CountriesService::class.java)

    class CountriesResponse(val name: String, val flag: String)

    interface CountriesService {
        @GET("/rest/v2/all")
        fun getAllCountries(): Call<List<CountriesResponse>>
    }

    fun loadCountries(callback: (List<Country>) -> Unit) {
        val call = countriesService.getAllCountries()
        call.enqueue(object : Callback<List<CountriesResponse>> {
            override fun onFailure(call: Call<List<CountriesResponse>>, t: Throwable) {
                println(t)
            }

            override fun onResponse(call: Call<List<CountriesResponse>>, response: Response<List<CountriesResponse>>) {
                val countries = mutableListOf<Country>()
                response.body()?.forEach {
                    countries.add(Country(it.name, it.flag))
                }
                callback(countries)
            }
        })
    }
}