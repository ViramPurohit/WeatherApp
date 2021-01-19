package com.viram.weather.ui.city

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.viram.weather.R
import com.viram.weather.Util
import com.viram.weather.di.Injectable
import com.viram.weather.viewmodel.OnFragmentInteractionListener
import com.viram.weather.vo.UserCity
import kotlinx.android.synthetic.main.add_city_fragment.*
import java.io.IOException
import java.util.*
import javax.inject.Inject


class AddCityFragment : Fragment(), Injectable,OnMapReadyCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mContext: Context
    private var mListener: OnFragmentInteractionListener? = null

    var user : UserCity? = null

    companion object {
        var mLocation: Location? = null
        fun newInstance(_mLocation: Location?): AddCityFragment {
            val fragment = AddCityFragment()
            mLocation = _mLocation
            return fragment
        }
    }


    private var mMapFragment: SupportMapFragment? = null

    private lateinit var mMap: GoogleMap

    private lateinit var addCityViewModel: AddCityViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        mListener = if (context is OnFragmentInteractionListener) {
            context as OnFragmentInteractionListener
        } else {
            throw RuntimeException(
                context.toString()
                        + " must implement OnFragmentInteractionListener"
            )
        }
    }
    override fun onDetach() {
        super.onDetach()
        mListener = null
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addCityViewModel =
            ViewModelProvider(this, viewModelFactory).get(AddCityViewModel::class.java)

        val root = inflater.inflate(R.layout.add_city_fragment, container, false)
        setHasOptionsMenu(true)
        /*
            * Calling setRetainInstance(true) inside Fragment protect from destroy and recreate and retain the current instance of the fragment when the activity is recreated.
            * */
        retainInstance = true
        return root

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_bookmarked -> {
                user?.let { addCityViewModel.addUserCity(it) }

                parentFragmentManager.popBackStack()

            }
        }
        return true
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mMapFragment = childFragmentManager.findFragmentById(R.id.cityMap) as SupportMapFragment?
        mMapFragment?.getMapAsync(this)

        mListener?.onSetTitle(mContext.getString(R.string.add_city))
        mListener?.onSetBookMarkVisibilityButton(true)
    }



    override fun onMapReady(gMap: GoogleMap) {
        mMap = gMap

        updateMaker(LatLng(mLocation!!.latitude, mLocation!!.longitude))

        setMapListener()



    }

    private fun setMapListener() {
        mMap.setOnMapClickListener { point ->

            updateMaker(LatLng(point.latitude, point.longitude))
        }


        mMap.setOnMarkerDragListener(object : OnMarkerDragListener {
            override fun onMarkerDragStart(p0: Marker?) {
            }

            override fun onMarkerDrag(p0: Marker?) {
            }

            override fun onMarkerDragEnd(p0: Marker?) {
                p0?.position?.let { updateMaker(it) }
            }

        })
    }
    fun updateMaker(_newLatLong: LatLng) {
        getAddressFromLocation(_newLatLong.latitude, _newLatLong.longitude)
        mMap.clear()
        val marker = MarkerOptions().position(_newLatLong)
            .title("User City")
            .draggable(true)
        mMap.addMarker(marker)
        val cameraPosition = CameraPosition.Builder()
            .target(_newLatLong)
            .zoom(16f) // Sets the zoom
            .build()
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
//            mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f))
    }


    private fun getAddressFromLocation(latitude: Double, longitude: Double) {
        val geocoder = Geocoder(mContext, Locale.ENGLISH)
        try {
            val addresses: List<Address> = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses.isNotEmpty()) {

                val fetchedAddress: Address = addresses[0]
                Log.d("fetchedAddress", fetchedAddress.getAddressLine(0))
                user_location.setText(fetchedAddress.getAddressLine(0))
                user = if(fetchedAddress.locality != null){
                    UserCity(
                        fetchedAddress.locality ,
                        fetchedAddress.getAddressLine(0),
                        latitude,
                        longitude
                    )
                }else if(fetchedAddress.subAdminArea != null){
                    UserCity(
                        fetchedAddress.subAdminArea ,
                        fetchedAddress.getAddressLine(0),
                        latitude,
                        longitude
                    )
                }else {
                    UserCity(
                        "No City" ,
                        fetchedAddress.getAddressLine(0),
                        latitude,
                        longitude
                    )
                }



            } else {
                user_location.setText(R.string.searching_location)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Util.showToastLong(mContext, getString(R.string.not_getting_address))
        }
    }


}