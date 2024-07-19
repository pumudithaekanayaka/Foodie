package sn0ww01f.project.foodie

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import sn0ww01f.project.foodie.databinding.ActivityAddRecipeBinding
import java.io.ByteArrayOutputStream

class AddRecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddRecipeBinding

    private lateinit var imageViewRecipe: ImageView
    private lateinit var editTextRecipeName: EditText
    private lateinit var editTextIngredients: EditText
    private lateinit var editTextSteps: EditText
    private lateinit var buttonAddRecipe: Button

    private val PICK_IMAGE_REQUEST = 1
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)

        imageViewRecipe = findViewById(R.id.imageViewRecipe)
        editTextRecipeName = findViewById(R.id.editTextRecipeName)
        editTextIngredients = findViewById(R.id.editTextIngredients)
        editTextSteps = findViewById(R.id.editTextSteps)
        buttonAddRecipe = findViewById(R.id.buttonAddRecipe)

        imageViewRecipe.setOnClickListener {
            openImagePicker()
        }

        buttonAddRecipe.setOnClickListener {
            saveRecipe()
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
            val inputStream = contentResolver.openInputStream(imageUri!!)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            imageViewRecipe.setImageBitmap(bitmap)
        }
    }

    private fun saveRecipe() {
        val recipeName = editTextRecipeName.text.toString()
        val ingredients = editTextIngredients.text.toString().split("\n")
        val steps = editTextSteps.text.toString().split("\n")

        val imageBase64 = imageUri?.let { uri ->
            val inputStream = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
            Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)
        }

        val recipe = Recipe(
            name = recipeName,
            imageBase64 = imageBase64,
            ingredients = ingredients,
            steps = steps,
            rating = 0.0
        )

        val dbHelper = RecipeDatabaseHelper(this)
        dbHelper.addRecipe(recipe)
    }
}
