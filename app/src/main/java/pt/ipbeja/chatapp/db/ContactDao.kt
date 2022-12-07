package pt.ipbeja.chatapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface ContactDao : BaseDao<Contact> {

    @Query("select * from contact")
    fun getAll(): List<Contact>


    @Transaction
    @Query("select * from contact where id = :contactId")
    fun getContactWithMessages(contactId: Long): ContactWithMessages


    @Query("delete from contact")
    fun deleteAll():Int

}