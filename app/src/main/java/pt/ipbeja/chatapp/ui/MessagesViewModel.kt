package pt.ipbeja.chatapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import pt.ipbeja.chatapp.repositories.MessagesRepository

class MessagesViewModel(app: Application) : AndroidViewModel(app) {
    private val messagesRepo = MessagesRepository(app)

    fun getMessages() = messagesRepo.getMessages()
}