package ru.kirovsky.myapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.kirovsky.myapplication.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.fragment.findNavController
import android.widget.Toast

class CatalogFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_catalog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewProducts)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val products = getMockProducts()

        val adapter = ProductAdapter(products) { product ->
            val prefs = PrefsHelper(requireContext())
            prefs.saveSelectedProductId(product.id)

            findNavController().navigate(R.id.productDetailFragment)
        }

        recyclerView.adapter = adapter

    }
    private fun getMockProducts(): List<Product> {
        return listOf(
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
}

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val category: String,
    val string: String,
    val imageId: Int
)

class ProductAdapter(
    private val products: List<Product>,
    private val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<android.widget.TextView>(R.id.textViewProductName)
        val price = view.findViewById<android.widget.TextView>(R.id.textViewProductPrice)
        val category = view.findViewById<android.widget.TextView>(R.id.textViewProductCategory)
        val addButton = view.findViewById<android.widget.Button>(R.id.buttonAddToCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]

        holder.name.text = product.name
        holder.price.text = "${product.price} руб."
        holder.category.text = product.category

        holder.itemView.setOnClickListener {
            onItemClick(product)  //
        }

        holder.addButton.setOnClickListener {
            val prefs = PrefsHelper(holder.itemView.context)
            prefs.addToCart(product.id, product.name, product.price)

            Toast.makeText(
                holder.itemView.context,
                "✅ Добавлено в корзину: ${product.name}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun getItemCount() = products.size
}