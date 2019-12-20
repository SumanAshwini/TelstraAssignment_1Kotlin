package com.infy.telstraassignment_1kotlin.roomdb

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Database(entities = [RoomEntity::class], exportSchema = false, version = 2)
abstract class CanadaRoomDb : RoomDatabase() {

    abstract fun canadaDao(): CanadaDao

    companion object {

        private val DB_NAME = "Canada.db"
        private var instance: CanadaRoomDb? = null


        fun getInstance(context: Context): CanadaRoomDb {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext, CanadaRoomDb::class.java, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance as CanadaRoomDb
        }
    }

}

