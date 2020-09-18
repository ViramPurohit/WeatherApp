package com.viram.weather.ui.city

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.viram.weather.R
import com.viram.weather.di.Injectable
import com.viram.weather.ui.home.HomeViewModel
import javax.inject.Inject

class CityFragment : Fragment() , Injectable {

    private lateinit var mContext: Context

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    private lateinit var cityViewModel: CityViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        cityViewModel =
                ViewModelProvider(this,viewModelFactory).get(CityViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_city, container, false)

        val textView: TextView = root.findViewById(R.id.text_dashboard)
        cityViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}