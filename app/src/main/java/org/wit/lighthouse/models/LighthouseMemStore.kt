package org.wit.lighthouse.models

import timber.log.Timber.i
var lastId = 0L
internal fun getId(): Long {
    return lastId++
}

class LighthouseMemStore : LighthouseStore {

    val lighthouses = ArrayList<LighthouseModel>()

    override fun findAll(): List<LighthouseModel> {
        return lighthouses
    }

    override fun create(lighthouse: LighthouseModel) {
        lighthouse.id = getId()
        lighthouses.add(lighthouse)
        logAll()
    }
    override fun update(lighthouse: LighthouseModel) {
        var foundlighthouse: LighthouseModel? = lighthouses.find { p -> p.id == lighthouse.id }
        if (foundlighthouse != null) {
            foundlighthouse.title = lighthouse.title
            foundlighthouse.description = lighthouse.description
            logAll()
        }
    }

    fun logAll() {
        lighthouses.forEach{ i("${it}") }
    }
}