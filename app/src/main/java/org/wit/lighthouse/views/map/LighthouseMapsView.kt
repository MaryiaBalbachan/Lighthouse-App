package org.wit.lighthouse.views.map

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.wit.lighthouse.R
import org.wit.lighthouse.databinding.ActivityLighthouseMapsBinding
import org.wit.lighthouse.databinding.ContentLighthouseMapsBinding
import org.wit.lighthouse.main.MainApp
import org.wit.lighthouse.models.LighthouseModel

class LighthouseMapsView : AppCompatActivity(), GoogleMap.OnMarkerClickListener {


    private lateinit var binding: ActivityLighthouseMapsBinding
    private lateinit var contentBinding: ContentLighthouseMapsBinding
    //lateinit var map: GoogleMap
    lateinit var app: MainApp
    lateinit var presenter: LighthouseMapsPresenter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as MainApp

        binding = ActivityLighthouseMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        presenter = LighthouseMapsPresenter(this)
        contentBinding = ContentLighthouseMapsBinding.bind(binding.root)

        contentBinding.mapView.onCreate(savedInstanceState)
        contentBinding.mapView.getMapAsync {
            GlobalScope.launch(Dispatchers.Main) {
                presenter.doPopulateMap(it)
            }
        }
    }
    fun showLighthouse(lighthouse: LighthouseModel) {
        contentBinding.currentTitle.text = lighthouse.title
        contentBinding.currentDescription.text = lighthouse.description
        Picasso.get()
            .load(lighthouse.image)
            .into(contentBinding.imageView2)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        GlobalScope.launch(Dispatchers.Main) {
            presenter.doMarkerSelected(marker)
        }

        return true
    }

    /*override fun onMarkerClick(marker: Marker): Boolean {
        val tag = marker.tag as Long
        val lighthouse = app.lighthouses.findById(tag)
        currentTitle.text = lighthouse!!.title
        currentDescription.text = lighthouse!!.description
        imageView.setImageBitmap(readImageFromPath(this@lighthouseMapsActivity, lighthouse.image))
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