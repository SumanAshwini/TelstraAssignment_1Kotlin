package com.infy.telstraassignment_1kotlin.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CanadaDao {

    @get:Query("Select * from Canadalist")
    val canadasList: List<RoomEntity>

    @Query("Delete from CanadaList")
    fun deleteTitle()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(roomEntity: RoomEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListOfUsers(titlesArrayList: List<RoomEntity>)
}