package com.example.homelastversion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homelastversion.databinding.RecyclerItemBinding

class WantedAdapter(val listner: Listner) : RecyclerView.Adapter<WantedAdapter.WantedViewHilder>(){
    class WantedViewHilder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = RecyclerItemBinding.bind(itemView)
        fun bind(wontedModel: WontedModel, listner: Listner) = with(binding){
            nickname.text = wontedModel.nicknames
            itemView.setOnClickListener {
                listner.onClickItem(wontedModel)
            }
        }
    }
    interface Listner {
        fun onClickItem(wontedModel: WontedModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WantedViewHilder {
        return WantedViewHilder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false))
    }

    override fun onBindViewHolder(holder: WantedViewHilder, position: Int) {
        holder.bind(Global.wanted[position], listner)
    }

    override fun getItemCount() = Global.wanted.size
}