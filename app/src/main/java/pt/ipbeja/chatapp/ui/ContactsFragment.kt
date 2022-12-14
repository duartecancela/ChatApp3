package pt.ipbeja.chatapp.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import pt.ipbeja.chatapp.R
import pt.ipbeja.chatapp.databinding.ContactItemBinding
import pt.ipbeja.chatapp.databinding.FragmentContactsBinding
import pt.ipbeja.chatapp.db.ChatDB
import pt.ipbeja.chatapp.db.Contact


class ContactsFragment : Fragment(), MenuProvider {

    private lateinit var binding: FragmentContactsBinding
    private val adapter = ContactsAdapter()
    private val viewModel by viewModels<ContactsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity()
            .addMenuProvider(this, viewLifecycleOwner)


        val contacts = viewModel.getContacts()

        adapter.setData(contacts)
        binding.contactList.adapter = adapter


        binding.addContact.setOnClickListener {
            findNavController().navigate(ContactsFragmentDirections.actionContactsFragmentToAddContactFragment())
        }

    }


    inner class ContactViewHolder(private val binding: ContactItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var contact: Contact
        init {
            binding.root.setOnLongClickListener {

                Snackbar.make(it, "'${contact.name}' has been deleted.", Snackbar.LENGTH_SHORT).show()
                ChatDB(requireContext()).contactDao().delete(contact)
                adapter.remove(adapterPosition)
                true // devolvemos true se tratamos deste evento
            }

            binding.root.setOnClickListener {
                findNavController().navigate(ContactsFragmentDirections.actionContactsFragmentToChatFragment(
                    contact.id
                ))
            }
        }

        fun bind(contact: Contact) {
            this.contact = contact
            binding.name.text = contact.name
        }
    }


    inner class ContactsAdapter(contacts: List<Contact> = mutableListOf()) :
        RecyclerView.Adapter<ContactViewHolder>() {

        private val data: MutableList<Contact> = mutableListOf()


        init {
            data.addAll(contacts)
        }

        fun add(contact: Contact) {
            data.add(contact)
            notifyItemInserted(data.lastIndex)
        }

        fun remove(position: Int) {
            data.removeAt(position)
            notifyItemRemoved(position)
        }

        fun removeAll() {
            viewModel.deleteContacts()
            val size = data.size
            data.clear()
            notifyItemRangeRemoved(0, size);

        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ContactItemBinding.inflate(layoutInflater, parent, false)
            return ContactViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
            val contact = data[position]
            holder.bind(contact)
        }

        override fun getItemCount() = data.size

        fun setData(contacts: List<Contact>) {
            data.clear()
            data.addAll(contacts)
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.itemDeleteContact -> {
                adapter.removeAll()
                Snackbar.make(requireView(), "Contacts deleted", Snackbar.LENGTH_SHORT).show()
                true
            }
            else -> false
        }
    }


}