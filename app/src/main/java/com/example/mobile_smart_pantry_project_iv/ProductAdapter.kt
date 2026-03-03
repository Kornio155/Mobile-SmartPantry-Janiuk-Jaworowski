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

    private var expandedPosition: Int = -1

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

        if (position == expandedPosition) {
            binding.buttonPanel.visibility = View.VISIBLE
        } else {
            binding.buttonPanel.visibility = View.GONE
        }

        binding.textProduct.setOnClickListener {

            expandedPosition =
                if (expandedPosition == position) {
                    -1
                } else {
                    position
                }

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
            products.removeAt(position)
            expandedPosition = -1
            notifyDataSetChanged()
        }

        return binding.root
    }
}