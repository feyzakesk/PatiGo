package com.project.patigo.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.patigo.databinding.SingleServiceLayoutBinding

class ServiceRecyclerViewAdapter(
    private val mContext: Context,
    private val services: List<String>,
) : RecyclerView.Adapter<ServiceRecyclerViewAdapter.ServiceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val view = SingleServiceLayoutBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return ServiceViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val service = services[position]
        holder.bind(service)
    }

    override fun getItemCount(): Int = services.size

    inner class ServiceViewHolder(var view: SingleServiceLayoutBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(service: String) {

            view.serviceContent.text=service;
        }
    }
}
