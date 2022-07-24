package droid.maxaria.maxander.newsapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import droid.maxaria.maxander.newsapp.R
import droid.maxaria.maxander.newsapp.databinding.FragmentNewsBinding
import droid.maxaria.maxander.newsapp.databinding.FragmentWebViewBinding
import java.lang.RuntimeException


class WebViewFragment : Fragment() {
    private var _binding: FragmentWebViewBinding? = null
    private val binding: FragmentWebViewBinding
        get() = _binding!!
    private lateinit var url:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentWebViewBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            webView.webViewClient = WebViewClient()
            webView.loadUrl(url)
            webView.settings.javaScriptEnabled = true
            webView.settings.setSupportZoom(true)
        }
    }

    private fun parseArgs(){
        arguments?.getString(URL_KEY).let {
            if (it != null) {
                url = it
            } else throw RuntimeException("Url can't be null")
        }
    }
    companion object {
        private const val URL_KEY = "url key"
        fun getInstance(url:String):WebViewFragment{
            return WebViewFragment().apply {
                arguments = Bundle().apply {
                    putString(URL_KEY,url)
                }
            }
        }
    }
}