package com.example.mobile_smart_pantry_project_iv

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mobile_smart_pantry_project_iv.databinding.ActivityMainBinding
import com.example.mobile_smart_pantry_project_iv.model.Product
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var products: MutableList<Product>
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        products = mutableListOf()

        adapter = ProductAdapter(this, products)

        binding.listViewItems.adapter = adapter

        loadPantry()

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }
    }

    private fun loadPantry() {
        try {
            val inputStream = resources.openRawResource(R.raw.pantry)
            val jsonString = inputStream.bufferedReader().use { it.readText() }

            val jsonObject = JSONObject(jsonString)
            val jsonArray = jsonObject.getJSONArray("produkty")

            products.clear()

            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)

                val id = obj.getInt("id")
                val nazwa = obj.getString("nazwa")
                val ilosc = obj.getInt("ilosc")
                val kategoria = obj.getString("kategoria")

                // jeśli w JSON nie ma zdjecia, ustaw pusty string
                val zdjecie = obj.optString("zdjecie", "")

                val product = Product(
                    id = id,
                    nazwa = nazwa,
                    ilosc = ilosc,
                    kategoria = kategoria,
                    zdjecie = zdjecie
                )

                products.add(product)
            }

            adapter.notifyDataSetChanged()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}