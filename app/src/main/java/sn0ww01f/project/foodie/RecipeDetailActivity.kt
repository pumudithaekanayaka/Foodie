package sn0ww01f.project.foodie

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import sn0ww01f.project.foodie.databinding.ActivityRecipeDetailBinding
import android.util.Base64

class RecipeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recipe = intent.getParcelableExtra<Recipe>("RECIPE")

        recipe?.let {
            binding.recipeName.text = it.name

            // Decode image from Base64 and set it to ImageView
            if (it.imageBase64 != null) {
                val decodedString = Base64.decode(it.imageBase64, Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                binding.recipeImage.setImageBitmap(bitmap)
            } else {
                binding.recipeImage.setImageResource(R.drawable.ic_placeholder)
            }

            binding.recipeIngredients.text = it.ingredients.joinToString("\n")
            binding.recipeSteps.text = it.steps.joinToString("\n")
        }
    }
}
