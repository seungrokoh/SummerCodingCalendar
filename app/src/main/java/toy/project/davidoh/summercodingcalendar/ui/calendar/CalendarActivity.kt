package toy.project.davidoh.summercodingcalendar.ui.calendar

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_calendar.*
import toy.project.davidoh.summercodingcalendar.Global.PREF_DAILY
import toy.project.davidoh.summercodingcalendar.Global.PREF_KEY_LAST_FRAGMENT
import toy.project.davidoh.summercodingcalendar.Global.PREF_MONTHLY
import toy.project.davidoh.summercodingcalendar.Global.PREF_WEEKLY
import toy.project.davidoh.summercodingcalendar.R
import toy.project.davidoh.summercodingcalendar.ui.add.AddScheduleActivity
import toy.project.davidoh.summercodingcalendar.ui.calendar.daily.DailyFragment
import toy.project.davidoh.summercodingcalendar.ui.calendar.monthly.MonthlyFragment
import toy.project.davidoh.summercodingcalendar.ui.calendar.weekly.WeeklyFragment
import toy.project.davidoh.summercodingcalendar.util.SharedPreferenceUtil
import toy.project.davidoh.summercodingcalendar.util.replaceFragment

class CalendarActivity : AppCompatActivity() {
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_monthly -> {
                replaceFragment(R.id.fl_container, MonthlyFragment.getInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_weekly -> {
                replaceFragment(R.id.fl_container, WeeklyFragment.getInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_daily -> {
                replaceFragment(R.id.fl_container, DailyFragment.getInstance())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        showLatestFragment()
        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        bottom_navigation.setCachedView()

        fab_add_schedule.setOnClickListener {
            startActivity(Intent(this, AddScheduleActivity::class.java))
        }
    }

    private fun showLatestFragment() {
        val latestFragment = SharedPreferenceUtil(applicationContext).get(PREF_KEY_LAST_FRAGMENT)
        if (latestFragment == PREF_MONTHLY) {
            replaceFragment(R.id.fl_container, MonthlyFragment.getInstance())
        }
        if (latestFragment == PREF_WEEKLY) {
            replaceFragment(R.id.fl_container, WeeklyFragment.getInstance())
        }
        if (latestFragment == PREF_DAILY) {
            replaceFragment(R.id.fl_container, DailyFragment.getInstance())
        }
    }


    private fun BottomNavigationView.setCachedView() {
        val latestFragment = SharedPreferenceUtil(applicationContext).get(PREF_KEY_LAST_FRAGMENT)

        if (latestFragment == PREF_MONTHLY) {
            selectedItemId = R.id.navigation_monthly
        }
        if (latestFragment == PREF_WEEKLY) {
            selectedItemId = R.id.navigation_weekly
        }
        if (latestFragment == PREF_DAILY) {
            selectedItemId = R.id.navigation_daily

        }
    }
}
