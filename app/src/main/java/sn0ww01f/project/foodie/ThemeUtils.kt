package sn0ww01f.project.foodie

import android.content.Context
import android.content.res.TypedArray
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.color.DynamicColors

object ThemeUtils {

    private var applyMonetColors = false

    fun setApplyMonetColors(shouldApply: Boolean) {
        applyMonetColors = shouldApply
    }

    fun applyDynamicColors(context: Context) {
        if (context is AppCompatActivity) {
            if (applyMonetColors && DynamicColors.isDynamicColorAvailable()) {
                DynamicColors.applyToActivityIfAvailable(context)
            } else {
                val primaryColor = getDynamicPrimaryColor(context)
                applyColorToApp(context, primaryColor)
            }
        }
    }

    private fun applyColorToApp(context: Context, color: Int) {

        if (context is AppCompatActivity) {
            val rootView = context.findViewById<View>(android.R.id.content)
            rootView.setBackgroundColor(color)
        }
    }

    private fun getDynamicPrimaryColor(context: Context): Int {
        return getColorFromAttribute(context, android.R.attr.colorPrimary)
    }

    fun getDynamicAccentColor(context: Context): Int {
        return getColorFromAttribute(context, android.R.attr.windowBackground)
    }

    private fun getColorFromAttribute(context: Context, attributeId: Int): Int {
        val typedArray: TypedArray = context.theme.obtainStyledAttributes(intArrayOf(attributeId))
        val color = typedArray.getColor(0, ContextCompat.getColor(context, android.R.color.transparent))
        typedArray.recycle()
        return color
    }
}
