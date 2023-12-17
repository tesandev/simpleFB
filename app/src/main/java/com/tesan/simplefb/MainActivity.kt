package com.tesan.simplefb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.tesan.simplefb.adapter.AdapterListUser
import com.tesan.simplefb.databinding.ActivityMainBinding
import com.tesan.simplefb.model.User
import java.util.UUID

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private var fb:FirebaseDatabase? = null
    private var ref:DatabaseReference? = null
    private var list = ArrayList<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fb = FirebaseDatabase.getInstance()
        ref = fb?.getReference("users")
        getData()
        binding.fabAdd.setOnClickListener {
            startActivity(Intent(this,DetailUser::class.java))
        }
    }

    private fun getData() {
        ref?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.e("getData","onDataChange: $snapshot")
                list.clear()
                for(data in snapshot.children){
                    val id = data.key
                    val nama = data.child("nama").value.toString()
                    val alamat = data.child("alamat").value.toString()
                    val umur = data.child("umur").value.toString()
                    val user = User(userid = id.toString(),nama = nama,alamat = alamat,umur = umur.toInt())
                    list.add(user)
                }
                binding.rvFiredb.setHasFixedSize(true)
                binding.rvFiredb.layoutManager = LinearLayoutManager(applicationContext)
                val adp = AdapterListUser(list)
                adp.notifyDataSetChanged()
                binding.rvFiredb.adapter = adp
                adp.setOnItemClick(object : AdapterListUser.onAdapterListener{
                    override fun Click(list: User) {
                        val intent = Intent(this@MainActivity,DetailUser::class.java)
                        intent.putExtra("userid", list.userid)
                        intent.putExtra("nama", list.nama)
                        intent.putExtra("alamat", list.alamat)
                        intent.putExtra("umur", list.umur.toString())
                        startActivity(intent)
                    }

                    override fun DelClick(list: User) {
                        val selectedId = list.userid.toString()
                        ref?.child(selectedId)?.removeValue()
                    }
                })
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ERR","onCancelled: ${error.toException()}")
            }

        })
    }
}