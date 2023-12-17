package com.tesan.simplefb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tesan.simplefb.databinding.ItmListBinding
import com.tesan.simplefb.model.User

class AdapterListUser(val list:ArrayList<User>):RecyclerView.Adapter<AdapterListUser.ViewHolder>() {

    private var onItemClickCallback: AdapterListUser.onAdapterListener? = null

    inner class ViewHolder(private val itemBind: ItmListBinding):RecyclerView.ViewHolder(itemBind.root) {
        fun bind(bind: User){
            itemBind.nama.text =bind.nama
            itemBind.alamat.text =bind.alamat
            itemBind.btnDelete.setOnClickListener { onItemClickCallback?.DelClick(bind) }
            itemBind.content.setOnClickListener { onItemClickCallback?.Click(bind) }
        }
    }

    interface onAdapterListener {
        fun Click(list: User)
        fun DelClick(list: User)
    }

    fun setOnItemClick(onItemClickCallback: AdapterListUser.onAdapterListener){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val x = ItmListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(x)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size


}