package org.wit.lighthouse.room

import android.content.Context
import androidx.room.Room
import org.wit.lighthouse.models.LighthouseModel
import org.wit.lighthouse.models.LighthouseStore

class LighthouseStoreRoom(val context: Context) : LighthouseStore {

    var dao: LighthouseDao

    init {
        val database = Room.databaseBuilder(context, Database::class.java, "room_sample.db")
            .fallbackToDestructiveMigration()
            .build()
        dao = database.lighthouseDao()
    }

    override suspend fun findAll(): List<LighthouseModel> {
        return dao.findAll()
    }

    override suspend fun findById(id: Long): LighthouseModel? {
        return dao.findById(id)
    }

    override suspend fun create(lighthouse: LighthouseModel) {
        dao.create(lighthouse)
    }

    override suspend fun update(lighthouse: LighthouseModel) {
        dao.update(lighthouse)
    }

    override suspend fun delete(lighthouse: LighthouseModel) {
        dao.deleteLighthouse(lighthouse)
    }

    override suspend fun clear() {
    }
}
