package com.ufc.mobile.onlist

import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ufc.mobile.onlist.adapter.ListItemListAdapter
import com.ufc.mobile.onlist.data.ListData
import kotlinx.android.synthetic.main.activity_list_lists.*

class ListListsActivity: AppCompatActivity() {

    private lateinit var listsDataList: ArrayList<ListData>
    private lateinit var listLists: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_lists)

        this.listLists = findViewById(R.id.listViewLists)
        this.listsDataList = ArrayList()

        for (i in 1..20) {
            val listData: ListData = ListData("Lista de número ${i}", "Mercaso seu Zé")
            this.listsDataList.add(listData)
        }

        this.listViewLists.isClickable = true
        this.listViewLists.adapter = ListItemListAdapter(this, listsDataList)
        this.listLists.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, listsDataList.get(position).name, Toast.LENGTH_SHORT).show()
        }
    }
}