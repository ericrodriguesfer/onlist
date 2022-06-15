package com.ufc.mobile.onlist.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.ufc.mobile.onlist.R
import com.ufc.mobile.onlist.model.Marketplace

class ListItemMarketplaceAdapter (private val context: Activity, private val listMarketplaceData: ArrayList<Marketplace>): ArrayAdapter<Marketplace>(context, R.layout.list_item_list_marketplaces, listMarketplaceData) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.list_item_list_marketplaces, null)

        val marketplaceName: TextView = view.findViewById(R.id.titleMarketplaceItemList)

        marketplaceName.text = listMarketplaceData[position].name

        return view
    }

}