package org.wit.lighthouse.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import androidx.navigation.ui.AppBarConfiguration
import org.wit.lighthouse.R
import org.wit.lighthouse.databinding.ActivityLighthouseMapsBinding
import org.wit.lighthouse.databinding.ContentLighthouseMapsBinding
import org.wit.lighthouse.main.MainApp

class LighthouseMapsActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener {


    private lateinit var binding: ActivityLighthouseMapsBinding
    private lateinit var contentBinding: ContentLighthouseMapsBinding
    lateinit var map: GoogleMap
    lateinit var app: MainApp



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as MainApp

        binding = ActivityLighthouseMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        contentBinding = ContentLighthouseMapsBinding.bind(binding.root)
        contentBinding.mapView.onCreate(savedInstanceState)
        contentBinding.mapView.getMapAsync {
            map = it
            configureMap()
        }

    }

    fun configureMap(){
        map.setOnMarkerClickListener(this)
        map.uiSettings.isZoomControlsEnabled = true
        app.lighthouses.findAll().forEach {
            val loc = LatLng(it.lat, it.lng)
            val options = MarkerOptions().title(it.title).position(loc)
            map.addMarker(options).tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val currentTitle: TextView = findViewById(R.id.currentTitle)
        currentTitle.text = marker.title

        return false
    }

    /*override fun onMarkerClick(marker: Marker): Boolean {
        val tag = marker.tag as Long
        val lighthouse = app.lighthouses.findById(tag)
        currentTitle.text = lighthouse!!.title
        currentDescription.text = lighthouse!!.description
        imageView.setImageBitmap(readImageFromPath(this@PlacemarkMapsActivity, placemark.image))
        return true
    }*/

    override fun onDestroy() {
        super.onDestroy()
        contentBinding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        contentBinding.mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        contentBinding.mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        contentBinding.mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        contentBinding.mapView.onSaveInstanceState(outState)
    }


}