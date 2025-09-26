package com.square.repos.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.square.repos.data.local.database.dao.RepoDao
import com.square.repos.data.local.database.entity.RepoEntity

@Database(
    entities = [RepoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class RepoDatabase : RoomDatabase() {
    abstract fun repoDao(): RepoDao

    companion object {
        @Volatile
        private var Instance: RepoDatabase? = null

        fun getInstance(context: Context): RepoDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    RepoDatabase::class.java,
                    "repo_database"
                ).build().also { Instance = it }
            }
        }
    }
}