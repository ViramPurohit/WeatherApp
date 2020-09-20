package com.viram.weather.ui.city

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.viram.weather.R
import com.viram.weather.Util
import com.viram.weather.api.ApiStage
import com.viram.weather.di.Injectable
import com.viram.weather.model.WeatherResult
import com.viram.weather.vo.UserCity
import kotlinx.android.synthetic.main.forecast_layout.*
import kotlinx.android.synthetic.main.fragment_city.*
import javax.inject.Inject

class CityFragment : Fragment() , Injectable {

    private lateinit var mContext: Context

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    private lateinit var cityViewModel: CityViewModel


    companion object {
        var mLocation: UserCity? = null
        fun newInstance(_mLocation: UserCity): CityFragment {
            val fragment = CityFragment()
            mLocation = _mLocation
            return fragment
        }
    }

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


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getWeatherFromServer(mLocation?.latitude, mLocation?.longitude)
    }

    fun setValue(data: WeatherResult) {
        txt_temperature_value.text = data.main.temp.toString().plus(" C")
        txt_humidity_value.text = data.main.humidity.toString()
        txt_rain_value.text = data.weather[0].main
        txt_wind_value.text = data.wind.speed.toString().plus("KMH")
    }

    private fun getWeatherFromServer(latitude: Double?, longitude: Double?) {
        if (Util.isNetworkAvailable(activity)) {

            fragment_city_pgBar.visibility = View.VISIBLE
            cityViewModel.getTodayWeather(latitude,longitude).observe(viewLifecycleOwner, { response ->
                when (response) {
                    is ApiStage.Loading -> fragment_city_pgBar.visibility = View.VISIBLE
                    is ApiStage.Success -> {
                        try {
                            if (response.data?.weather?.isNotEmpty()!!) {
                                setValue(response.data)
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        fragment_city_pgBar.visibility = View.GONE
                    }
                    is ApiStage.Failure -> {
                        fragment_city_pgBar.visibility = View.GONE
                    }

                }
            })


        }else{
            Util.showToastLong(
                mContext,
                getString(R.string.internet_not_connected)
            )
        }
    }

}