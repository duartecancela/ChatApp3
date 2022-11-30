package pt.ipbeja.chatapp.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


@Entity
data class Contact(
    val name: String,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)

@Entity
data class Message(
    val contactId: Long,
    val text: String,
    val direction: Direction = Direction.OUT,
    @PrimaryKey(autoGenerate = true) val id: Long = 0
) {
    enum class Direction(val id: Int) {
        IN(0), OUT(1)
    }
}

data class ContactWithMessages(
    @Embedded val contact: Contact,
    @Relation(parentColumn = "id", entityColumn = "contactId") val messages: List<Message>
)






