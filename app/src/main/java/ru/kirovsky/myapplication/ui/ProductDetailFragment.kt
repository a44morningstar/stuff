package ru.kirovsky.myapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.kirovsky.myapplication.R
import android.widget.Toast
import androidx.navigation.fragment.findNavController

class ProductDetailFragment : Fragment() {
    private val products = listOf(
        Product(1, "Молоко 'Кировское' 1л", 85.0, "Молочные", "Свежее пастеризованное молоко высшего качества. Произведено на фермах Кировской области. Срок годности: 10 дней.", R.drawable.milk),
        Product(2, "Хлеб 'Бородинский'", 45.0, "Хлеб", "Ржаной хлеб по традиционному рецепту. С хрустящей корочкой и ароматом тмина. Вес: 500г.", R.drawable.bread),
        Product(3, "Яйца куриные 10шт", 95.0, "Яйца", "Свежие куриные яйца категории С0. Отборные, с ярким желтком. Упаковка: 10 штук.", R.drawable.eggs),
        Product(4, "Сахар 1кг", 65.0, "Бакалея", "Сахар-песок высшего сорта. Рафинированный, идеален для выпечки и напитков.", R.drawable.sugar),
        Product(5, "Масло сливочное", 120.0, "Молочные", "Сливочное масло 82,5% жирности. Натуральное, без растительных жиров.", R.drawable.butter),
        Product(6, "Колбаса докторская", 250.0, "Мясо", "Варёная колбаса высшего сорта. Нежная, с традиционным вкусом. Вес: 300г.", R.drawable.sausage),
        Product(7, "Сыр Российский", 350.0, "Сыры", "Полутвёрдый сыр с нежным сливочным вкусом. Отлично плавится. Вес: 200г.", R.drawable.cheese),
        Product(8, "Чай индийский", 80.0, "Напитки", "Чёрный листовой чай. Ароматный, с насыщенным вкусом. Упаковка: 100г.", R.drawable.tea),
        Product(9, "Кофе молотый", 300.0, "Напитки", "Арабика 100%. Средней обжарки. Ароматный, с шоколадными нотами. 250г.", R.drawable.coffee),
        Product(10, "Печенье 'Юбилейное'", 75.0, "Сладости", "Песочное печенье с нежным вкусом. Отлично к чаю. Упаковка: 200г.", R.drawable.cookie)
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_product_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addToCartButton = view.findViewById<android.widget.Button>(R.id.buttonAddToCart)
        val favoriteButton = view.findViewById<android.widget.Button>(R.id.buttonFavorite)
        val prefs = PrefsHelper(requireContext())
        val productId = prefs.getSelectedProductId()
        val product = products.find { it.id == productId } ?: products[0]
        val imageView = view.findViewById<android.widget.ImageView>(R.id.imageViewProduct)
        val nameTextView = view.findViewById<android.widget.TextView>(R.id.textViewProductName)
        val priceTextView = view.findViewById<android.widget.TextView>(R.id.textViewProductPrice)
        val categoryTextView = view.findViewById<android.widget.TextView>(R.id.textViewProductCategory)
        val descriptionTextView = view.findViewById<android.widget.TextView>(R.id.textViewProductDescription)
        imageView.setImageResource(product.imageId)
        nameTextView.text = product.name
        priceTextView.text = "${product.price} руб."
        categoryTextView.text = product.category
        descriptionTextView.text = product.string
        imageView.setImageResource(product.imageId)

        addToCartButton.setOnClickListener {
            prefs.addToCart(product.id, product.name, product.price)
            Toast.makeText(requireContext(),
                "Добавлено в корзину!",
                Toast.LENGTH_SHORT
            ).show()
        }

        updateFavoriteButton(favoriteButton, prefs, product.id)

        favoriteButton.setOnClickListener {
            val favorites = prefs.getFavorites()

            if (favorites.contains(product.id.toString())) {
                prefs.clearFavorites()
                favoriteButton.setBackgroundColor(0xFFF5F5F5.toInt())
                updateFavoriteButton(favoriteButton, prefs, product.id)
                Toast.makeText(requireContext(),
                    "🗑️ Удалено из избранного",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                prefs.addToFavorites(product.id, product.name, product.price)
                favoriteButton.setBackgroundColor(0xFFFFEBEE.toInt())
                updateFavoriteButton(favoriteButton, prefs, product.id)
                Toast.makeText(requireContext(),
                    "Добавлено в избранное!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        view.findViewById<android.widget.Button>(R.id.buttonBack).setOnClickListener {
            findNavController().navigateUp()
        }
    }
    private fun updateFavoriteButton(button: android.widget.Button, prefs: PrefsHelper, productId: Int) {
        val favorites = prefs.getFavorites()
        if (favorites.contains(productId.toString())) {
            button.text = "★"
            button.setBackgroundColor(0xFFFFEBEE.toInt())
        } else {
            button.text = "☆"
            button.setBackgroundColor(0xFFF5F5F5.toInt())
        }
    }
}

