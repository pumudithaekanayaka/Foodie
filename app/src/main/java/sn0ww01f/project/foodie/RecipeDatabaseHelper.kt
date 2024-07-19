package sn0ww01f.project.foodie

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor

class RecipeDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "recipes.db"
        private const val TABLE_RECIPES = "recipes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_INGREDIENTS = "ingredients"
        private const val COLUMN_STEPS = "steps"
        private const val COLUMN_IMAGE_BASE64 = "image_base64"
        private const val COLUMN_RATING = "rating"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_RECIPES (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_NAME TEXT," +
                "$COLUMN_INGREDIENTS TEXT," +
                "$COLUMN_STEPS TEXT," +
                "$COLUMN_IMAGE_BASE64 TEXT," +
                "$COLUMN_RATING REAL)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_RECIPES")
        onCreate(db)
    }

    fun addRecipe(recipe: Recipe) {
        val values = ContentValues().apply {
            put(COLUMN_NAME, recipe.name)
            put(COLUMN_INGREDIENTS, recipe.ingredients.joinToString("\n"))
            put(COLUMN_STEPS, recipe.steps.joinToString("\n"))
            put(COLUMN_IMAGE_BASE64, recipe.imageBase64)
            put(COLUMN_RATING, recipe.rating)
        }

        val db = writableDatabase
        db.insert(TABLE_RECIPES, null, values)
        db.close()
    }

    fun getAllRecipes(): List<Recipe> {
        val recipes = mutableListOf<Recipe>()
        val db = readableDatabase
        val cursor = db.query(TABLE_RECIPES, null, null, null, null, null, null)

        with(cursor) {
            while (moveToNext()) {
                val name = getString(getColumnIndexOrThrow(COLUMN_NAME))
                val ingredients = getString(getColumnIndexOrThrow(COLUMN_INGREDIENTS)).split("\n")
                val steps = getString(getColumnIndexOrThrow(COLUMN_STEPS)).split("\n")
                val imageBase64 = getString(getColumnIndexOrThrow(COLUMN_IMAGE_BASE64))
                val rating = getDouble(getColumnIndexOrThrow(COLUMN_RATING))

                recipes.add(Recipe(name, imageBase64, ingredients, steps, rating))
            }
        }
        cursor.close()
        db.close()
        return recipes
    }
}
