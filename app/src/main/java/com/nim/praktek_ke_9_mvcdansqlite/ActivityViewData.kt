package com.nim.praktek_ke_9_mvcdansqlite

import android.app.Dialog
import android.app.ListActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import java.util.*


class ActivityViewData : ListActivity(), AdapterView.OnItemLongClickListener {
    //inisialisasi kontroller
    private var dataSource: DBDataSource? = null

    //inisialisasi arraylist
    private var values: ArrayList<Barang>? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_data)
        dataSource = DBDataSource(this)
        // buka kontroller
        dataSource!!.open()

        // ambil semua data barang
        values = dataSource!!.getAllBarang()

        // masukkan data barang ke array adapter
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, values!!
        )

        // set adapter pada list
        listAdapter = adapter
        //listView.onItemLongClickListener = this

        // mengambil listview untuk diset onItemLongClickListener
        val lv: ListView = findViewById<View>(android.R.id.list) as ListView
        lv.onItemLongClickListener = this
        lv.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
            val barang = listAdapter.getItem(position) as Barang
            switchToGetData(barang.id)
        })
    }

    //apabila ada long click
    override fun onItemLongClick(
        adapter: AdapterView<*>?, v: View?, pos: Int,
        id: Long
    ): Boolean {

        //tampilkan alert dialog
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_view)
        dialog.setTitle("Pilih Aksi")
        dialog.show()
        val b = listAdapter.getItem(pos) as Barang
        val editButton = dialog.findViewById(R.id.button_edit_data) as Button
        val delButton = dialog.findViewById(R.id.button_delete_data) as Button

        //apabila tombol edit diklik
        editButton.setOnClickListener(
            object : View.OnClickListener {
                override fun onClick(v: View?) {
                    // TODO Auto-generated method stub
                    switchToEdit(b.id)
                    dialog.dismiss()
                }
            }
        )

        //apabila tombol delete di klik
        delButton.setOnClickListener(
            object : View.OnClickListener {
                override fun onClick(v: View?) {
                    // Delete barang
                    dataSource!!.deleteBarang(b.id)
                    dialog.dismiss()
                    finish()
                    startActivity(intent)
                }
            }
        )
        return true
    }

    //method untuk edit data
    fun switchToEdit(id: Long) {
        val b = dataSource!!.getBarang(id)
        val i = Intent(this, ActivityEditData::class.java)
        val bun = Bundle()
        bun.putLong("id", b!!.id)
        bun.putString("nama", b.nama_barang)
        bun.putString("merk", b.merk_barang)
        bun.putString("harga", b.harga_barang)
        i.putExtras(bun)
        finale()
        startActivity(i)
    }

    //method untuk get single data
    fun switchToGetData(id: Long) {
        val b = dataSource!!.getBarang(id)
        val i = Intent(this, ActivityViewSingleData::class.java)
        val bun = Bundle()
        bun.putLong("id", b!!.id)
        bun.putString("nama", b.nama_barang)
        bun.putString("merk", b.merk_barang)
        bun.putString("harga", b.harga_barang)
        i.putExtras(bun)
        dataSource!!.close()
        startActivity(i)
    }

    //method yang dipanggil ketika edit data selesai
    fun finale() {
        this@ActivityViewData.finish()
        dataSource!!.close()
    }

    override fun onResume() {
        dataSource!!.open()
        super.onResume()
    }

    override fun onPause() {
        dataSource!!.close()
        super.onPause()
    }

}