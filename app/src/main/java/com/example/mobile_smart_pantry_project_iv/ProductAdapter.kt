package com.example.mobile_smart_pantry_project_iv

import android.content.Context
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

        // ustaw tekst produktu
        binding.textProduct.text =
            "${product.nazwa} - ${product.ilosc} (${product.kategoria})"

        // początkowo panel przycisków ukryty
        binding.buttonPanel.visibility = View.GONE

        // kliknięcie w TextView pokazuje/ukrywa panel dla tego elementu
        binding.textProduct.setOnClickListener {
            binding.buttonPanel.visibility =
                if (binding.buttonPanel.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }

        // przycisk +
        binding.buttonAdd.setOnClickListener {
            product.ilosc += 1
            notifyDataSetChanged()
        }

        // przycisk -
        binding.buttonSubtract.setOnClickListener {
            if (product.ilosc > 0) {
                product.ilosc -= 1
                notifyDataSetChanged()
            }
        }

        // przycisk Usuń
        binding.buttonDelete.setOnClickListener {
            products.removeAt(position)
            notifyDataSetChanged()
        }

        return binding.root
    }
}