package sn0ww01f.project.foodie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import sn0ww01f.project.foodie.databinding.FragmentRecipeDetailBinding

class RecipeDetailFragment : Fragment() {

    private lateinit var binding: FragmentRecipeDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recipe: Recipe? = arguments?.getParcelable("recipe")
        recipe?.let {
            binding.textViewRecipeName.text = it.name
            binding.textViewIngredients.text = it.ingredients.joinToString("\n")
            binding.textViewSteps.text = it.steps.joinToString("\n")
            // Load image using imageBase64 if needed
            binding.ratingBar.rating = it.rating.toFloat()
            // Load user reviews
            // For simplicity, we assume reviews are empty in this implementation
        }
    }
}
