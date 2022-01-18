package com.ufc.mobile.onlist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ListView
import android.widget.Toast
import com.ufc.mobile.onlist.adapter.ListItemMarketplaceAdapter
import com.ufc.mobile.onlist.data.MarketplaceData
import kotlinx.android.synthetic.main.activity_list_marketplaces.*

class ListMarketplacesActivity: AppCompatActivity() {

    private lateinit var marketplaceDataList: ArrayList<MarketplaceData>
    private lateinit var listMarketplaces: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_marketplaces)

        this.listMarketplaces = findViewById(R.id.listViewMarketplaces)
        this.marketplaceDataList = ArrayList()

        for (i in 1..20) {
            val marketplaceData: MarketplaceData = MarketplaceData("Mercado ${i}")
            this.marketplaceDataList.add(marketplaceData)
        }

        this.listViewMarketplaces.isClickable = true
        this.listViewMarketplaces.adapter = ListItemMarketplaceAdapter(this, marketplaceDataList)
        this.listMarketplaces.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, marketplaceDataList.get(position).name, Toast.LENGTH_SHORT).show()
        }
    }
}