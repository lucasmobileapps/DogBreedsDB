package com.example.dogbreedsdb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.dogbreedsdb.R
import com.example.dogbreedsdb.model.Breeds

class BreedAdapter(private val breedList: MutableList<Breeds>, private val breedadapterDelegate: BreedAdapterDelegate) : RecyclerView.Adapter<BreedAdapter.BreedViewHolder>() {
    interface BreedAdapterDelegate {
        fun breedSelect(breed: Breeds)
        fun favoriteSelect(breed: Breeds)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BreedAdapter.BreedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.breed_item_view_layout, parent, false)
        return BreedViewHolder(view)

    }

    override fun getItemCount(): Int {
        return breedList.size
    }

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        holder.apply {

            breedName?.text = breedList.get(position).breedname
            breedGroup?.text = breedList.get(position).breedgroup
            breedHeight?.text = breedList.get(position).height
            breedWeight?.text = breedList.get(position).weight
            breedLifespan?.text = breedList.get(position).lifespan
            breedName?.setOnClickListener {
                breedadapterDelegate.breedSelect(breedList.get(position))
            }
            favorite?.setOnClickListener {
                breedadapterDelegate.favoriteSelect(breedList.get(position))
            }
        }
    }
    class BreedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val favorite: ImageView? = view.findViewById(R.id.edit_imageview)
        val breedName: TextView? = view.findViewById(R.id.breedName_textview)
        val breedGroup: TextView? = view.findViewById(R.id.breedGroup_textview)
        val breedHeight: TextView? = view.findViewById(R.id.height_textview)
        val breedWeight: TextView? = view.findViewById(R.id.weight_edittext)
        val breedLifespan: TextView? = view.findViewById(R.id.lifeSpan_textview)
        val viewGroup: ConstraintLayout? = view.findViewById(R.id.allBreeds_recyclerview)

    }
}