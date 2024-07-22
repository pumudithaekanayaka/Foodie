package sn0ww01f.project.foodie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.color.DynamicColors
import sn0ww01f.project.foodie.databinding.ActivityHelpBinding

class HelpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHelpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ThemeUtils.applyDynamicColors(this)

        binding = ActivityHelpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvHelpContent.text = getString(R.string.help_content)

        val accentColor = ThemeUtils.getDynamicAccentColor(this)
        binding.root.setBackgroundColor(accentColor)
        setStatusBarColor(accentColor)
    }

    private fun setStatusBarColor(color: Int) {
        window.statusBarColor = color
    }
}
