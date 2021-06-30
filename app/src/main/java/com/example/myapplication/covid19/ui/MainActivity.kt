package com.example.myapplication.covid19.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.covid19.databinding.ActivityMainBinding
import com.example.myapplication.covid19.repository.CovidRepository
import com.example.myapplication.covid19.util.Resource
import com.hbb20.CountryCodePicker
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel

class MainActivity : AppCompatActivity() {


    private lateinit var binding:ActivityMainBinding
    lateinit var viewModel: CovidViewModel
    private var TAG ="MainActivity"
    lateinit var country:String
    lateinit var covidPieChart: PieChart
    override fun onCreate(savedInstanceState: Bundle?) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val covidRepository=CovidRepository()
        val viewModelProviderFactory = CovidViewModelProviderFactory(covidRepository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(CovidViewModel::class.java)


        covidPieChart=binding.pieChart
        binding.countryPicker.setAutoDetectedCountry(true)
        country =binding.countryPicker.selectedCountryName
        viewModel.getCovidData(country)
        binding.countryPicker.setOnCountryChangeListener(CountryCodePicker.OnCountryChangeListener {
            country=binding.countryPicker.selectedCountryName
            viewModel.getCovidData(country)


        })

        viewModel.covidData.observe(this, Observer { response->
            when(response){
                is Resource.Success->{
                    hideProgressBar()
                    response.data?.let {
                       binding.RCount.text= it.recovered.toString()
                        binding.TCount.text= it.cases.toString()
                        binding.ACount.text= it.active.toString()
                        binding.dCount.text= it.deaths.toString()


                        val activeGraph= it.active
                        val deathGraph= it.deaths
                        val totalGraph= it.cases
                        val recoveredGraph= it.recovered
                        updateGraphFun(activeGraph,deathGraph,totalGraph,recoveredGraph)


                    }
                }
                is Resource.Error->{
                    hideProgressBar()
                    Intent(this, NoCountryActivity::class.java).also { startActivity(it)  }
                    response.data?.let { message->
                        Log.e(TAG,"An error occurred $message")
                    }
                }
                is Resource.Loading->{
                    showProgressBar()
                }
            }

        })

    }

    private fun updateGraphFun(activeGraph: Int, deathGraph: Int, totalGraph: Int, recoveredGraph: Int) {

        covidPieChart.clearChart()
        covidPieChart.addPieSlice(PieModel("Deaths", deathGraph.toFloat(),Color.parseColor("#FFA1A1")))
        covidPieChart.addPieSlice(PieModel("Recovered", recoveredGraph.toFloat(),Color.parseColor("#A1D2FF")))
        covidPieChart.addPieSlice(PieModel("Active", activeGraph.toFloat(),Color.parseColor("#85F8C7")))
        covidPieChart.addPieSlice(PieModel("Cases", totalGraph.toFloat(),Color.parseColor("#FBFD79")))
        covidPieChart.startAnimation()

    }


    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility=View.INVISIBLE
    }
    private fun showProgressBar() {
        binding.paginationProgressBar.visibility=View.VISIBLE
    }


}















