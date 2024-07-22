package sn0ww01f.project.foodie

import android.content.ContentValues
import android.content.Context
import android.database.Cursor

class RecipeRepository(context: Context) {

    private val dbHelper = RecipeDatabaseHelper(context)

    fun addRecipe(recipe: Recipe): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(RecipeDatabaseHelper.COLUMN_NAME, recipe.name)
            put(RecipeDatabaseHelper.COLUMN_IMAGE, recipe.image)
            put(RecipeDatabaseHelper.COLUMN_INGREDIENTS, recipe.ingredients)
            put(RecipeDatabaseHelper.COLUMN_STEPS, recipe.steps)
            put(RecipeDatabaseHelper.COLUMN_RATING, recipe.rating)
        }
        return db.insert(RecipeDatabaseHelper.TABLE_RECIPES, null, values)
    }

    fun getAllRecipes(): List<Recipe> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(
            RecipeDatabaseHelper.TABLE_RECIPES,
            null, null, null, null, null, null
        )
        return cursorToList(cursor)
    }

    private fun cursorToList(cursor: Cursor): List<Recipe> {
        val recipes = mutableListOf<Recipe>()
        with(cursor) {
            while (moveToNext()) {
                val recipe = Recipe(
                    getInt(getColumnIndexOrThrow(RecipeDatabaseHelper.COLUMN_ID)),
                    getString(getColumnIndexOrThrow(RecipeDatabaseHelper.COLUMN_NAME)),
                    getString(getColumnIndexOrThrow(RecipeDatabaseHelper.COLUMN_IMAGE)),
                    getString(getColumnIndexOrThrow(RecipeDatabaseHelper.COLUMN_INGREDIENTS)),
                    getString(getColumnIndexOrThrow(RecipeDatabaseHelper.COLUMN_STEPS)),
                    getFloat(getColumnIndexOrThrow(RecipeDatabaseHelper.COLUMN_RATING))
                )
                recipes.add(recipe)
            }
        }
        cursor.close()
        return recipes
    }

    fun searchRecipes(query: String): List<Recipe> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(
            RecipeDatabaseHelper.TABLE_RECIPES,
            null,
            "${RecipeDatabaseHelper.COLUMN_NAME} LIKE ?",
            arrayOf("%$query%"),
            null, null, null
        )
        return cursorToList(cursor)
    }

    fun getRecipeById(id: Int): Recipe? {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(
            RecipeDatabaseHelper.TABLE_RECIPES,
            null,
            "${RecipeDatabaseHelper.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        var recipe: Recipe? = null
        with(cursor) {
            if (moveToFirst()) {
                recipe = Recipe(
                    getInt(getColumnIndexOrThrow(RecipeDatabaseHelper.COLUMN_ID)),
                    getString(getColumnIndexOrThrow(RecipeDatabaseHelper.COLUMN_NAME)),
                    getString(getColumnIndexOrThrow(RecipeDatabaseHelper.COLUMN_IMAGE)),
                    getString(getColumnIndexOrThrow(RecipeDatabaseHelper.COLUMN_INGREDIENTS)),
                    getString(getColumnIndexOrThrow(RecipeDatabaseHelper.COLUMN_STEPS)),
                    getFloat(getColumnIndexOrThrow(RecipeDatabaseHelper.COLUMN_RATING))
                )
            }
        }
        cursor.close()
        return recipe
    }

}
