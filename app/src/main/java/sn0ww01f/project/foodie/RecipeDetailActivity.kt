package sn0ww01f.project.foodie

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import sn0ww01f.project.foodie.databinding.ActivityRecipeDetailBinding
import android.util.Base64
import android.widget.ImageView
import android.widget.TextView

class RecipeDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        val recipe: Recipe? = intent.getParcelableExtra("recipe")
        val imageView = findViewById<ImageView>(R.id.recipe_image)
        val nameTextView = findViewById<TextView>(R.id.recipe_name)
        val ratingTextView = findViewById<TextView>(R.id.recipe_rating)

        nameTextView.text = recipe?.name
        ratingTextView.text = recipe?.rating.toString()

        // Display image
        recipe?.imageBase64?.let {
            val imageBytes = Base64.decode(it, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            imageView.setImageBitmap(bitmap)
        }
    }
}


