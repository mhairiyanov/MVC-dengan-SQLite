package com.nim.praktek_ke_9_mvcdansqlite

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class ActivityEditData : AppCompatActivity(), View.OnClickListener {

    private var dataSource: DBDataSource? = null

    private var id: Long = 0
    private var harga: String? = null
    private var merk: String? = null
    private var nama: String? = null

    private var edNama: EditText? = null
    private var edHarga: EditText? = null
    private var edMerk: EditText? = null

    private var txId: TextView? = null

    private var btnSave: Button? = null
    private var btnCancel: Button? = null

    private var barang: Barang? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_data)

        //inisialisasi variabel
        edNama = findViewById(R.id.editText_nama) as EditText
        edHarga = findViewById(R.id.editText_harga) as EditText
        edMerk = findViewById(R.id.editText_merk) as EditText
        txId = findViewById(R.id.text_id_barang) as TextView

        //buat sambungan baru ke database
        dataSource = DBDataSource(this)
        dataSource!!.open()

        // ambil data barang dari extras
        val bun = this.intent.extras
        id = bun!!.getLong("id")
        harga = bun.getString("harga")
        merk = bun.getString("merk")
        nama = bun.getString("nama")

        //masukkan data-data barang tersebut ke field editor
        txId!!.append(id.toString())
        edNama!!.setText(nama)
        edHarga!!.setText(harga)
        edMerk!!.setText(merk)

        //set listener pada tombol
        btnSave = findViewById(R.id.button_save_update) as Button
        btnSave!!.setOnClickListener(this)
        btnCancel = findViewById(R.id.button_cancel_update) as Button
        btnCancel!!.setOnClickListener(this)
    }

    override  fun onClick(v: View) {
        // TODO Auto-generated method stub
        when (v.getId()) {
            R.id.button_save_update -> {
                barang = Barang()
                barang!!.harga_barang = edHarga!!.text.toString()
                barang!!.nama_barang = edNama!!.text.toString()
                barang!!.merk_barang = edMerk!!.text.toString()
                barang!!.id = id
                dataSource!!.updateBarang(barang!!)
                val i = Intent(this, ActivityViewData::class.java)
                startActivity(i)
                this@ActivityEditData.finish()
                dataSource!!.close()
            }
            R.id.button_cancel_update -> {
                finish()
                dataSource!!.close()
            }
        }
    }
}