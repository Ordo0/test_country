package com.kslv.testcountry

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.kslv.testcountry.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btSearch.setOnClickListener {
            val countryName = binding.edtInputCountryName.text.toString()

            lifecycleScope.launch {
                try {
                    val countries = restCountriesApi.getCountryByName(countryName)
                    val country = countries[0]

                    binding.tvCountryName.text = country.name
                    binding.tvCapital.text = country.capital
                    binding.tvPopulation.text =
                        NumberFormat.getInstance().format(country.population)
                    binding.tvArea.text = NumberFormat.getInstance().format(country.area)
                    binding.tvLanguages.text = country.languages.joinToString { it.name }

                    loadSvg(binding.ivFlag, country.flag)

                    binding.clResult.visibility = View.VISIBLE
                    binding.clStatus.visibility = View.INVISIBLE
                } catch (e: Exception) {
                    binding.tvStatus.text = "Страна не найдена"
                    binding.ivStatus.setImageResource(R.drawable.ic_baseline_error_24)
                    binding.clResult.visibility = View.INVISIBLE
                    binding.clStatus.visibility = View.VISIBLE
                }
            }
        }
    }
}