package sn0ww01f.project.foodie

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sn0ww01f.project.foodie.databinding.ItemRecipeBinding

class RecipesAdapter(
    private val recipes: List<Recipe>,
    private val onItemClick: (Recipe) -> Unit
) : RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipes[position], onItemClick)
    }

    override fun getItemCount(): Int = recipes.size

    class RecipeViewHolder(private val binding: ItemRecipeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe, onItemClick: (Recipe) -> Unit) {
            binding.textViewRecipeName.text = recipe.name

            if (recipe.imageBase64 != null) {
                val decodedString = Base64.decode(recipe.imageBase64, Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                binding.imageViewRecipeThumbnail.setImageBitmap(bitmap)
            } else {
                binding.imageViewRecipeThumbnail.setImageResource(R.drawable.ic_placeholder)
            }

            binding.textViewRating.text = recipe.rating.toString()

            binding.root.setOnClickListener { onItemClick(recipe) }
        }
    }
}
