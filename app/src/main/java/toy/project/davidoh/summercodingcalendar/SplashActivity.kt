package toy.project.davidoh.summercodingcalendar

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import toy.project.davidoh.summercodingcalendar.Global.isSplash
import toy.project.davidoh.summercodingcalendar.ui.calendar.CalendarActivity

class SplashActivity : AppCompatActivity() {

    private var delayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 1300

    private val runnable = Runnable {
        if (!isFinishing) {
            isSplash = !isSplash
            navigateToCalendar()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (isSplash) {
            navigateToCalendar()
        }
        delayHandler = Handler()
        delayHandler!!.postDelayed(runnable, SPLASH_DELAY)

    }

    private fun navigateToCalendar() {
        startActivity(Intent(this@SplashActivity, CalendarActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        delayHandler?.let {
            delayHandler!!.removeCallbacks(runnable)
        }
        super.onDestroy()
    }
}
