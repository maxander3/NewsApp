package droid.maxaria.maxander.newsapp.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import droid.maxaria.maxander.newsapp.R
import droid.maxaria.maxander.newsapp.databinding.FragmentNewsBinding
import droid.maxaria.maxander.newsapp.databinding.FragmentParentFragmentBinding
import droid.maxaria.maxander.newsapp.domain.country_model.CountryModel
import droid.maxaria.maxander.newsapp.domain.models.news_model_in_list.NewsModel
import java.lang.RuntimeException


class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding: FragmentNewsBinding
        get() = _binding!!
    private lateinit var args:NewsModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentNewsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews(){
        binding.titleTv.text = args.title
        binding.descriptionTv.text =args.description
        binding.dateTv.text = args.publishedAt.substring(0,10)
        if(args.urlToImage!="") {

            Picasso.get().load(args.urlToImage)
                .error(R.mipmap.error_img_ic)
                .into(binding.newsImg)

        }else{
            binding.newsImg.setImageResource(R.mipmap.error_img_ic)
        }
    }

    private fun parseArgs() {
        arguments?.getParcelable<NewsModel>(NEWS_MODEL)?.let {
            args = it
        } ?:throw RuntimeException("Fragment need arguments to init")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    companion object {
        private const val NEWS_MODEL = "news model"
        fun getInstance(data: NewsModel): NewsFragment {
            return NewsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(NEWS_MODEL, data)
                }
            }
        }
    }
}