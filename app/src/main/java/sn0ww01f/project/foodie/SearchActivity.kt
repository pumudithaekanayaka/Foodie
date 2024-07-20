package sn0ww01f.project.foodie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import sn0ww01f.project.foodie.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var recipeDatabaseHelper: RecipeDatabaseHelper
    private lateinit var adapter: RecipesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recipeDatabaseHelper = RecipeDatabaseHelper(this)

        // Initialize the adapter with an empty list
        adapter = RecipesAdapter(emptyList()) { recipe ->
            // Handle recipe item click
        }

        binding.recyclerViewSearchResults.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewSearchResults.adapter = adapter

        binding.buttonSearch.setOnClickListener {
            val query = binding.editTextSearch.text.toString()
            val recipes = recipeDatabaseHelper.getAllRecipes()
            val filteredRecipes = recipes.filter { it.name.contains(query, ignoreCase = true) }

            // Update the adapter with the filtered recipes
            adapter.updateList(filteredRecipes)
        }
    }
}
