package sn0ww01f.project.foodie

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor

class RecipeDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 2  // Increment if you've made schema changes
        private const val DATABASE_NAME = "recipes.db"
        private const val TABLE_RECIPES = "recipes"
        private const val TABLE_CATEGORIES = "categories"
        private const val TABLE_REVIEWS = "reviews"

        // Recipes Table
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_CATEGORY_ID = "category_id"
        private const val COLUMN_INGREDIENTS = "ingredients"
        private const val COLUMN_STEPS = "steps"
        private const val COLUMN_IMAGE_BASE64 = "image_base64"
        private const val COLUMN_RATING = "rating"

        // Categories Table
        private const val COLUMN_CATEGORY_NAME = "category_name"

        // Reviews Table
        private const val COLUMN_RECIPE_ID = "recipe_id"
        private const val COLUMN_REVIEW_TEXT = "review_text"
        private const val COLUMN_REVIEW_RATING = "review_rating"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createCategoriesTable = ("CREATE TABLE $TABLE_CATEGORIES (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_CATEGORY_NAME TEXT)")
        db.execSQL(createCategoriesTable)

        val createRecipesTable = ("CREATE TABLE $TABLE_RECIPES (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_NAME TEXT," +
                "$COLUMN_CATEGORY_ID INTEGER," +
                "$COLUMN_INGREDIENTS TEXT," +
                "$COLUMN_STEPS TEXT," +
                "$COLUMN_IMAGE_BASE64 TEXT," +
                "$COLUMN_RATING REAL," +
                "FOREIGN KEY($COLUMN_CATEGORY_ID) REFERENCES $TABLE_CATEGORIES($COLUMN_ID))")
        db.execSQL(createRecipesTable)

        val createReviewsTable = ("CREATE TABLE $TABLE_REVIEWS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_RECIPE_ID INTEGER," +
                "$COLUMN_REVIEW_TEXT TEXT," +
                "$COLUMN_REVIEW_RATING REAL," +
                "FOREIGN KEY($COLUMN_RECIPE_ID) REFERENCES $TABLE_RECIPES($COLUMN_ID))")
        db.execSQL(createReviewsTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_REVIEWS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_RECIPES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CATEGORIES")
        onCreate(db)
    }

    fun addRecipe(recipe: Recipe) {
        val values = ContentValues().apply {
            put(COLUMN_NAME, recipe.name)
            put(COLUMN_CATEGORY_ID, recipe.categoryId)
            put(COLUMN_INGREDIENTS, recipe.ingredients.joinToString("\n"))
            put(COLUMN_STEPS, recipe.steps.joinToString("\n"))
            put(COLUMN_IMAGE_BASE64, recipe.imageBase64)
            put(COLUMN_RATING, recipe.rating)
        }

        val db = writableDatabase
        try {
            db.insert(TABLE_RECIPES, null, values)
        } catch (e: Exception) {
            e.printStackTrace()  // Log the error
        } finally {
            db.close()
        }
    }

    fun getCategories(): List<String> {
        val categories = mutableListOf<String>()
        val db = readableDatabase
        val cursor = db.query(TABLE_CATEGORIES, arrayOf(COLUMN_CATEGORY_NAME), null, null, null, null, null)

        with(cursor) {
            while (moveToNext()) {
                categories.add(getString(getColumnIndexOrThrow(COLUMN_CATEGORY_NAME)))
            }
        }
        cursor.close()
        db.close()
        return categories
    }

    fun getRecipesInCategory(categoryId: Int): List<Recipe> {
        val recipes = mutableListOf<Recipe>()
        val db = readableDatabase
        val cursor = db.query(
            TABLE_RECIPES,
            null,
            "$COLUMN_CATEGORY_ID = ?",
            arrayOf(categoryId.toString()),
            null,
            null,
            null
        )

        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(COLUMN_ID))
                val name = getString(getColumnIndexOrThrow(COLUMN_NAME))
                val ingredients = getString(getColumnIndexOrThrow(COLUMN_INGREDIENTS)).split("\n")
                val steps = getString(getColumnIndexOrThrow(COLUMN_STEPS)).split("\n")
                val imageBase64 = getString(getColumnIndexOrThrow(COLUMN_IMAGE_BASE64))
                val rating = getDouble(getColumnIndexOrThrow(COLUMN_RATING))

                recipes.add(Recipe(id, name, categoryId, ingredients, steps, imageBase64, rating))
            }
        }
        cursor.close()
        db.close()
        return recipes
    }

    fun getAllRecipes(): List<Recipe> {
        val recipes = mutableListOf<Recipe>()
        val db = readableDatabase
        val cursor = db.query(
            TABLE_RECIPES,
            null,
            null,
            null,
            null,
            null,
            null
        )

        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(COLUMN_ID))
                val name = getString(getColumnIndexOrThrow(COLUMN_NAME))
                val categoryId = getInt(getColumnIndexOrThrow(COLUMN_CATEGORY_ID))
                val ingredients = getString(getColumnIndexOrThrow(COLUMN_INGREDIENTS)).split("\n")
                val steps = getString(getColumnIndexOrThrow(COLUMN_STEPS)).split("\n")
                val imageBase64 = getString(getColumnIndexOrThrow(COLUMN_IMAGE_BASE64))
                val rating = getDouble(getColumnIndexOrThrow(COLUMN_RATING))

                recipes.add(Recipe(id, name, categoryId, ingredients, steps, imageBase64, rating))
            }
        }
        cursor.close()
        db.close()
        return recipes
    }

}

