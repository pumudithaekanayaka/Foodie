package sn0ww01f.project.foodie

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import sn0ww01f.project.foodie.databinding.ActivityRecipeDetailBinding
import android.util.Base64
import android.widget.TextView

class RecipeDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        val recipe: Recipe? = intent.getParcelableExtra("recipe")
        findViewById<TextView>(R.id.recipe_name).text = recipe?.name
        findViewById<TextView>(R.id.recipe_rating).text = recipe?.rating.toString()
    }
}

