package droid.maxaria.maxander.newsapp.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import droid.maxaria.maxander.newsapp.R
import droid.maxaria.maxander.newsapp.databinding.FragmentParentFragmentBinding
import droid.maxaria.maxander.newsapp.domain.country_model.CountryModel
import droid.maxaria.maxander.newsapp.domain.models.news_model_in_list.NewsModel
import java.lang.RuntimeException

class ParentFragment : Fragment() {
    private lateinit var adapter: ViewPagerAdapter
    //TODO di_________________________________________________________
    private val viewModel: ParentFragmentViewModel by viewModels()
    private var _binding:FragmentParentFragmentBinding? = null
    private val binding:FragmentParentFragmentBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        parseArgs()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentParentFragmentBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.currentNewsTag.observe(viewLifecycleOwner){
            apiCall(it)
        }
        viewModel.newsList.observe(viewLifecycleOwner){
            initAdapter(it)
        }
    }



    private fun initAdapter(list: List<NewsModel>){
        adapter = ViewPagerAdapter(this)
        adapter.setNewsList(list)
        binding.viewPager.adapter = adapter
    }

    private fun apiCall(tag:String){
        viewModel.getNewsList(tag)
    }

    private fun parseArgs(){
        arguments?.getParcelable<CountryModel>(COUNTRY_KEY).let {
            viewModel.currentNewsTag.value = it?.country ?:throw RuntimeException("Can't detect location")
        }
    }

    companion object{
        private const val COUNTRY_KEY = "country key"
        fun getInstance(country: CountryModel):ParentFragment{
            return ParentFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(COUNTRY_KEY,country)
                }
            }
        }
    }
}