package me.rankov.kaboom.country.details

import me.rankov.kaboom.BaseInteractor
import me.rankov.kaboom.country.Country
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

class CountryDetailsInteractor : BaseInteractor("https://7nx0ovlz52.execute-api.us-west-2.amazonaws.com") {
    private val countryActionService = retrofit.create(CountryActionService::class.java)

    interface CountryActionService {
        @Headers("x-api-key:P26oxaGPlH2bB4ZIS5HVA1OSIt7aZQMo1onBsJXe")
        @POST("/Production/actions")
        fun callAction(@Body action: CountryAction): Call<ResponseBody>
    }

    fun attack(country: Country) {
        performAction(country, "0")
    }

    fun heal(country: Country) {
        performAction(country, "1")
    }

    private fun performAction(country: Country, action: String) {
        val countryAction = countryAction(country)
        countryAction.action = action
        val call = countryActionService.callAction(countryAction)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                println(t)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                println(response.message())
                //TODO: handle callback, process errors
            }

        })
    }

    private fun countryAction(country: Country): CountryAction {
        val countryAction = CountryAction()
        countryAction.timestamp = System.currentTimeMillis().toString()
        countryAction.target = country.latlng.joinToString()
        //TODO: add the other action parameters (user id etc..)
        return countryAction
    }

}
