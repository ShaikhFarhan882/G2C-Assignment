package com.example.g2c.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.g2c.CheckListActivity
import com.example.g2c.databinding.ActivityChecklistBinding
import com.example.g2c.databinding.SingleRowBinding
import com.example.g2c.model.CheckList

class CheckListAdapter(private val checkList: ArrayList<CheckList>, private val itemClickListener: ItemClickListener) : RecyclerView.Adapter<CheckListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(SingleRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.data = checkList[position]

        holder.binding.root.setOnClickListener {
            Log.d("Adapter",checkList[position].uniqueKey.toString())
            itemClickListener.onClick(checkList[position])
        }
    }

    override fun getItemCount(): Int {
        return checkList.size
    }

    class ViewHolder(val binding: SingleRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

}