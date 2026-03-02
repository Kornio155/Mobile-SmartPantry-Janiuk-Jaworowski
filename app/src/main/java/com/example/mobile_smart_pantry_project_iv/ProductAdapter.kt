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

    override fun getCount(): Int = products.size
    override fun getItem(position: Int): Any = products[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val binding = ItemProductBinding.inflate(LayoutInflater.from(context), parent, false)
        val product = products[position]

        binding.textProduct.text =
            "${product.nazwa} - ${product.ilosc} (${product.kategoria})"


        if (product.ilosc < 5) {
            binding.textProduct.setTextColor(Color.RED)
        } else {
            binding.textProduct.setTextColor(Color.BLACK)
        }

        binding.buttonPanel.visibility = View.GONE

        binding.textProduct.setOnClickListener {
            binding.buttonPanel.visibility =
                if (binding.buttonPanel.visibility == View.VISIBLE) View.GONE else View.VISIBLE
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
            products.removeAt(position)
            notifyDataSetChanged()
        }

        return binding.root
    }
}