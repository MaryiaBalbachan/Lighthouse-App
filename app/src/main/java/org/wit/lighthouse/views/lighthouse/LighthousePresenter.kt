package org.wit.lighthouse.views.lighthouse

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log.i
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.lighthouse.databinding.ActivityLighthouseBinding
import org.wit.lighthouse.helpers.checkLocationPermissions
import org.wit.lighthouse.helpers.createDefaultLocationRequest
import org.wit.lighthouse.main.MainApp
import org.wit.lighthouse.models.Location
import org.wit.lighthouse.models.LighthouseModel
import org.wit.lighthouse.helpers.showImagePicker
import org.wit.lighthouse.views.location.EditLocationView
import timber.log.Timber
import timber.log.Timber.i

class LighthousePresenter(private val view: LighthouseView) {

    var map: GoogleMap? = null
    var lighthouse = LighthouseModel()
    var app: MainApp = view.application as MainApp
    var binding: ActivityLighthouseBinding = ActivityLighthouseBinding.inflate(view.layoutInflater)
    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher: ActivityResultLauncher<Intent>
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    var edit = false;
    private val location = Location(52.1237, -6.9294, 15f)
    var locationService: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(view)
    val locationRequest = createDefaultLocationRequest()


    init {
        doPermissionLauncher()
        registerImagePickerCallback()
        registerMapCallback()


        if (view.intent.hasExtra("lighthouse_edit")) {
            edit = true
            lighthouse = view.intent.extras?.getParcelable("lighthouse_edit")!!
            view.showLighthouse(lighthouse)
        } else {
            if (checkLocationPermissions(view)) {
                doSetCurrentLocation()
            }

            lighthouse.location.lat = location.lat
            lighthouse.location.lng = location.lng
        }
    }

    @SuppressLint("MissingPermission")
    fun doRestartLocationUpdates() {
        var locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult != null && locationResult.locations != null) {
                    val l = locationResult.locations.last()
                    locationUpdate(l.latitude, l.longitude)
                }
            }
        }
        if (!edit) {
            locationService.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }

    @SuppressLint("MissingPermission")
    fun doSetCurrentLocation() {
        i("setting location from doSetLocation")
        locationService.lastLocation.addOnSuccessListener {
            locationUpdate(it.latitude, it.longitude)
        }
    }


    fun doConfigureMap(m: GoogleMap) {
        map = m
        locationUpdate(lighthouse.location.lat, lighthouse.location.lng)
    }

    fun locationUpdate(lat: Double, lng: Double) {
        lighthouse.location = location
        map?.clear()
        map?.uiSettings?.setZoomControlsEnabled(true)
        val options = MarkerOptions().title(lighthouse.title).position(LatLng(lighthouse.location.lat, lighthouse.location.lng))
        map?.addMarker(options)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lighthouse.location.lat, lighthouse.location.lng), lighthouse.location.zoom))
        view.showLighthouse(lighthouse)
    }
    

    suspend fun doAddOrSave(title: String, description: String) {
        lighthouse.title = title
        lighthouse.description = description
        if (edit) {
            app.lighthouses.update(lighthouse)
        } else {
            app.lighthouses.create(lighthouse)
        }

        view.finish()

    }

    fun doCancel() {
        view.finish()

    }

    suspend fun doDelete() {
        app.lighthouses.delete(lighthouse)
        view.finish()

    }

    fun doSelectImage() {
        showImagePicker(imageIntentLauncher)
    }

    fun doSetLocation() {
        if (lighthouse.location.zoom != 0f) {

            location.lat =  lighthouse.location.lat
            location.lng = lighthouse.location.lng
            location.zoom = lighthouse.location.zoom
            locationUpdate(lighthouse.location.lat, lighthouse.location.lng)
        }

        val launcherIntent = Intent(view, EditLocationView::class.java)
            .putExtra("location", location)
        mapIntentLauncher.launch(launcherIntent)
    }

    fun cacheLighthouse (title: String, description: String) {
        lighthouse.title = title;
        lighthouse.description = description
    }

    private fun registerImagePickerCallback() {

        imageIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")
                            lighthouse.image = result.data!!.data!!
                            view.updateImage(lighthouse.image)
                        }
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }

            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            view.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Location ${result.data.toString()}")
                            val location =
                                result.data!!.extras?.getParcelable<Location>("location")!!
                            Timber.i("Location == $location")
                            lighthouse.location = location

                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> {
                    }
                    else -> {
                    }
                }

            }
    }
        private fun doPermissionLauncher() {
            i("permission check called")
            requestPermissionLauncher =
                view.registerForActivityResult(ActivityResultContracts.RequestPermission())
                { isGranted: Boolean ->
                    if (isGranted) {
                        doSetCurrentLocation()
                    } else {
                        locationUpdate(location.lat, location.lng)
                    }
                }
        }
    }


