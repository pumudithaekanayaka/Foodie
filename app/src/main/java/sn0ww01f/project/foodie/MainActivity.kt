package sn0ww01f.project.foodie

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import sn0ww01f.project.foodie.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Foodie"

        val viewPager = binding.viewPager
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = 1

            override fun createFragment(position: Int): Fragment {
                return CategoryFragment()
            }
        }

        val tabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Categories"
                else -> null
            }
        }.attach()

        binding.fab.setOnClickListener {
            startActivity(Intent(this, AddRecipeActivity::class.java))
        }

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_search -> {
                    startActivity(Intent(this, SearchActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}
