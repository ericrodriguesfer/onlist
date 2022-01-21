package com.ufc.mobile.onlist.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.ufc.mobile.onlist.R
import com.ufc.mobile.onlist.data.ListData

class ListItemListAdapter (private val context: Activity, private val listListData: ArrayList<ListData>): ArrayAdapter<ListData>(context, R.layout.list_item_list_list, listListData) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.list_item_list_list, null)

        val listName: TextView = view.findViewById(R.id.titleListItemList)
        val marketName: TextView = view.findViewById(R.id.marketListItemList)

        listName.text = listListData[position].name
        marketName.text = listListData[position].market

        return view
    }

}