package sn0ww01f.project.foodie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sn0ww01f.project.foodie.databinding.ItemCategoryBinding

class CategoryAdapter(
    private val categories: List<String>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position], onItemClick)
    }

    override fun getItemCount(): Int = categories.size

    class CategoryViewHolder(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: String, onItemClick: (String) -> Unit) {
            binding.textViewCategoryName.text = category
            binding.root.setOnClickListener { onItemClick(category) }
        }
    }
}
