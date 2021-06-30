package com.example.myapplication.covid19.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.covid19.models.CovidResponse
import com.example.myapplication.covid19.repository.CovidRepository
import com.example.myapplication.covid19.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class CovidViewModel(
    private  val covidRepository: CovidRepository
):ViewModel(){


    val covidData : MutableLiveData<Resource<CovidResponse>> = MutableLiveData()



    fun getCovidData(country:String) = viewModelScope.launch {
        covidData.postValue(Resource.Loading())
        val response = covidRepository.getCovidData(country)
        covidData.postValue(handleCovidResponse(response))

    }

    private fun handleCovidResponse(response: Response<CovidResponse>) : Resource<CovidResponse>{

    if(response.isSuccessful){
        response.body()?.let {
            return Resource.Success(it)
        }
    }
        return  Resource.Error(response.message())


    }



}