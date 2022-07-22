package droid.maxaria.maxander.newsapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private val fusedLocationClient by lazy { LocationServices.getFusedLocationProviderClient(this) }

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //-----------------------------------------------------------------
        checkPermissionsGps()
        getLastKnownLocation()
        observeViewModel()
        //-----------------------------------------------------------------
    }
    private fun observeViewModel(){
        viewModel.newsList.observe(this) {
            Log.d("TAG", it.toString())
        }
        viewModel.country.observe(this) {
            viewModel.country.value?.let { countryModel ->
                viewModel.getNewsList(countryModel.country.lowercase())
            }
        }
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
                    viewModel.getCountryByCord(location.latitude,location.longitude)
                }
            }
    }

    companion object {
        private const val GEO_LOCATION_REQUEST_COD_SUCCESS = 1
    }
}