package com.project.patigo.ui.adapters

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.project.patigo.R
import com.project.patigo.data.entity.Pet
import com.project.patigo.databinding.DeleteConfirmDialogBinding
import com.project.patigo.databinding.PetInfoDialogBinding
import com.project.patigo.databinding.PetSingleItemBinding
import com.project.patigo.ui.viewmodels.PetFragmentViewModel

class PetRecyclerViewAdapter(
    var petList: List<Pet>,
    var mContext: Context,
    var viewModel: PetFragmentViewModel,
    ) :
    RecyclerView.Adapter<PetRecyclerViewAdapter.PetViewHolder>() {

    inner class PetViewHolder(var view: PetSingleItemBinding) : ViewHolder(view.root) {
        fun bind(petItem: Pet) {
            with(view) {
                Glide.with(mContext).load(petItem.petPicture).into(petImageView);
                petGenderImage.setImageResource(if (petItem.petGender) R.drawable.male_ic else R.drawable.female_ic)
                petInfoButton.setOnClickListener { }
                petDeleteButton.setOnClickListener {
                    showDeleteConfirmDialogBox(positiveButton = {
                        viewModel.deletePet(petId = petItem.petId, userId = petItem.userId)
                    })
                }
                petTypeTextView.text = petItem.petType
                petNameTextView.text = petItem.petName
                petWeightTextView.text = petItem.petWeight.toString()
                petAgeTextView.text = petItem.petAge.toString()
                petInfoButton.setOnClickListener {
                    infoDialog(petItem)
                }


            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val view = PetSingleItemBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return PetViewHolder(view)
    }

    override fun getItemCount(): Int = petList.size

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val currentPetItem = petList[position]

        holder.bind(currentPetItem)
    }

    private fun showDeleteConfirmDialogBox(
        positiveButton: () -> Unit,
    ) {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        val view =
            DeleteConfirmDialogBinding.inflate(LayoutInflater.from(mContext), null, false)
        view.infoDescription.text =
            "Seçmiş olduğunuz evcil hayvanı kaldırmak istediğinize emin misiniz ?"
        dialog.setContentView(view.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        view.positiveButton.setOnClickListener {
            positiveButton()
            dialog.dismiss()
        }
        view.negativeButton.setOnClickListener {

            dialog.dismiss()
        }

        dialog.show()
    }

    private fun infoDialog(petItem: Pet): Unit {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        val view = PetInfoDialogBinding.inflate(LayoutInflater.from(mContext), null, false)
        dialog.setContentView(view.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        with(view) {
            petNameTextView.text = petItem.petName
            petInfoTextView.text =
                if (petItem.petInfo.isNullOrBlank()) "Herhangi bir açıklama mevcut değil..." else petItem.petInfo

            closeButton.setOnClickListener {
                dialog.dismiss()
            }
        }

        dialog.show()
    }

}