package com.example.coffeeapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class OrderListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_list)

        val order_cap = findViewById<Button>(R.id.order_cappuccino)
        val order_lat = findViewById<Button>(R.id.order_latte)
        val order_moc = findViewById<Button>(R.id.order_moc)
        val total_sum_txt = findViewById<TextView>(R.id.all_price)
        val sharedPref = this?.getSharedPreferences("data", Context.MODE_PRIVATE)
        var total_sum = 0

        if (sharedPref.getBoolean("1", false)){
            order_cap.text = "dismiss"
            total_sum+=250
        }else{
            order_cap.text = "order"
        }

        if (sharedPref.getBoolean("2", false)){
            order_lat.text = "dismiss"
            total_sum+=320
        }else{
            order_lat.text = "order"
        }

        if (sharedPref.getBoolean("3", false)){
            order_moc.text = "dismiss"
            total_sum+=400
        }else{
            order_moc.text = "order"
        }

        total_sum_txt.text = "Price: "+total_sum.toString()


        order_cap.setOnClickListener {
            if (order_cap.text=="dismiss"){
                with (sharedPref.edit()) {
                    putBoolean("1", false)
                    total_sum-=250
                    total_sum_txt.text = "Price: "+total_sum.toString()
                    apply()
                }
                order_cap.text = "order"
            }else{
                with (sharedPref.edit()) {
                    putBoolean("1", true)
                    total_sum+=250
                    total_sum_txt.text = "Price: "+total_sum.toString()
                    apply()
                }
                order_cap.text = "dismiss"
            }
        }

        order_lat.setOnClickListener {
            if (order_lat.text=="dismiss"){
                with (sharedPref.edit()) {
                    putBoolean("2", false)
                    total_sum-=320
                    total_sum_txt.text = "Price: "+total_sum.toString()
                    apply()
                }
                order_lat.text = "order"
            }else{
                with (sharedPref.edit()) {
                    putBoolean("2", true)
                    total_sum+=320
                    total_sum_txt.text = "Price: "+total_sum.toString()
                    apply()
                }
                order_lat.text = "dismiss"
            }
        }

        order_moc.setOnClickListener {
            if (order_moc.text=="dismiss"){
                with (sharedPref.edit()) {
                    putBoolean("3", false)
                    total_sum-=400
                    total_sum_txt.text = "Price: "+total_sum.toString()
                    apply()
                }
                order_moc.text = "order"
            }else{
                with (sharedPref.edit()) {
                    putBoolean("3", true)
                    total_sum+=400
                    total_sum_txt.text = "Price: "+total_sum.toString()
                    apply()
                }
                order_moc.text = "dismiss"
            }
        }

    }
}