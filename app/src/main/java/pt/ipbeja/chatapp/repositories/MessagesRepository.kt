package pt.ipbeja.chatapp.repositories

import android.app.Application
import pt.ipbeja.chatapp.db.ChatDB
import pt.ipbeja.chatapp.db.Message

class MessagesRepository(app: Application) {

    private val messagesDao = ChatDB(app).messageDao()
    private val messageCache = mutableListOf<Message>()

    fun getMessages() : List<Message> {
        if(messageCache.isEmpty()) {
            messageCache.addAll(messagesDao.getAll())
        }

        return messageCache;
    }

    fun addMessage(message: Message) {
        val id = messagesDao.add(message)
        val copyWithId = message.copy(id = id)
        messageCache.add(copyWithId)
    }
}