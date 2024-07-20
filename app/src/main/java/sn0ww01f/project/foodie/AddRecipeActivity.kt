package sn0ww01f.project.foodie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import sn0ww01f.project.foodie.databinding.ActivityAddRecipeBinding

class AddRecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddRecipeBinding
    private lateinit var recipeDatabaseHelper: RecipeDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recipeDatabaseHelper = RecipeDatabaseHelper(this)

        binding.buttonAddRecipe.setOnClickListener {
            // Collect recipe data from inputs
            val name = binding.editTextRecipeName.text.toString()
            val categoryId = 1  // Assuming default category, handle category selection properly
            val ingredients = binding.editTextIngredients.text.toString().split("\n")
            val steps = binding.editTextSteps.text.toString().split("\n")
            val imageBase64 = ""  // Handle image input
            val rating = 0.0  // Default rating, handle user input

            val recipe = Recipe(
                name = name,
                categoryId = categoryId,
                ingredients = ingredients,
                steps = steps,
                imageBase64 = imageBase64,
                rating = rating
            )

            // Add recipe to database
            recipeDatabaseHelper.addRecipe(recipe)
            finish()
        }
    }
}
