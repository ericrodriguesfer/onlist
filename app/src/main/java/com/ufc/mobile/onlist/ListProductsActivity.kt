package com.ufc.mobile.onlist

import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ufc.mobile.onlist.adapter.ListItemProductAdapter
import com.ufc.mobile.onlist.data.ProductData
import kotlinx.android.synthetic.main.activity_list_products.*

class ListProductsActivity: AppCompatActivity() {

    private lateinit var productsDataList: ArrayList<ProductData>
    private lateinit var listProducts: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_products)

        this.listProducts = findViewById(R.id.listViewProducts)
        this.productsDataList = ArrayList()

        for (i in 1..20) {
            val productData: ProductData = ProductData("Produto ${i}", (i * 1.0))
            this.productsDataList.add(productData)
        }

        this.listViewProducts.isClickable = true
        this.listViewProducts.adapter = ListItemProductAdapter(this, productsDataList)
        this.listProducts.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, productsDataList.get(position).name, Toast.LENGTH_SHORT).show()
        }
    }
}