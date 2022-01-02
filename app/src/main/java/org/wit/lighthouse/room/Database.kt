package org.wit.lighthouse.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.wit.lighthouse.helpers.Converters
import org.wit.lighthouse.models.LighthouseModel

@Database(entities = arrayOf(LighthouseModel::class), version = 2,  exportSchema = false)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {

    abstract fun lighthouseDao(): LighthouseDao
}