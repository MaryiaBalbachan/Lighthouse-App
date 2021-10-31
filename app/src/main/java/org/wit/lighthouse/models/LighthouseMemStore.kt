package org.wit.lighthouse.models

import timber.log.Timber.i

class LighthouseMemStore : LighthouseStore {

    val lighthouses = ArrayList<LighthouseModel>()

    override fun findAll(): List<LighthouseModel> {
        return lighthouses
    }

    override fun create(lighthouse: LighthouseModel) {
        lighthouses.add(lighthouse)
        logAll()
    }
    fun logAll() {
        lighthouses.forEach{ i("${it}") }
    }
}