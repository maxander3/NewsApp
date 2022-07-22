package droid.maxaria.maxander.newsapp.domain.models.news_model_in_list

data class NewsModel(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
)