package com.example.coffeeapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeeapp.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.toOrderListBtn.setOnClickListener {
            val intent = Intent(this, OrderListActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        googleMap.setOnMarkerClickListener(this)

        val starbucks1 = LatLng(34.02716, -118.33436)
        val s1 = mMap.addMarker(MarkerOptions().position(starbucks1).title("Starbucks 1"))
        if (s1 != null) {
            s1.tag = "1"
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(starbucks1))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14.0f))

        val starbucks2 = LatLng(34.05164, -118.34394);
        val s2 = mMap.addMarker(MarkerOptions().position(starbucks2).title("Starbucks 2"))
        if (s2 != null) {
            s2.tag = "2"
        }

        val starbucks3 = LatLng(34.05799, -118.36369);
        val s3 = mMap.addMarker(MarkerOptions().position(starbucks3).title("Starbucks 3"))
        if (s3 != null) {
            s3.tag = "3"
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val dialog = BottomSheetDialog(this)

        val view = layoutInflater.inflate(R.layout.coffee_bottom_sheet, null)
        val closeDialogBtn = view.findViewById<ImageButton>(R.id.close_dialog_btn)
        val coffee_img = view.findViewById<ImageView>(R.id.coffee_pic)
        val coffee_name = view.findViewById<TextView>(R.id.coffee_name)
        val order_btn = view.findViewById<Button>(R.id.order_btn)
        val price = view.findViewById<TextView>(R.id.price)

        closeDialogBtn.setOnClickListener {
            dialog.dismiss();
        }

        val tag = marker.tag
        Log.d("orderlist", "Tag: " + tag)
        if (tag == "1"){
            coffee_img.setImageResource(R.drawable.cappuccino)
            coffee_name.text = "Cappuccino"
            price.text = "250"
        }
        else if (tag=="2"){
            coffee_img.setImageResource(R.drawable.latte)
            coffee_name.text = "Latte"
            price.text = "320"
        }
        else if (tag=="3"){
            coffee_img.setImageResource(R.drawable.mocaccino);
            coffee_name.text = "Mochachino"
            price.text = "400"
        }

        val sharedPref = this?.getSharedPreferences("data", Context.MODE_PRIVATE)

        if (sharedPref.getBoolean(tag.toString(), false)){
            order_btn.text = "dismiss"
        }else{
            order_btn.text = "order"
        }

        order_btn.setOnClickListener {

            if (order_btn.text=="dismiss"){
                val sharedPref = this?.getSharedPreferences("data", Context.MODE_PRIVATE)
                with (sharedPref.edit()) {
                    putBoolean(tag.toString(), false)
                    apply()
                }
                order_btn.text = "order"
            }else{
                val sharedPref = this?.getSharedPreferences("data", Context.MODE_PRIVATE)
                with (sharedPref.edit()) {
                    var order_list = ""
                    putBoolean(tag.toString(), true)
                    apply()
                }
                order_btn.text = "dismiss"
            }

        }

        dialog.setCancelable(false)

        dialog.setContentView(view)

        dialog.show()

        return false
    }
}