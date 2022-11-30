package pt.ipbeja.chatapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import pt.ipbeja.chatapp.R
import pt.ipbeja.chatapp.databinding.FragmentChatBinding
import pt.ipbeja.chatapp.databinding.SentMessageItemBinding
import pt.ipbeja.chatapp.db.ChatDB
import pt.ipbeja.chatapp.db.Message

class ChatFragment : Fragment() {

    private val args: ChatFragmentArgs by navArgs()

    private lateinit var binding: FragmentChatBinding

    private val viewModel by viewModels<MessagesViewModel>()

    private val adapter: ChatAdapter = ChatAdapter()

    // TODO pedir o ChatViewModel e
    //  substituir as chamadas diretas à BD

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentChatBinding.inflate(inflater)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val contactId = args.contactId

        val contactWithMessages = ChatDB(requireContext())
            .contactDao()
            .getContactWithMessages(contactId)

        adapter.setData(contactWithMessages.messages)

        binding.send.setOnClickListener {
            // TODO criar a mensagem na bd e adapter

            val messageText = binding.input.text.toString()
            val message = Message(contactId, messageText, Message.Direction.OUT)
            viewModel.addMessage(message)

        }


    }



    class MessageViewHolder(view: View) : ViewHolder(view) {

        private val binding = SentMessageItemBinding.bind(view)

        fun bind(message: Message) {
            // todo colocar o texto da Message na TextView
        }

    }


    class ChatAdapter : RecyclerView.Adapter<MessageViewHolder>() {

        private val messages: MutableList<Message> = mutableListOf()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {

            val view = LayoutInflater.from(parent.context).inflate(R.layout.sent_message_item, parent, false)
            return MessageViewHolder(view)
        }

        override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
            holder.bind(messages[position])
        }

        override fun getItemCount() = messages.size

        override fun getItemViewType(position: Int): Int {
            val direction = messages[position].direction
            return direction.id
        }

        fun setData(messages: List<Message>) {
            this.messages.clear()
            this.messages.addAll(messages)
        }

    }

}