package sn0ww01f.project.foodie

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import sn0ww01f.project.foodie.databinding.ActivityNotificationsBinding

class NotificationsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNavigationView()
    }

    private fun setupBottomNavigationView() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_recipes -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.nav_notifications -> {
                    // Already on notifications
                    true
                }
                R.id.nav_search -> {
                    // Handle search action
                    true
                }
                else -> false
            }
        }
    }
}
