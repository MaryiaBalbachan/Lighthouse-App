package org.wit.lighthouse.views.lighthouse

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.maps.GoogleMap
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import org.wit.lighthouse.R
import org.wit.lighthouse.databinding.ActivityLighthouseBinding
import org.wit.lighthouse.models.LighthouseModel
import timber.log.Timber.i

class LighthouseView : AppCompatActivity() {
    private lateinit var binding: ActivityLighthouseBinding
    private lateinit var presenter: LighthousePresenter
    lateinit var map: GoogleMap
    var lighthouse = LighthouseModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityLighthouseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        presenter = LighthousePresenter(this)

        binding.mapView.onCreate(savedInstanceState);
        binding.mapView.getMapAsync {
            map = it
            presenter.doConfigureMap(map)
        }

        binding.chooseImage.setOnClickListener {
            presenter.cacheLighthouse(binding.lighthouseTitle.text.toString(), binding.description.text.toString())
            presenter.doSelectImage()
        }

        /*binding.lighthouseLocation.setOnClickListener {
            presenter.cacheLighthouse(binding.lighthouseTitle.text.toString(), binding.description.text.toString())
            presenter.doSetLocation()
        }*/
        binding.mapView.getMapAsync {
            map = it
            presenter.doConfigureMap(map)
            it.setOnMapClickListener { presenter.doSetLocation() }
        }

    }
    fun showLighthouse(lighthouse: LighthouseModel) {
        binding.lighthouseTitle.setText(lighthouse.title)
        binding.description.setText(lighthouse.description)

        Picasso.get()
            .load(lighthouse.image)
            .into(binding.lighthouseImage)

        if (lighthouse.image != Uri.EMPTY) {
            binding.chooseImage.setText(R.string.change_lighthouse_image)
        }
        binding.lat.setText("%.6f".format(lighthouse.lat))
        binding.lng.setText("%.6f".format(lighthouse.lng))

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_lighthouse, menu)
        val deleteMenu: MenuItem = menu.findItem(R.id.item_delete)
        if (presenter.edit){
            deleteMenu.setVisible(true)
        }
        else{
            deleteMenu.setVisible(false)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_save -> {
                if (binding.lighthouseTitle.text.toString().isEmpty()) {
                    Snackbar.make(binding.root, R.string.enter_lighthouse_title, Snackbar.LENGTH_LONG)
                        .show()
                } else {
                    presenter.doAddOrSave(binding.lighthouseTitle.text.toString(), binding.description.text.toString())
                }
            }
            R.id.item_delete -> {
                presenter.doDelete()
            }
            R.id.item_cancel -> {
                presenter.doCancel()
            }

        }
        return super.onOptionsItemSelected(item)
    }

    fun updateImage(image: Uri){
        i("Image updated")
        Picasso.get()
            .load(image)
            .into(binding.lighthouseImage)
        binding.chooseImage.setText(R.string.change_lighthouse_image)
    }
    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }
}

