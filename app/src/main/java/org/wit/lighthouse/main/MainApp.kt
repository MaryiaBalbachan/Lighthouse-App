package org.wit.lighthouse.main

import android.app.Application
import org.wit.lighthouse.models.LighthouseJSONStore
import org.wit.lighthouse.models.LighthouseMemStore
import org.wit.lighthouse.models.LighthouseStore
import org.wit.lighthouse.room.LighthouseStoreRoom
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var lighthouses: LighthouseStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        lighthouses = LighthouseStoreRoom(applicationContext)
        // lighthouses = LighthouseJSONStore(applicationContext)
        i("lighthouse started")
    }
}