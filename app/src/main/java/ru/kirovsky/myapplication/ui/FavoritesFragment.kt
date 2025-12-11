package ru.kirovsky.myapplication.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.kirovsky.myapplication.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import android.widget.Toast
import androidx.navigation.fragment.findNavController

class FavoritesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<android.widget.Button>(R.id.buttonBack).setOnClickListener {
            findNavController().navigateUp()
        }

        loadFavorites(view)
    }

    private fun loadFavorites(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewFavorites)
        val emptyLayout = view.findViewById<View>(R.id.layoutEmptyFavorites)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val prefs = PrefsHelper(requireContext())
        val favoritesJson = prefs.getFavorites()

        if (favoritesJson == "[]" || favoritesJson.length < 3) {
            recyclerView.visibility = View.GONE
            emptyLayout.visibility = View.VISIBLE
        } else {
            try {
                val jsonArray = JSONArray(favoritesJson)

                if (jsonArray.length() == 0) {
                    recyclerView.visibility = View.GONE
                    emptyLayout.visibility = View.VISIBLE
                } else {
                    val favorites = mutableListOf<Product>()

                    for (i in 0 until jsonArray.length()) {
                        val item = jsonArray.getJSONObject(i)
                        favorites.add(
                            Product(
                                item.getInt("id"),
                                item.getString("name"),
                                item.getDouble("price"),
                                "Избранное",
                                "Песочное печенье с нежным вкусом. Отлично к чаю. Упаковка: 200г.",
                                R.drawable.milk
                            )
                        )
                    }

                    recyclerView.visibility = View.VISIBLE
                    emptyLayout.visibility = View.GONE

                    recyclerView.adapter = ProductAdapter(favorites) { product ->
                        val prefs = PrefsHelper(requireContext())
                        prefs.saveSelectedProductId(product.id)

                        findNavController().navigate(R.id.productDetailFragment)
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(),
                    "❌ Ошибка: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
                recyclerView.visibility = View.GONE
                emptyLayout.visibility = View.VISIBLE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        view?.let { loadFavorites(it) }
    }
}