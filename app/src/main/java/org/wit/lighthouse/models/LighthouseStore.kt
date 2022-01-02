package org.wit.lighthouse.models

interface LighthouseStore {
    suspend fun findAll(): List<LighthouseModel>
    suspend fun create(lighthouse: LighthouseModel)
    suspend fun update(lighthouse: LighthouseModel)
    suspend fun delete(lighthouse: LighthouseModel)
    suspend fun findById(id:Long) : LighthouseModel?
    suspend fun clear()
}