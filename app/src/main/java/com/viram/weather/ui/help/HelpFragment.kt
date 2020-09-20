package com.viram.weather.ui.help

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.viram.weather.R
import com.viram.weather.di.Injectable
import com.viram.weather.ui.home.HomeViewModel
import kotlinx.android.synthetic.main.fragment_help.*
import javax.inject.Inject

class HelpFragment : Fragment() , Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mContext: Context

    private lateinit var helpViewModel: HelpViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        helpViewModel =
                ViewModelProvider(this,viewModelFactory).get(HelpViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_help, container, false)



        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView.webViewClient = WebViewClient()
        webView.loadUrl("https://www.google.com")
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
    }
}