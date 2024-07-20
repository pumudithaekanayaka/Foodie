package sn0ww01f.project.foodie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import sn0ww01f.project.foodie.databinding.FragmentRecipesBinding

class RecipesFragment : Fragment() {

    private lateinit var binding: FragmentRecipesBinding
    private lateinit var recipeDatabaseHelper: RecipeDatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecipesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipeDatabaseHelper = RecipeDatabaseHelper(requireContext())
        val categoryId = arguments?.getInt("category_id") ?: return
        val recipes = recipeDatabaseHelper.getRecipesInCategory(categoryId)

        binding.recipesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recipesRecyclerView.adapter = RecipesAdapter(recipes) { recipe ->
            // Open RecipeDetailFragment with selected recipe
            val fragment = RecipeDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("recipe", recipe)
                }
            }
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}
