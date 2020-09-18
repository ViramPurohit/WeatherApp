package com.viram.weather.ui.city

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.viram.weather.R
import kotlinx.android.synthetic.main.add_city_fragment.*


class AddCityFragment : Fragment(), OnMapReadyCallback {

    companion object {
        fun newInstance() = AddCityFragment()
    }
    private var mapFragment: MapView? = null

    private lateinit var mMap: GoogleMap
    private lateinit var viewModel: AddCityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(this).get(AddCityViewModel::class.java)

        val root = inflater.inflate(R.layout.add_city_fragment, container, false)


        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapFragment = view.findViewById<View>(R.id.cityMap) as MapView
        mapFragment!!.onCreate(savedInstanceState)
        if(mapFragment != null){
            cityMap.getMapAsync(this)
        }

    }


    private fun setMapListener() {
        mMap.setOnMapClickListener(GoogleMap.OnMapClickListener {point->

            updateMaker(LatLng(point.latitude, point.longitude))
        })


        mMap.setOnMarkerDragListener(object : OnMarkerDragListener{
            override fun onMarkerDragStart(p0: Marker?) {
                TODO("Not yet implemented")
            }

            override fun onMarkerDrag(p0: Marker?) {
                TODO("Not yet implemented")
            }

            override fun onMarkerDragEnd(p0: Marker?) {
                p0?.position?.let { updateMaker(it) }
            }

        })
    }
    fun updateMaker(_newLatLong: LatLng) {
        mMap.clear()
        val marker = MarkerOptions().position(_newLatLong)
            .title("User City")
        mMap.addMarker(marker)
        mMap.animateCamera(CameraUpdateFactory.newLatLng(_newLatLong))
    }
    override fun onMapReady(gMap: GoogleMap) {
        mMap = gMap

        setMapListener()
    }

}