package droid.maxaria.maxander.newsapp.presentation.fragments

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import droid.maxaria.maxander.newsapp.domain.models.news_model_in_list.NewsModel

class ViewPagerAdapter(fragment:Fragment):FragmentStateAdapter(fragment) {
    private var _list:List<NewsModel> = emptyList()
    val list:List<NewsModel>
        get() = _list


    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment {
        return NewsFragment.getInstance(list[position])
    }

    fun setNewsList(newList: List<NewsModel>){
        _list = newList
        notifyDataSetChanged()
    }
}