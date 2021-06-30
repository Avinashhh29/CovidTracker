package com.example.myapplication.covid19.api

import com.example.myapplication.covid19.models.CovidResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CovidAPI {

    @GET("/v3/covid-19/countries/{country}")
    suspend fun getCovidData(
        @Path("country")
        country:String
    ):Response<CovidResponse>


}