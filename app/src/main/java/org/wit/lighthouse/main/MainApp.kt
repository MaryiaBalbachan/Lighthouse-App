package org.wit.lighthouse.main

import android.app.Application
import org.wit.lighthouse.models.LighthouseMemStore
import org.wit.lighthouse.models.LighthouseModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val lighthouses = LighthouseMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("lighthouse started")
//        lighthouses.add(lighthouseModel("One", "About one..."))
//        lighthouses.add(lighthouseModel("Two", "About two..."))
//        lighthouses.add(lighthouseModel("Three", "About three..."))
    }
}