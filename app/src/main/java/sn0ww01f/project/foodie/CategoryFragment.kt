package sn0ww01f.project.foodie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import sn0ww01f.project.foodie.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
    private lateinit var recipeDatabaseHelper: RecipeDatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipeDatabaseHelper = RecipeDatabaseHelper(requireContext())
        val categories = recipeDatabaseHelper.getCategories()

        binding.categoryRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.categoryRecyclerView.adapter = CategoryAdapter(categories) { category ->
            // Open RecipesFragment with selected category
            val fragment = RecipesFragment().apply {
                arguments = Bundle().apply {
                    putInt("category_id", categories.indexOf(category) + 1) // Assume category IDs are 1-based
                }
            }
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}
