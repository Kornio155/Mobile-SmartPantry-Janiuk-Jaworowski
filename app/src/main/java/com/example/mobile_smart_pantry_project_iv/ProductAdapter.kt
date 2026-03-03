package com.example.mobile_smart_pantry_project_iv

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.mobile_smart_pantry_project_iv.databinding.ItemProductBinding
import com.example.mobile_smart_pantry_project_iv.model.Product

class ProductAdapter(
    private val context: Context,
    private val products: MutableList<Product>
) : BaseAdapter() {

    private var filteredProducts: MutableList<Product> = products.toMutableList()
    private var expandedPosition: Int = -1

    override fun getCount(): Int = filteredProducts.size
    override fun getItem(position: Int): Any = filteredProducts[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val binding = ItemProductBinding.inflate(LayoutInflater.from(context), parent, false)
        val product = filteredProducts[position]

        binding.textProduct.text =
            "${product.nazwa} - ${product.ilosc} (${product.kategoria})"

        if (product.ilosc < 3) {
            binding.textProduct.setTextColor(Color.RED)
        } else if(product.ilosc < 5){
            binding.textProduct.setTextColor(Color.YELLOW)
        } else{
            binding.textProduct.setTextColor(Color.GREEN)
        }

        if (position == expandedPosition) {
            binding.buttonPanel.visibility = View.VISIBLE
        } else {
            binding.buttonPanel.visibility = View.GONE
        }

        binding.textProduct.setOnClickListener {
            expandedPosition =
                if (expandedPosition == position) -1 else position
            notifyDataSetChanged()
        }

        binding.buttonAdd.setOnClickListener {
            product.ilosc += 1
            notifyDataSetChanged()
        }

        binding.buttonSubtract.setOnClickListener {
            if (product.ilosc > 0) {
                product.ilosc -= 1
                notifyDataSetChanged()
            }
        }

        binding.buttonDelete.setOnClickListener {
            products.remove(product)
            filteredProducts.removeAt(position)
            expandedPosition = -1
            notifyDataSetChanged()
        }

        return binding.root
    }

    fun filter(query: String) {

        filteredProducts = if (query.trim().isEmpty()) {
            products.toMutableList()
        } else {
            products.filter {
                it.nazwa.contains(query, ignoreCase = true)
            }.toMutableList()
        }

        expandedPosition = -1
        notifyDataSetChanged()
    }
}