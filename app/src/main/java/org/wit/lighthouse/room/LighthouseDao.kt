package org.wit.lighthouse.room

import androidx.room.*
import org.wit.lighthouse.models.LighthouseModel

@Dao
interface LighthouseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun create(lighthouse: LighthouseModel)

    @Query("SELECT * FROM LighthouseModel")
    suspend fun findAll(): List<LighthouseModel>

    @Query("select * from LighthouseModel where id = :id")
    suspend fun findById(id: Long): LighthouseModel

    @Update
    suspend fun update(lighthouse: LighthouseModel)

    @Delete
    suspend fun deleteLighthouse(lighthouse: LighthouseModel)
}