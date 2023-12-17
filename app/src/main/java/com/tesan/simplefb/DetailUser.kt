package com.tesan.simplefb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.tesan.simplefb.databinding.ActivityDetailUserBinding
import com.tesan.simplefb.databinding.ActivityMainBinding
import com.tesan.simplefb.model.User
import java.util.UUID

class DetailUser : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private var fb: FirebaseDatabase? = null
    private var ref: DatabaseReference? = null

    private var userid:String? = null
    private var nama:String? = null
    private var alamat:String? = null
    private var umur:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fb = FirebaseDatabase.getInstance()
        ref = fb?.getReference("users")

        userid = intent.getStringExtra("userid")
        Log.e("detial", "userid: $userid")
        if (userid == null || userid == ""){
            Toast.makeText(this, "Halaman Tambah", Toast.LENGTH_SHORT).show()
        }else{
            nama = intent.getStringExtra("nama")
            alamat = intent.getStringExtra("alamat")
            umur = intent.getStringExtra("umur").toString()
            binding.tiAge.setText(umur)
            binding.tiName.setText(nama)
            binding.tiAddress.setText(alamat)

            Toast.makeText(this, "Halaman Edit", Toast.LENGTH_SHORT).show()
        }
        binding.btnSave.setOnClickListener {
            savedata()
            startActivity(Intent(this@DetailUser,MainActivity::class.java))
        }
    }

    private fun savedata() {
        var uuid:String? = null
        if (userid == null || userid == ""){
            val myUuid = UUID.randomUUID()
            uuid = myUuid.toString()
            Toast.makeText(this, "Proses Insert", Toast.LENGTH_SHORT).show()
        }else{
            uuid = userid
            Toast.makeText(this, "Proses Update", Toast.LENGTH_SHORT).show()
        }
        val nama = binding.tiName.text
        val umur = binding.tiAge.text.toString()
        val addreess = binding.tiAddress.text
        val user = User(nama = nama.toString(),alamat = addreess.toString(),umur = umur.toInt())
        ref?.child(uuid.toString())?.setValue(user)
    }
}