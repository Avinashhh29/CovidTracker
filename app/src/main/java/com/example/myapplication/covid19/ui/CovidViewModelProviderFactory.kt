package com.example.myapplication.covid19.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.covid19.repository.CovidRepository

class CovidViewModelProviderFactory(
    private val covidRepository: CovidRepository
):ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CovidViewModel(covidRepository) as T

    }


}