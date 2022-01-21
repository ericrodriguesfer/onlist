package com.ufc.mobile.onlist.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.ufc.mobile.onlist.R
import com.ufc.mobile.onlist.data.ProductData

class ListItemProductAdapter (private val context: Activity, private val listProductData: ArrayList<ProductData>): ArrayAdapter<ProductData>(context, R.layout.list_item_list_products, listProductData) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.list_item_list_products, null)

        val productName: TextView = view.findViewById(R.id.titleProductItemList)
        val produtPrice: TextView = view.findViewById(R.id.priceProductItemList)

        productName.text = listProductData[position].name
        produtPrice.text = "Preço: R$ " + listProductData[position].price.toString()

        return view
    }

}