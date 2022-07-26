package droid.maxaria.maxander.newsapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import droid.maxaria.maxander.newsapp.databinding.FragmentParentFragmentBinding
import droid.maxaria.maxander.newsapp.domain.country_model.CountryModel
import droid.maxaria.maxander.newsapp.domain.models.news_model_in_list.NewsModel
import java.lang.RuntimeException
import javax.inject.Inject

@AndroidEntryPoint
class ParentFragment : Fragment() {
    @Inject lateinit var adapter: ViewPagerAdapter

    private val viewModel: ParentFragmentViewModel by viewModels()

    private var _binding:FragmentParentFragmentBinding? = null
    private val binding:FragmentParentFragmentBinding
        get() = _binding!!

    private var currentMode:String = COUNTRY_MODE

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

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun observeViewModel(){
        viewModel.error.observe(viewLifecycleOwner){
            Toast.makeText(context,it,Toast.LENGTH_LONG).show()
        }
        viewModel.currentCountry.observe(viewLifecycleOwner){
            apiCall(it)
        }
        viewModel.newsList.observe(viewLifecycleOwner){
            initAdapter(it)
        }
    }



    private fun initAdapter(list: List<NewsModel>){
        adapter.setNewsList(list)
        binding.viewPager.adapter = adapter
    }

    private fun apiCall(country:String){
        when (currentMode) {
            TAG_MODE -> viewModel.getNewsListByTag(viewModel.currentNewsTag.value!!,country)
            COUNTRY_MODE -> viewModel.getNewsListByCountry(country)
            else -> throw RuntimeException("Unknown mode")
        }
    }

    private fun parseArgs(){
        arguments?.getString(TAG_KEY).let{
            if ( it != null){
                currentMode = TAG_MODE
                viewModel.currentNewsTag.value = it
            }
        }
        arguments?.getParcelable<CountryModel>(COUNTRY_KEY).let {
            viewModel.currentCountry.value = it?.country ?:throw RuntimeException("Can't detect location")
        }
    }

    companion object{
        private const val TAG_KEY = "tag key"
        private const val COUNTRY_KEY = "country key"
        private const val COUNTRY_MODE = "country mode"
        private const val TAG_MODE = "tag mode"
        fun getInstanceByCountry(country: CountryModel):ParentFragment{
            return ParentFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(COUNTRY_KEY,country)
                }
            }
        }
        fun getInstanceByTag(tag: String,country: CountryModel):ParentFragment{
            return ParentFragment().apply {
                arguments = Bundle().apply {
                    putString(TAG_KEY,tag)
                    putParcelable(COUNTRY_KEY,country)
                }
            }
        }
    }


}