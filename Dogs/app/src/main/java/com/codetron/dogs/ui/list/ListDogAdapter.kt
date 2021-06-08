package com.codetron.dogs.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codetron.dogs.data.Dog
import com.codetron.dogs.databinding.ItemDogBinding

class ListDogAdapter : RecyclerView.Adapter<ListDogAdapter.DogViewHolder>() {

    var onItemClickListener: ((id: Int, name: String) -> Unit)? = null

    private var dogs: ArrayList<Dog> = arrayListOf()

    fun updateData(dogs: List<Dog>) {
        this.dogs.clear()
        this.dogs.addAll(dogs)
        notifyDataSetChanged()
    }

    inner class DogViewHolder(private val binding: ItemDogBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dog: Dog) {
            binding.dog = dog
            binding.root.setOnClickListener {
                dog.id?.let { id ->
                    dog.name?.let { name ->
                        onItemClickListener?.invoke(
                            id,
                            name
                        )
                    }
                }
            }
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDogBinding.inflate(inflater, parent, false)
        return DogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        holder.bind(dogs[position])
    }

    override fun getItemCount(): Int {
        return dogs.size
    }
}