package sn0ww01f.project.foodie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.color.DynamicColors
import sn0ww01f.project.foodie.databinding.ActivityRecipeDetailBinding

class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailBinding
    private lateinit var recipeRepository: RecipeRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ThemeUtils.applyDynamicColors(this)

        val accentColor = ThemeUtils.getDynamicAccentColor(this)
        binding.root.setBackgroundColor(accentColor)
        setStatusBarColor(accentColor)

        recipeRepository = RecipeRepository(this)
        val recipeId = intent.getIntExtra("RECIPE_ID", -1)
        val recipe = recipeRepository.getRecipeById(recipeId)

        if (recipe != null) {
            binding.tvRecipeName.text = recipe.name

            // Use Glide to load the image
            Glide.with(this)
                .load(recipe.image)
                .into(binding.tvRecipeImage)

            binding.tvRecipeIngredients.text = recipe.ingredients
            binding.tvRecipeSteps.text = recipe.steps
            binding.rbRecipeRating.rating = recipe.rating
        }
    }

    private fun setStatusBarColor(color: Int) {
        window.statusBarColor = color
    }
}
