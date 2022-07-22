package droid.maxaria.maxander.newsapp.data.retrofit.models.city_model

data class CityModelItem(
    val country: String,
    val lat: Double,
    val local_names: LocalNames,
    val lon: Double,
    val name: String,
    val state: String
)