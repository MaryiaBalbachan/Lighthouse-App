package org.wit.lighthouse.models

interface LighthouseStore {
    fun findAll(): List<LighthouseModel>
    fun create(lighthouse: LighthouseModel)
    fun update(placemark: LighthouseModel)
}