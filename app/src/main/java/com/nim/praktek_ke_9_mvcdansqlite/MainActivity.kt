package com.nim.praktek_ke_9_mvcdansqlite

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button


class MainActivity : Activity(), View.OnClickListener {
    private var bTambah: Button? = null
    private var bLihat: Button? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bTambah = findViewById<View>(R.id.button_tambah) as Button
        bTambah!!.setOnClickListener(this)
        bLihat = findViewById<View>(R.id.button_view) as Button
        bLihat!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        // TODO Auto-generated method stub
        when (v.id) {
            R.id.button_tambah -> {
                val i = Intent(this, ActivityTambahData::class.java)
                startActivity(i)
            }
            R.id.button_view -> {
                val i2 = Intent(this, ActivityViewData::class.java)
                startActivity(i2)
            }
        }
    }
}