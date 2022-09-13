package droid.maxaria.maxander.newsapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import droid.maxaria.maxander.newsapp.data.NewsRepositoryImpl
import droid.maxaria.maxander.newsapp.data.retrofit.ApiProvider
import droid.maxaria.maxander.newsapp.domain.NewsRepository

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {
    @Provides
    fun provideRepository(apiProvider: ApiProvider):NewsRepository{
        return  NewsRepositoryImpl(apiProvider)
    }
}