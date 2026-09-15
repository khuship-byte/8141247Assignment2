package com.example.assignment2.ui.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment2.R
import com.example.assignment2.data.model.Animal
import com.example.assignment2.util.AnimalImageMapper

class AnimalAdapter(
    private val onAnimalClick: (Animal) -> Unit
) : RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder>() {

    private var animals: List<Animal> = emptyList()

    class AnimalViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        val ivAnimal: ImageView =
            itemView.findViewById(R.id.ivAnimal)

        val tvAnimalName: TextView =
            itemView.findViewById(R.id.tvAnimalName)

        val tvScientificName: TextView =
            itemView.findViewById(R.id.tvScientificName)

        val tvViewDetails: TextView =
            itemView.findViewById(R.id.tvViewDetails)

        val tvCardFavorite: TextView =
            itemView.findViewById(R.id.tvCardFavorite)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AnimalViewHolder {

        val view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_animal,
                parent,
                false
            )

        return AnimalViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: AnimalViewHolder,
        position: Int
    ) {

        val animal = animals[position]

        // Display API information
        holder.tvAnimalName.text =
            animal.species

        holder.tvScientificName.text =
            animal.scientificName


        // Get local image from AnimalImageMapper
        holder.ivAnimal.setImageResource(
            AnimalImageMapper.getImageResource(
                animal.species
            )
        )


        // Check whether this animal is saved as a favorite
        val sharedPreferences =
            holder.itemView.context.getSharedPreferences(
                FAVORITES_PREFERENCES,
                Context.MODE_PRIVATE
            )

        val favoriteKey =
            "favorite_${animal.species}"

        val isFavorite =
            sharedPreferences.getBoolean(
                favoriteKey,
                false
            )

        holder.tvCardFavorite.visibility =
            if (isFavorite) {
                View.VISIBLE
            } else {
                View.GONE
            }


        // Whole card opens Details
        holder.itemView.setOnClickListener {

            onAnimalClick(animal)
        }


        // VIEW DETAILS also opens Details
        holder.tvViewDetails.setOnClickListener {

            onAnimalClick(animal)
        }
    }


    override fun getItemCount(): Int {

        return animals.size
    }


    fun submitList(
        newAnimals: List<Animal>
    ) {

        animals = newAnimals

        notifyDataSetChanged()
    }


    fun refreshFavorites() {

        notifyDataSetChanged()
    }


    companion object {

        private const val FAVORITES_PREFERENCES =
            "animal_favorites"
    }
}