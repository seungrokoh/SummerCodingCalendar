package toy.project.davidoh.summercodingcalendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import toy.project.davidoh.summercodingcalendar.ui.calendar.CalendarActivity

class SplashActivity : AppCompatActivity() {

    private var delayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 1300

    internal val runnable = Runnable {
        if (!isFinishing) {
            startActivity(Intent(this@SplashActivity, CalendarActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        delayHandler = Handler()
        delayHandler!!.postDelayed(runnable, SPLASH_DELAY)

    }

    override fun onDestroy() {
        delayHandler?.let {
            delayHandler!!.removeCallbacks(runnable)
        }
        super.onDestroy()
    }
}
