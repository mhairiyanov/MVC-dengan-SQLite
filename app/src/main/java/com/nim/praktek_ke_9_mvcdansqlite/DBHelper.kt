package com.nim.praktek_ke_9_mvcdansqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class DBHelper(context: Context?) :
    SQLiteOpenHelper(context, db_name, null, db_version) {
    //mengeksekusi perintah SQL di atas untuk membuat tabel database baru
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(db_create)
    }

    // dijalankan apabila ingin mengupgrade database
    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        Log.w(
            DBHelper::class.java.name,
            "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data"
        )
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    companion object {
        /** deklarasi konstanta-konstanta yang digunakan pada database, seperti nama tabel,
         * nama-nama kolom, nama database, dan versi dari database  */
        const val TABLE_NAME = "data_inventori"
        const val COLUMN_ID = "_id"
        const val COLUMN_NAME = "nama_barang"
        const val COLUMN_MERK = "merk_barang"
        const val COLUMN_HARGA = "harga_barang"
        private const val db_name = "inventori.db"
        private const val db_version = 1

        // Perintah SQL untuk membuat tabel database baru
        private const val db_create = ("create table "
                + TABLE_NAME + "("
                + COLUMN_ID + " integer primary key autoincrement, "
                + COLUMN_NAME + " varchar(50) not null, "
                + COLUMN_MERK + " varchar(50) not null, "
                + COLUMN_HARGA + " varchar(50) not null);")
    }
}