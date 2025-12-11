package ru.kirovsky.myapplication.ui

import android.content.Context
import android.content.SharedPreferences

class PrefsHelper(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("kirovsky_store", Context.MODE_PRIVATE)

    fun saveUserEmail(email: String) {
        prefs.edit().putString("user_email", email).apply()
    }

    fun getUserEmail(): String {
        return prefs.getString("user_email", "Гость") ?: "Гость"
    }

    fun clearUserEmail() {
        prefs.edit().remove("user_email").apply()
    }

    fun isUserLoggedIn(): Boolean {
        return prefs.getString("user_email", null) != null
    }

    fun addToFavorites(productId: Int, productName: String, price: Double) {
        val current = getFavorites()

        if (current == "[]") {
            val newItem = """{"id":$productId,"name":"$productName","price":$price}"""
            prefs.edit().putString("favorites", "[$newItem]").apply()
        } else {
            val newItem = """,{"id":$productId,"name":"$productName","price":$price}"""
            val newFavorites = current.replace("]", "$newItem]")
            prefs.edit().putString("favorites", newFavorites).apply()
        }
    }

    fun getFavorites(): String {
        return prefs.getString("favorites", "[]") ?: "[]"
    }

    fun clearFavorites() {
        prefs.edit().remove("favorites").apply()
    }

    fun addToCart(productId: Int, productName: String, price: Double) {
        val current = getCart()

        if (current == "[]") {
            val newItem = """{"id":$productId,"name":"$productName","price":$price,"quantity":1}"""
            prefs.edit().putString("cart", "[$newItem]").apply()
        } else {
            val newItem = """,{"id":$productId,"name":"$productName","price":$price,"quantity":1}"""
            val newCart = current.replace("]", "$newItem]")
            prefs.edit().putString("cart", newCart).apply()
        }
    }

    fun getCart(): String {
        return prefs.getString("cart", "[]") ?: "[]"
    }

    fun clearCart() {
        prefs.edit().remove("cart").apply()
    }

    fun getSelectedProductId(): Int {
        return prefs.getInt("selected_product_id", 1) // по умолчанию ID=1 (молоко)
    }
    fun clearSelectedProductId() {
        prefs.edit().remove("selected_product_id").apply()
    }
    fun saveSelectedProductId(productId: Int) {
        prefs.edit().putInt("selected_product_id", productId).apply()
    }
}