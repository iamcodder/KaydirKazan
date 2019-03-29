package com.nosignalapp.kaydirkazan.Model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nosignalapp.kaydirkazan.R
import kotlinx.android.synthetic.main.soru_karti.view.*

class SoruActivityAdapter(var list: List<soruModel>) : RecyclerView.Adapter<SoruActivityAdapter.viewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {

        val v=LayoutInflater.from(parent.context).inflate(R.layout.soru_karti,parent,false)

        return viewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {

        holder.itemView.soru_karti_tasarimi_ANA_SORU.text = list[position].soru
        holder.itemView.soru_karti_tasarimi_SOL_CEVAP.text=list[position].solCevap
        holder.itemView.soru_karti_tasarimi_SAG_CEVAP.text=list[position].sagCevap

    }


    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}