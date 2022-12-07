package pt.ipbeja.chatapp.repositories

import android.app.Application
import pt.ipbeja.chatapp.db.ChatDB
import pt.ipbeja.chatapp.db.Contact

class ContactsRepository(app: Application) {

    private val contactsDao = ChatDB(app).contactDao()
    private val contactCache = mutableListOf<Contact>()

    fun getContacts() : List<Contact> {
        if(contactCache.isEmpty()) {
            contactCache.addAll(contactsDao.getAll())
        }

        return contactCache;
    }

    fun addContact(contact: Contact) {
        val id = contactsDao.add(contact)
        val copyWithId = contact.copy(id = id)
        contactCache.add(copyWithId)
    }

    fun deleteContacts(){
        contactsDao.deleteAll()
    }

}