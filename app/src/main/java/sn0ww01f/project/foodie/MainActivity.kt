package sn0ww01f.project.foodie

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.color.DynamicColors
import com.google.android.material.navigation.NavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import sn0ww01f.project.foodie.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recipeRepository: RecipeRepository
    private lateinit var adapter: RecipeAdapter
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DynamicColors.applyToActivitiesIfAvailable(application)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recipeRepository = RecipeRepository(this)
        adapter = RecipeAdapter()

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.fabOpenMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        binding.fabAddRecipe.setOnClickListener {
            startActivity(Intent(this, AddRecipeActivity::class.java))
        }

        binding.fabSearchRecipe.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_add_recipe -> {
                    startActivity(Intent(this, AddRecipeActivity::class.java))
                }
                R.id.nav_search -> {
                    startActivity(Intent(this, SearchActivity::class.java))
                }
                R.id.nav_about -> {
                    startActivity(Intent(this, AboutActivity::class.java))
                }
                R.id.nav_help -> {
                    startActivity(Intent(this, HelpActivity::class.java))
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        onResume()
    }

    override fun onResume() {
        super.onResume()
        val recipes = recipeRepository.getAllRecipes()
        adapter.submitList(recipes)
    }
}
