package com.example.myapplication.covid19.repository

import com.example.myapplication.covid19.api.RetrofitInstance

class CovidRepository {


    suspend fun getCovidData(country:String)=
        RetrofitInstance.api.getCovidData(country)




}