package pt.ipbeja.chatapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import pt.ipbeja.chatapp.db.Message
import pt.ipbeja.chatapp.repositories.MessagesRepository

class ChatViewModel(app: Application)
    : AndroidViewModel(app) {

//    // TODO Add MessageRepository
//    private val messagesRepo = MessagesRepository(app)
//
//    private val args = ChatFragmentArgs.fromSavedStateHandle(stateHandle)
//    private val contactId = args.contactId
//
//
//    fun getMessages() : List<Message> {
//        TODO("obter as mensagens do repo")
//        return messagesRepo.getMessages()
//    }
//
//    // TODO adicionar o método para
//    //  obter ContactWithMessages
//
//    // TODO adicionar o método para
//    //  adicionar mensagem

    private val messagesRepo = MessagesRepository(app)

    fun getMessages(id: Long) = messagesRepo.getMessages(id)

    fun addMessage(message: Message) = messagesRepo.addMessage(message)

}