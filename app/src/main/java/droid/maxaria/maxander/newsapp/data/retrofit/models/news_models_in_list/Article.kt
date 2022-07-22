package droid.maxaria.maxander.newsapp.data.retrofit.models.news_models_in_list

data class Article(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: SourceData,
    val title: String,
    val url: String,
    val urlToImage: String
)