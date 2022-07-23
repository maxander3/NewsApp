package droid.maxaria.maxander.newsapp.domain.country_model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CountryModel(
    val country: String,
    val name: String,
    val state: String
):Parcelable
