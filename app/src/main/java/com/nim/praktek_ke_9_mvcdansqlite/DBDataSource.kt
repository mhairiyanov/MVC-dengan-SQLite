package com.nim.praktek_ke_9_mvcdansqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.util.Log


class DBDataSource(context: Context?) {
    //inisialiasi SQLite Database
    private var database: SQLiteDatabase? = null

    //inisialisasi kelas DBHelper
    private val dbHelper: DBHelper

    //ambil semua nama kolom
    private val allColumns = arrayOf(
        DBHelper.COLUMN_ID,
        DBHelper.COLUMN_NAME, DBHelper.COLUMN_MERK, DBHelper.COLUMN_HARGA
    )

    //membuka/membuat sambungan baru ke database
    @Throws(SQLException::class)
    fun open() {
        database = dbHelper.writableDatabase
    }

    //menutup sambungan ke database
    fun close() {
        dbHelper.close()
    }

    //method untuk create/insert barang ke database
    fun createBarang(nama: String?, merk: String?, harga: String?): Barang {

        // membuat sebuah ContentValues, yang berfungsi
        // untuk memasangkan data dengan nama-nama
        // kolom pada database
        val values = ContentValues()
        values.put(DBHelper.COLUMN_NAME, nama)
        values.put(DBHelper.COLUMN_MERK, merk)
        values.put(DBHelper.COLUMN_HARGA, harga)

        // mengeksekusi perintah SQL insert data
        // yang akan mengembalikan sebuah insert ID
        val insertId = database!!.insert(
            DBHelper.TABLE_NAME, null,
            values
        )

        // setelah data dimasukkan, memanggil
        // perintah SQL Select menggunakan Cursor untuk
        // melihat apakah data tadi benar2 sudah masuk
        // dengan menyesuaikan ID = insertID
        val cursor = database!!.query(
            DBHelper.TABLE_NAME,
            allColumns, DBHelper.COLUMN_ID + " = " + insertId, null,
            null, null, null
        )

        // pindah ke data paling pertama
        cursor.moveToFirst()

        // mengubah objek pada kursor pertama tadi
        // ke dalam objek barang
        val newBarang = cursorToBarang(cursor)

        // close cursor
        cursor.close()

        // mengembalikan barang baru
        return newBarang
    }

    private fun cursorToBarang(cursor: Cursor): Barang {
        // buat objek barang baru
        val barang = Barang()
        // debug LOGCAT
        Log.v("info", "The getLONG " + cursor.getLong(0))
        Log.v(
            "info",
            "The setLatLng " + cursor.getString(1) + "," + cursor.getString(2)
        )

        /* Set atribut pada objek barang dengan
         * data kursor yang diambil dari database*/barang.id = cursor.getLong(0)
        barang.nama_barang = cursor.getString(1)
        barang.merk_barang = cursor.getString(2)
        barang.harga_barang = cursor.getString(3)

        //kembalikan sebagai objek barang
        return barang
    }

    //mengambil semua data barang
    fun getAllBarang(): ArrayList<Barang>? {
        val daftarBarang = ArrayList<Barang>()

        // select all SQL query
        val cursor = database!!.query(
            DBHelper.TABLE_NAME,
            allColumns, null, null, null, null, null
        )

        // pindah ke data paling pertama
        cursor.moveToFirst()
        // jika masih ada data, masukkan data barang ke
        // daftar barang
        while (!cursor.isAfterLast) {
            val barang = cursorToBarang(cursor)
            daftarBarang.add(barang)
            cursor.moveToNext()
        }
        // Make sure to close the cursor
        cursor.close()
        return daftarBarang
    }

    //ambil satu barang sesuai id
    fun getBarang(id: Long): Barang? {
        var barang = Barang() //inisialisasi barang
        //select query
        val cursor =
            database!!.query(DBHelper.TABLE_NAME, allColumns, "_id =$id", null, null, null, null)
        //ambil data yang pertama
        cursor.moveToFirst()
        //masukkan data cursor ke objek barang
        barang = cursorToBarang(cursor)
        //tutup sambungan
        cursor.close()
        //return barang
        return barang
    }

    //update barang yang diedit
    fun updateBarang(b: Barang) {
        //ambil id barang
        val strFilter = "_id=" + b.id
        //memasukkan ke content values
        val args = ContentValues()
        //masukkan data sesuai dengan kolom pada database
        args.put(DBHelper.COLUMN_NAME, b.nama_barang)
        args.put(DBHelper.COLUMN_MERK, b.merk_barang)
        args.put(DBHelper.COLUMN_HARGA, b.harga_barang)
        //update query
        database!!.update(DBHelper.TABLE_NAME, args, strFilter, null)
    }

    // delete barang sesuai ID
    fun deleteBarang(id: Long) {
        val strFilter = "_id=$id"
        database!!.delete(DBHelper.TABLE_NAME, strFilter, null)
    }

    //DBHelper diinstantiasi pada constructor
    init {
        dbHelper = DBHelper(context)
    }
}