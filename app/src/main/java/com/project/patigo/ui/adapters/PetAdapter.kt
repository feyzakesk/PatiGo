package com.project.patigo.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


class PetAdapter(context: Context, pets: List<String>) : ArrayAdapter<String>(context,  android.R.layout.simple_spinner_dropdown_item, pets) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate( android.R.layout.simple_spinner_dropdown_item, parent, false)
        val pet = getItem(position)
        view.findViewById<TextView>(android.R.id.text1).text = pet
        return view
    }
}