package com.nim.praktek_ke_9_mvcdansqlite

class Barang {
    var id: Long = 0
    var nama_barang: String? = null
    var merk_barang: String? = null
    var harga_barang: String? = null

    override fun toString(): String {
        return "Barang $nama_barang $merk_barang $harga_barang"
    }
}