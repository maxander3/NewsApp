package droid.maxaria.maxander.newsapp.data.retrofit.models.news_models_in_list

data class NewsModelInList(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)