package droid.maxaria.maxander.newsapp.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import droid.maxaria.maxander.newsapp.R
import droid.maxaria.maxander.newsapp.databinding.ActivityMainBinding
import droid.maxaria.maxander.newsapp.domain.country_model.CountryModel
import droid.maxaria.maxander.newsapp.presentation.fragments.NewsFragment
import droid.maxaria.maxander.newsapp.presentation.fragments.ParentFragment
import droid.maxaria.maxander.newsapp.presentation.fragments.WebViewFragment
@AndroidEntryPoint
class MainActivity : AppCompatActivity(),NewsFragment.OnImgClickListener {

    private val viewModel:MainViewModel by viewModels()
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!

    private val fusedLocationClient by lazy { LocationServices.getFusedLocationProviderClient(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPermissionsGps()
        getLastKnownLocation()
        observeViewModel()
        binding.searchBtn.setOnClickListener {
            val tag = binding.searchEditTxt.text.toString()
            launchParentFragmentByTag(tag)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun observeViewModel() {
        viewModel.error.observe(this) {
            Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show()
        }
        viewModel.country.observe(this) {
            viewModel.country.value?.let { countryModel ->
                binding.countryBtn.text = countryModel.country
                launchParentFragmentByCountry(countryModel)
            }
        }
    }
    private fun launchParentFragmentByTag(tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(binding.mainFragmentContainer.id, ParentFragment.getInstanceByTag(tag,viewModel.country.value!!))
            .commit()
    }

    private fun launchParentFragmentByCountry(country: CountryModel) {
        supportFragmentManager.beginTransaction()
            .replace(binding.mainFragmentContainer.id, ParentFragment.getInstanceByCountry(country))
            .commit()
    }
    private fun launchWebViewFragment(url: String) {
        supportFragmentManager.beginTransaction()
            .replace(binding.mainFragmentContainer.id, WebViewFragment.getInstance(url))
            .addToBackStack(null)
            .commit()
    }

    private fun checkPermissionsGps() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                GEO_LOCATION_REQUEST_COD_SUCCESS)
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                GEO_LOCATION_REQUEST_COD_SUCCESS)
        }
    }

    @SuppressLint("MissingPermission")
    fun getLastKnownLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    viewModel.getCountryByCord(location.latitude, location.longitude)
                }
            }
    }

    companion object {
        private const val GEO_LOCATION_REQUEST_COD_SUCCESS = 1
    }

    override fun onClick(url: String) {
        launchWebViewFragment(url)
    }


}