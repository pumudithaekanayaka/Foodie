package sn0ww01f.project.foodie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.color.DynamicColors
import sn0ww01f.project.foodie.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding
    private lateinit var adapter: RecipeAdapter
    private lateinit var recipeRepository: RecipeRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ThemeUtils.applyDynamicColors(this)

        val accentColor = ThemeUtils.getDynamicAccentColor(this)
        binding.root.setBackgroundColor(accentColor)
        setStatusBarColor(accentColor)

        recipeRepository = RecipeRepository(this)
        adapter = RecipeAdapter()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.searchButton.setOnClickListener {
            val query = binding.searchText.text.toString()
            performSearch(query)
        }
    }

    private fun performSearch(query: String) {
        if (query.isNotBlank()) {
            val results = search(query)
            adapter.submitList(results)
        }
    }

    private fun search(query: String): List<Recipe> {
        return recipeRepository.searchRecipes(query)
    }

    private fun setStatusBarColor(color: Int) {
        window.statusBarColor = color
    }
}
