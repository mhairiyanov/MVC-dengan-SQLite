package com.nim.praktek_ke_9_mvcdansqlite

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class ActivityTambahData : Activity(), View.OnClickListener {
    //inisilisasi elemen-elemen pada layout
    private var buttonSubmit: Button? = null
    private var edNama: EditText? = null
    private var edMerk: EditText? = null
    private var edHarga: EditText? = null

    //inisialisasi kontroller/Data Source
    private var dataSource: DBDataSource? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_data)
        buttonSubmit = findViewById<View>(R.id.buttom_submit) as Button
        buttonSubmit!!.setOnClickListener(this)
        edNama = findViewById<View>(R.id.nama_barang) as EditText
        edHarga = findViewById<View>(R.id.harga_barang) as EditText
        edMerk = findViewById<View>(R.id.merk_barang) as EditText

        // instanstiasi kelas DBDataSource
        dataSource = DBDataSource(this)

        //membuat sambungan baru ke database
        dataSource!!.open()
    }

    //KETIKA Tombol Submit Diklik
    override fun onClick(v: View) {
        // Inisialisasi data barang
        var nama: String? = null
        var merk: String? = null
        var harga: String? = null
        var barang:  //inisialisasi barang baru (masih kosong)
                Barang? = null
        if (edNama!!.text != null && edMerk!!.text != null && edHarga!!.text != null) {
            /* jika field nama, merk, dan harga tidak kosong
             * maka masukkan ke dalam data barang*/
            nama = edNama!!.text.toString()
            merk = edMerk!!.text.toString()
            harga = edHarga!!.text.toString()
        }
        when (v.id) {
            R.id.buttom_submit -> {
                // insert data barang baru
                barang = dataSource!!.createBarang(nama, merk, harga)

                //konfirmasi kesuksesan
                Toast.makeText(
                    this, """
     masuk Barang
     nama${barang.nama_barang}merk${barang.merk_barang}harga${barang.harga_barang}
     """.trimIndent(), Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}