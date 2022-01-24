package com.ufc.mobile.onlist.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.ufc.mobile.onlist.R
import com.ufc.mobile.onlist.data.ProductInListData

class ListItemProductInListAdapter (private val context: Activity, private val listProductInListData: ArrayList<ProductInListData>): ArrayAdapter<ProductInListData>(context, R.layout.list_item_products_in_listview, listProductInListData) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.list_item_products_in_listview, null)

        val productName: TextView = view.findViewById(R.id.titleProductListItem)
        val productPrice: TextView = view.findViewById(R.id.priceProductListItem)
        val productQuantity: TextView = view.findViewById(R.id.quantityProductListItem)

        productName.text = listProductInListData[position].name
        productPrice.text = "Preço: R$ " + listProductInListData[position].price.toString()
        productQuantity.text = "Quantidade: " + listProductInListData[position].quantity.toString()

        return view
    }

}