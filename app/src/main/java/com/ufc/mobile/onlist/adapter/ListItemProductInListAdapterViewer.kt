package com.ufc.mobile.onlist.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.ufc.mobile.onlist.R
import com.ufc.mobile.onlist.model.ProductInList

class ListItemProductInListAdapterViewer (private val context: Activity, private val listProductInList: ArrayList<ProductInList>): ArrayAdapter<ProductInList>(context, R.layout.list_item_products_in_listview, listProductInList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.list_item_products_in_listview_viewer, null)

        val productName: TextView = view.findViewById(R.id.titleProductListItemViewer)
        val productPrice: TextView = view.findViewById(R.id.quantityProductListItemViewer)
        val productQuantity: TextView = view.findViewById(R.id.priceProductListItemViewer)

        productName.text = listProductInList[position].name
        productPrice.text = "Preço: R$ " + listProductInList[position].price.toString()
        productQuantity.text = "Quantidade: 1"

        return view
    }

}
