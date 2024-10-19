package co.touchlab.dogify

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import co.touchlab.dogify.databinding.ItemBreedBinding
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

/**
 * Adapter class for binding the dog breeds and their images to the RecyclerView.
 * Uses Glide for efficient image loading with caching and error handling.
 */
class BreedAdapter : RecyclerView.Adapter<BreedAdapter.BreedViewHolder>() {

    private var breedList: List<Breed> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedViewHolder {
        val binding = ItemBreedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BreedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        val breed = breedList[position]
        holder.bind(breed)
    }

    override fun getItemCount(): Int = breedList.size

    /**
     * Updates the breed list and notifies the RecyclerView to rebind.
     */
    fun submitList(breeds: List<Breed>) {
        breedList = breeds
        notifyDataSetChanged()
    }

    /**
     * ViewHolder class for binding each breed item to the UI.
     */
    inner class BreedViewHolder(private val binding: ItemBreedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(breed: Breed) {
            binding.breedName.text = breed.name

            // Setup Glide with resizing, caching, placeholders, and error handling
            val requestOptions = RequestOptions()
                .override(300, 300)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error_placeholder)

            // Load the breed image with Glide
            Glide.with(binding.root.context)
                .setDefaultRequestOptions(requestOptions)
                .load(breed.imageUrl)
                .into(binding.breedImage)
        }
    }
}
