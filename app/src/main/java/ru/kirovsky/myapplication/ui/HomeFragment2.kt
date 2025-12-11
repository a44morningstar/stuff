package ru.kirovsky.myapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.kirovsky.myapplication.R
import androidx.navigation.fragment.findNavController

class HomeFragment2 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home2, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnPromo = view.findViewById<android.widget.Button>(R.id.btnPromo)
        val btnContacts = view.findViewById<android.widget.Button>(R.id.btnContacts)
        val bannerPromo = view.findViewById<androidx.cardview.widget.CardView>(R.id.bannerPromo)

        btnPromo.setOnClickListener {
            findNavController().navigate(R.id.promoFragment)
        }
        bannerPromo.setOnClickListener {
            findNavController().navigate(R.id.promoFragment)
        }
        btnContacts.setOnClickListener {
            findNavController().navigate(R.id.contactsFragment)
        }
    }

}