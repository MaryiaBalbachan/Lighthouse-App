package org.wit.lighthouse.models

interface LighthouseStore {
    fun findAll(): List<LighthouseModel>
    fun create(lighthouse: LighthouseModel)
    fun update(lighthouse: LighthouseModel)
    fun delete(lighthouse: LighthouseModel)
    fun findById(id:Long) : LighthouseModel?
}