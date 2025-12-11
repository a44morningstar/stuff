package ru.kirovsky.myapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.kirovsky.myapplication.R
import androidx.navigation.fragment.findNavController
import android.widget.Toast

class AuthFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emailEditText = view.findViewById<android.widget.EditText>(R.id.editTextEmail)
        val passwordEditText = view.findViewById<android.widget.EditText>(R.id.editTextPassword)

        view.findViewById<android.widget.Button>(R.id.buttonLogin).setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
            } else {
                val prefs = PrefsHelper(requireContext())
                prefs.saveUserEmail(email)

                Toast.makeText(requireContext(), "Вход выполнен: $email", Toast.LENGTH_SHORT).show()

                findNavController().navigateUp()
            }
        }

        view.findViewById<android.widget.Button>(R.id.buttonRegister).setOnClickListener {
            Toast.makeText(requireContext(), "Регистрация пока не доступна", Toast.LENGTH_SHORT).show()
        }
    }
}