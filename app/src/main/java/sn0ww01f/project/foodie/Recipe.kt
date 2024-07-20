package sn0ww01f.project.foodie

import android.os.Parcel
import android.os.Parcelable

data class Recipe(
    val id: Int = 0,
    val name: String,
    val categoryId: Int,
    val ingredients: List<String> = emptyList(),
    val steps: List<String> = emptyList(),
    val imageBase64: String? = null,
    val rating: Double = 0.0
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.createStringArrayList() ?: emptyList(),
        parcel.createStringArrayList() ?: emptyList(),
        parcel.readString(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(categoryId)
        parcel.writeStringList(ingredients)
        parcel.writeStringList(steps)
        parcel.writeString(imageBase64)
        parcel.writeDouble(rating)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Recipe> {
        override fun createFromParcel(parcel: Parcel): Recipe {
            return Recipe(parcel)
        }

        override fun newArray(size: Int): Array<Recipe?> {
            return arrayOfNulls(size)
        }
    }
}

data class Review(
    val text: String,
    val rating: Double
)
