package ru.kirovsky.myapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.kirovsky.myapplication.R
import org.json.JSONArray
import android.widget.Toast

class CartFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateCartView(view)
    }

    private fun updateCartView(view: View) {
        val emptyTextView = view.findViewById<android.widget.TextView>(R.id.textViewEmptyCart)
        val totalTextView = view.findViewById<android.widget.TextView>(R.id.textViewTotal)
        val checkoutButton = view.findViewById<android.widget.Button>(R.id.buttonCheckout)

        val prefs = PrefsHelper(requireContext())
        val cartJson = prefs.getCart()

        try {
            val jsonArray = JSONArray(cartJson)

            if (jsonArray.length() == 0) {
                emptyTextView.text = "🛒 Корзина пуста\nДобавьте товары из каталога"
                totalTextView.text = "0 руб."
                checkoutButton.isEnabled = false
                checkoutButton.text = "Корзина пуста"
            } else {
                emptyTextView.visibility = View.GONE

                var total = 0.0
                val itemsText = StringBuilder()
                itemsText.append("Товары в корзине:\n\n")

                for (i in 0 until jsonArray.length()) {
                    val item = jsonArray.getJSONObject(i)
                    val price = item.getDouble("price")
                    val quantity = item.getInt("quantity")
                    total += price * quantity

                    itemsText.append("• ${item.getString("name")}\n")
                    itemsText.append("  Цена: ${price} руб. × ${quantity} шт.\n")
                    itemsText.append("  Итого: ${price * quantity} руб.\n\n")
                }

                totalTextView.text = "${total} руб."
                checkoutButton.isEnabled = true
                checkoutButton.text = "Оформить заказ (${jsonArray.length()} товара)"

                val itemsTextView = view.findViewById<android.widget.TextView>(R.id.textViewCartItems)
                if (itemsTextView != null) {
                    itemsTextView.visibility = View.VISIBLE
                    itemsTextView.text = itemsText.toString()
                }
            }
        } catch (e: Exception) {
            emptyTextView.text = "Ошибка загрузки корзины"
            totalTextView.text = "0 руб."
        }

        checkoutButton.setOnClickListener {
            Toast.makeText(requireContext(), "Заказ оформлен! Спасибо за покупку!", Toast.LENGTH_LONG).show()

            prefs.clearCart()

            updateCartView(view)
        }
    }

    override fun onResume() {
        super.onResume()
        view?.let { updateCartView(it) }
    }
}