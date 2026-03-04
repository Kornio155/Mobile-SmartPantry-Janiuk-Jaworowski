package com.example.mobile_smart_pantry_project_iv

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

        setupFilters()

        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {}

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                adapter.filter(s.toString())
            }
        })

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

    private fun setupFilters() {
        binding.btnFilterAll.setOnClickListener { adapter.filterByCategory("Wszystkie") }
        binding.btnFilterDry.setOnClickListener { adapter.filterByCategory("Produkty sypkie") }
        binding.btnFilterSpices.setOnClickListener { adapter.filterByCategory("Przyprawy") }
        binding.btnFilterOils.setOnClickListener { adapter.filterByCategory("Oleje") }
        binding.btnFilterCans.setOnClickListener { adapter.filterByCategory("Konserwy") }
        binding.btnFilterBreakfast.setOnClickListener { adapter.filterByCategory("Produkty śniadaniowe") }
        binding.btnFilterDairy.setOnClickListener { adapter.filterByCategory("Nabiał") }
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

            adapter.filterByCategory("Wszystkie") // Initial filter application

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}