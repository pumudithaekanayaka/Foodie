package sn0ww01f.project.foodie

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.material.color.DynamicColors
import sn0ww01f.project.foodie.databinding.ActivityAddRecipeBinding

class AddRecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddRecipeBinding
    private lateinit var recipeRepository: RecipeRepository

    companion object {
        private const val NOTIFICATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ThemeUtils.applyDynamicColors(this)

        val accentColor = ThemeUtils.getDynamicAccentColor(this)
        binding.root.setBackgroundColor(accentColor)
        setStatusBarColor(accentColor)

        recipeRepository = RecipeRepository(this)

        binding.btnSaveRecipe.setOnClickListener {
            val name = binding.etRecipeName.text.toString()
            val image = binding.etRecipeImage.text.toString()
            val ingredients = binding.etRecipeIngredients.text.toString()
            val steps = binding.etRecipeSteps.text.toString()
            val rating = binding.rbRecipeRating.rating

            if (name.isBlank() || image.isBlank() || ingredients.isBlank() || steps.isBlank()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val recipe = Recipe(
                id = 0,
                name = name,
                image = image,
                ingredients = ingredients,
                steps = steps,
                rating = rating
            )

            recipeRepository.addRecipe(recipe)

            Toast.makeText(this, "Recipe added", Toast.LENGTH_SHORT).show()

            // Check for notification permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), NOTIFICATION_PERMISSION_REQUEST_CODE)
                } else {
                    showNotification(name)
                }
            } else {
                showNotification(name)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showNotification(binding.etRecipeName.text.toString())
            } else {
                Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showNotification(recipeName: String) {
        val channelId = "new_recipe_channel"
        val channelName = "New Recipe Notifications"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = "Notifications for new recipes"
            }
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle("New Recipe Added")
            .setContentText("Recipe '$recipeName' has been added.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notification)
    }

    private fun setStatusBarColor(color: Int) {
        window.statusBarColor = color
    }
}
