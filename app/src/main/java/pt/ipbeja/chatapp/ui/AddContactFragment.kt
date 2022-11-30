package pt.ipbeja.chatapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import pt.ipbeja.chatapp.databinding.FragmentAddContactBinding
import pt.ipbeja.chatapp.db.ChatDB
import pt.ipbeja.chatapp.db.Contact


class AddContactFragment : Fragment() {

    companion object {
        const val REQUEST_KEY = "ADD_CONTACT"
        const val NAME_KEY = "CONTACT_NAME"
    }

    private lateinit var binding: FragmentAddContactBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentAddContactBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO alterar para adicionar o contacto à bd e voltar ao ContactsFragment

        binding.addContact.setOnClickListener {
            val contactName = binding.name.text.toString()
            val contact = Contact(contactName)
            ChatDB(requireContext())
                .contactDao()
                .add(contact)

            Log.i(AddContactFragment::class.simpleName, "Contact created $contact")

            findNavController().popBackStack()
        }
    }
}