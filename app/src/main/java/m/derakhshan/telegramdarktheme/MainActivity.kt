package m.derakhshan.telegramdarktheme

import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewAnimationUtils
import androidx.core.animation.doOnEnd
import androidx.core.view.LayoutInflaterCompat
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.hypot

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        LayoutInflaterCompat.setFactory2(
            LayoutInflater.from(this),
            MyLayoutInflater(delegate)
        )

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            val newTheme = when (ThemeManager.theme) {
                ThemeManager.Theme.DARK -> ThemeManager.Theme.LIGHT
                ThemeManager.Theme.LIGHT -> ThemeManager.Theme.DARK
            }
            setTheme(newTheme, animate = true)
        }
    }

    override fun onPostResume() {
        super.onPostResume()
        setTheme(Settings(this).theme,false)
    }

    override fun onStop() {
        super.onStop()
        Settings(this).theme = ThemeManager.theme
    }

    private fun setTheme(theme: ThemeManager.Theme, animate: Boolean = true) {

        if (!animate) {
            ThemeManager.theme = theme
            return
        }

        if (imageView.isVisible) {
            return
        }

        val w = container.measuredWidth
        val h = container.measuredHeight

        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        container.draw(canvas)

        imageView.setImageBitmap(bitmap)
        imageView.isVisible = true

        val finalRadius = hypot(w.toFloat(), h.toFloat())

        ThemeManager.theme = theme

        val anim = ViewAnimationUtils.createCircularReveal(container, w / 2, h / 2, 0f, finalRadius)
        anim.duration = 400L
        anim.doOnEnd {
            imageView.setImageDrawable(null)
            imageView.isVisible = false
        }
        anim.start()
    }
}