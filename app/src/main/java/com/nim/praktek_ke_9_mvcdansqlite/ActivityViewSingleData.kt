package com.nim.praktek_ke_9_mvcdansqlite

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class ActivityViewSingleData : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_single_data)

        val tvNama = findViewById(R.id.tv_nama_barang) as TextView
        val tvMerk = findViewById(R.id.tv_merk_barang) as TextView
        val tvHarga = findViewById(R.id.tv_harga_barang) as TextView

        println("APPINVENT " + intent.extras!!.getString("nama"))
        tvNama.text = "Barang " + intent.extras!!.getString("nama")
        tvMerk.text = "Merk " + intent.extras!!.getString("merk")
        tvHarga.text = "Harga " + intent.extras!!.getString("harga")

        val buttonOK: Button = findViewById(R.id.bt_ok) as Button
        buttonOK.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })
    }
}