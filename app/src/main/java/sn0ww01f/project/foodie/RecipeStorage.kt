package sn0ww01f.project.foodie

object RecipeStorage {
    private val recipes = mutableListOf<String>()
    private val notifications = mutableListOf<String>()

    fun addRecipe(recipe: String) {
        recipes.add(recipe)
        notifications.add("New recipe added: $recipe")
    }

    fun getRecipes(): List<String> = recipes

    fun getNotifications(): List<String> = notifications

    fun clearNotifications() {
        notifications.clear()
    }
}

