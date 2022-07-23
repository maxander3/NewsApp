package droid.maxaria.maxander.newsapp.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.LocationServices
import droid.maxaria.maxander.newsapp.R
import droid.maxaria.maxander.newsapp.databinding.ActivityMainBinding
import droid.maxaria.maxander.newsapp.domain.country_model.CountryModel
import droid.maxaria.maxander.newsapp.presentation.fragments.ParentFragment

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    private var _binding: ActivityMainBinding? = null
    val binding: ActivityMainBinding
        get() = _binding!!

    private val fusedLocationClient by lazy { LocationServices.getFusedLocationProviderClient(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //-----------------------------------------------------------------
        checkPermissionsGps()
        getLastKnownLocation()
        observeViewModel()
        //-----------------------------------------------------------------
        binding.searchBtn.setOnClickListener {
            val fragment = (supportFragmentManager
                .findFragmentById(binding.mainFragmentContainer.id) as ParentFragment)
            fragment.searchNewsByTag()
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
                launchParentFragment(countryModel)
            }
        }
    }

    private fun launchParentFragment(country: CountryModel) {
        supportFragmentManager.beginTransaction()
            .replace(binding.mainFragmentContainer.id, ParentFragment.getInstance(country))
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
}