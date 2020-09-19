package com.viram.weather.ui.home

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.viram.weather.MainActivity
import com.viram.weather.R
import com.viram.weather.Util
import com.viram.weather.api.ApiStage
import com.viram.weather.di.Injectable
import com.viram.weather.ui.city.AddCityFragment
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject


class HomeFragment : Fragment() , Injectable {

    private var mLocation: Location? = null

    private val PERMISSION_REQUEST_CODE: Int = 101

    private lateinit var mContext: Context

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        /*
        * Calling setRetainInstance(true) inside Fragment protect from destroy and recreate and retain the current instance of the fragment when the activity is recreated.
        * */
        retainInstance = true
        return root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addRecyclerView()
        /*
        * Check location Permission
        * */
        checkLocationPermission()

        fab_add_city.setOnClickListener {
            (activity as MainActivity).loadFragment(AddCityFragment.newInstance(mLocation))
        }
    }

    fun addRecyclerView(){
        recyclerView_userCity.apply {

            layoutManager = LinearLayoutManager(mContext)

        }
    }
    fun checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(
                mContext,
                ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                mContext,
                ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            requestPermissions(
                arrayOf(
                    ACCESS_FINE_LOCATION,
                    ACCESS_COARSE_LOCATION
                ),
                PERMISSION_REQUEST_CODE
            )
        }else{
            getUserLocation()
        }
    }

    fun getUserLocation(){
        fusedLocationClient = activity?.let { LocationServices.getFusedLocationProviderClient(it) }!!

        if (ActivityCompat.checkSelfPermission(
                mContext,
                ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                mContext,
                ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                // Got last known location. In some rare situations this can be null.
                mLocation = location
                Log.e("location latitude", location?.latitude.toString())
                Log.e("location longitude", location?.longitude.toString())
                getWeatherFromServer(location?.latitude, location?.longitude)
            }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        getUserLocation()
                    } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        // Should we show an explanation?
                        if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                            Util.showDialogMessage(mContext,
                                "You need to allow location permission for current weather details"
                            ) { dialog, which ->
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    requestPermissions(
                                        arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION),
                                        PERMISSION_REQUEST_CODE
                                    )
                                }
                            }
                            return
                        }else{
                            activity?.let { openAppSetting(it) }
                        }
                    }
                }
            }

        }
    }

    private fun getWeatherFromServer(latitude: Double?, longitude: Double?) {
        if (Util.isNetworkAvailable(activity)) {

            fragment_home_pgBar.visibility = View.VISIBLE
            homeViewModel.getTodayWeather(latitude,longitude).observe(viewLifecycleOwner, { response ->
                when (response) {
                    is ApiStage.Loading -> fragment_home_pgBar.visibility = View.VISIBLE
                    is ApiStage.Success -> {
                        try {
                            if (response.data?.weather?.isNotEmpty()!!) {

                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        fragment_home_pgBar.visibility = View.GONE
                    }
                    is ApiStage.Failure -> {
                        fragment_home_pgBar.visibility = View.GONE
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
    fun openAppSetting(activity: Activity){
        Intent(ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:${activity.packageName}")).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(this)
        }
    }
}