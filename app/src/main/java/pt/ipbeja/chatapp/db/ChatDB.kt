package pt.ipbeja.chatapp.db

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec

@Database(entities = [Contact::class, Message::class], version = 2, exportSchema = false)
abstract class ChatDB : RoomDatabase(){

    abstract fun contactDao() : ContactDao
    abstract fun messageDao() : MessageDao


    companion object {
        @Volatile private var instance: ChatDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context.applicationContext).also { instance = it}}

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, ChatDB::class.java, "chat.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries() // for now :)
                .build()
    }



}