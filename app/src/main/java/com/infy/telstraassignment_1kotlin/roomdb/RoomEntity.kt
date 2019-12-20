package com.infy.telstraassignment_1kotlin.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

import java.io.Serializable

@Entity(tableName = "CanadaList")
class RoomEntity(
    @field:ColumnInfo(name = "Title")
    var title: String?, @field:ColumnInfo(name = "Description")
    var description: String?, @field:ColumnInfo(name = "Image Url")
    var imageUrl: String?
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}


