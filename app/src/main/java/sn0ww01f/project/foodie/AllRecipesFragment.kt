package sn0ww01f.project.foodie

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import sn0ww01f.project.foodie.databinding.FragmentAllRecipesBinding

class AllRecipesFragment : Fragment() {

    private lateinit var binding: FragmentAllRecipesBinding
    private lateinit var recipeDatabaseHelper: RecipeDatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllRecipesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipeDatabaseHelper = RecipeDatabaseHelper(requireContext())
        val recipes = recipeDatabaseHelper.getAllRecipes()

        binding.recyclerViewAllRecipes.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewAllRecipes.adapter = RecipesAdapter(recipes) { recipe ->
            // Handle recipe item click
            val intent = Intent(requireContext(), RecipeDetailActivity::class.java).apply {
                putExtra("recipe", recipe)
            }
            startActivity(intent)
        }
    }

    private fun decodeBase64(base64: String): Bitmap? {
        return try {
            val decodedString = Base64.decode(base64, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
