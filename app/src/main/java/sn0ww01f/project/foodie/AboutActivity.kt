package sn0ww01f.project.foodie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.color.DynamicColors
import sn0ww01f.project.foodie.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ThemeUtils.applyDynamicColors(this)

        val accentColor = ThemeUtils.getDynamicAccentColor(this)
        binding.root.setBackgroundColor(accentColor)
        setStatusBarColor(accentColor)

        binding.tvAboutContent.text = getString(R.string.about_content)
    }

    private fun setStatusBarColor(color: Int) {
        window.statusBarColor = color
    }

}
