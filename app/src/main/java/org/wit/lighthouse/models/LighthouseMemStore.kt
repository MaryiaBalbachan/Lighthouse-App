package org.wit.lighthouse.models

import timber.log.Timber.i
var lastId = 0L
internal fun getId(): Long {
    return lastId++
}

class LighthouseMemStore : LighthouseStore {

    val lighthouses = ArrayList<LighthouseModel>()

    override suspend fun findAll(): List<LighthouseModel> {
        return lighthouses
    }

    override suspend fun findById(id:Long) : LighthouseModel? {
        val foundLighthouse: LighthouseModel? = lighthouses.find { it.id == id }
        return foundLighthouse
    }

    override suspend fun create(lighthouse: LighthouseModel) {
        lighthouse.id = getId()
        lighthouses.add(lighthouse)
        logAll()
    }
    override suspend fun update(lighthouse: LighthouseModel) {
        var foundlighthouse: LighthouseModel? = lighthouses.find { p -> p.id == lighthouse.id }
        if (foundlighthouse != null) {
            foundlighthouse.title = lighthouse.title
            foundlighthouse.description = lighthouse.description
            foundlighthouse.image = lighthouse.image
            foundlighthouse.location = lighthouse.location
            logAll()
        }
    }

    override suspend fun delete(lighthouse: LighthouseModel){
        lighthouses.remove(lighthouse)
    }

    fun logAll() {
        lighthouses.forEach{ i("${it}") }
    }
    override suspend fun clear(){
        lighthouses.clear()
    }
}