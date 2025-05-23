import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

open class BaseActivity : AppCompatActivity() {

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) { // S = API 31 = Android 12
            enableEdgeToEdge()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            // Obtiene el primer hijo visible del decorView (típicamente tu layout raíz)
            val rootView = (findViewById<View>(android.R.id.content) as? ViewGroup)?.getChildAt(0)
            rootView?.let { applySystemBarInsets(it) }
        }
    }

    private fun applySystemBarInsets(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) { // Android 15
            ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
                val sys = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(sys.left, sys.top, sys.right, sys.bottom)
                insets
            }
            view.requestApplyInsets()
        }
    }
}
