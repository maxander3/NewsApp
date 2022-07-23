package droid.maxaria.maxander.newsapp.domain.models.news_model_in_list

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Source(
    val id: String,
    val name: String
):Parcelable