package pt.ipbeja.chatapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import pt.ipbeja.chatapp.db.ChatDB
import pt.ipbeja.chatapp.db.Contact
import pt.ipbeja.chatapp.repositories.ContactsRepository

class ContactsViewModel(app: Application) : AndroidViewModel(app) {

    private val contactsRepo = ContactsRepository(app)

    fun getContacts() = contactsRepo.getContacts()
    fun addContact(contact: Contact) = contactsRepo.addContact(contact)

}