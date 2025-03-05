package com.bridge.androidtechnicaltest.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PupilDao {
    /**
     * Insert multiple Pupils at once. On conflict by primary key,
     * we replace the existing row.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<Pupil>)

    /**
     * Insert a single Pupil.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Pupil)

    /**
     * Return the single pupil, or null if not found
     */
    @Query("SELECT * FROM Pupils WHERE pupil_id=:id")
    suspend fun getById(id: Int): Pupil?

    /**
     * Retrieve all Pupils, sorted by name ascending, as a Flow.
     * This allows real-time observation of the Pupils table.
     */
    @Query("SELECT * FROM Pupils ORDER BY name ASC")
    fun pagingSource(): PagingSource<Int, Pupil>

    /**
     * Delete a pupil by its ID directly.
     */
    @Query("DELETE FROM Pupils WHERE pupil_id = :pupilId")
    suspend fun deletePupilById(pupilId: Int)

    @Query("DELETE FROM Pupils")
    suspend fun clearAll()
}

