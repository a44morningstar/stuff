package ru.kirovsky.myapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.kirovsky.myapplication.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import androidx.navigation.fragment.findNavController

class SearchFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editText = view.findViewById<android.widget.EditText>(R.id.editTextSearch)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewSearchResults)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = ProductAdapter(emptyList()) { product ->
            findNavController().navigate(R.id.productDetailFragment)
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    recyclerView.adapter = ProductAdapter(emptyList()) { product ->
                        findNavController().navigate(R.id.productDetailFragment)
                    }
                } else {
                    val filtered = getMockProducts().filter {
                        it.name.contains(s.toString(), ignoreCase = true)
                    }
                    recyclerView.adapter = ProductAdapter(filtered) { product ->
                        findNavController().navigate(R.id.productDetailFragment)
                    }
                }
            }
        })

        view.findViewById<android.widget.Button>(R.id.buttonClear).setOnClickListener {
            editText.text.clear()
        }
    }

    private fun getMockProducts() = listOf(
        Product(
            1,
            "Молоко 'Кировское' 1л",
            85.0,
            "Молочные",
            "Песочное печенье с нежным вкусом. Отлично к чаю. Упаковка: 200г.",
            R.drawable.milk
        ),
        Product(
            2,
            "Хлеб 'Бородинский'",
            45.0,
            "Хлеб",
            "Песочное печенье с нежным вкусом. Отлично к чаю. Упаковка: 200г.",
            R.drawable.bread
        ),
        Product(
            3,
            "Яйца куриные 10шт",
            95.0,
            "Яйца",
            "Песочное печенье с нежным вкусом. Отлично к чаю. Упаковка: 200г.",
            R.drawable.eggs
        ),
        Product(
            4,
            "Сахар 1кг",
            65.0,
            "Бакалея",
            "Песочное печенье с нежным вкусом. Отлично к чаю. Упаковка: 200г.",
            R.drawable.sugar
        ),
        Product(
            5,
            "Масло сливочное",
            120.0,
            "Молочные",
            "Песочное печенье с нежным вкусом. Отлично к чаю. Упаковка: 200г.",
            R.drawable.butter
        ),
        Product(
            6,
            "Колбаса докторская",
            250.0,
            "Мясо",
            "Песочное печенье с нежным вкусом. Отлично к чаю. Упаковка: 200г.",
            R.drawable.sausage
        ),
        Product(
            7,
            "Сыр Российский",
            350.0,
            "Сыры",
            "Песочное печенье с нежным вкусом. Отлично к чаю. Упаковка: 200г.",
            R.drawable.cheese
        ),
        Product(
            8,
            "Чай индийский",
            80.0,
            "Напитки",
            "Песочное печенье с нежным вкусом. Отлично к чаю. Упаковка: 200г.",
            R.drawable.tea
        ),
        Product(
            9,
            "Кофе молотый",
            300.0,
            "Напитки",
            "Песочное печенье с нежным вкусом. Отлично к чаю. Упаковка: 200г.",
            R.drawable.coffee
        ),
        Product(
            10,
            "Печенье 'Юбилейное'",
            75.0,
            "Сладости",
            "Песочное печенье с нежным вкусом. Отлично к чаю. Упаковка: 200г.",
            R.drawable.cookie
        )
    )
}