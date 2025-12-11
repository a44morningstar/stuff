package ru.kirovsky.myapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.kirovsky.myapplication.R
import androidx.navigation.fragment.findNavController
import android.widget.Toast

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userNameTextView = view.findViewById<android.widget.TextView>(R.id.textViewUserName)
        val loginButton = view.findViewById<android.widget.Button>(R.id.buttonLogin)
        val favoritesCard = view.findViewById<androidx.cardview.widget.CardView>(R.id.cardFavorites)
        val contactsCard = view.findViewById<androidx.cardview.widget.CardView>(R.id.cardContacts)

        val prefs = PrefsHelper(requireContext())

        if (prefs.isUserLoggedIn()) {
            userNameTextView.text = prefs.getUserEmail()
            loginButton.text = "Выйти из аккаунта"
        } else {
            userNameTextView.text = "Гость"
            loginButton.text = "Войти в аккаунт"
        }

        loginButton.setOnClickListener {
            if (prefs.isUserLoggedIn()) {
                prefs.clearUserEmail()
                Toast.makeText(requireContext(), "Вы вышли из аккаунта", Toast.LENGTH_SHORT).show()
                userNameTextView.text = "Гость"
                loginButton.text = "Войти в аккаунт"
            } else {
                findNavController().navigate(R.id.authFragment)
            }
        }

        favoritesCard.setOnClickListener {
            findNavController().navigate(R.id.favoritesFragment)
        }

        contactsCard.setOnClickListener {
            findNavController().navigate(R.id.contactsFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        val userNameTextView = view?.findViewById<android.widget.TextView>(R.id.textViewUserName)
        val loginButton = view?.findViewById<android.widget.Button>(R.id.buttonLogin)
        val prefs = PrefsHelper(requireContext())

        if (userNameTextView != null && loginButton != null) {
            if (prefs.isUserLoggedIn()) {
                userNameTextView.text = prefs.getUserEmail()
                loginButton.text = "Выйти из аккаунта"
            } else {
                userNameTextView.text = "Гость"
                loginButton.text = "Войти в аккаунт"
            }
        }
    }
}