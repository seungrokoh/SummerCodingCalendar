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

    private val monthlyFragment: MonthlyFragment by lazy {
        MonthlyFragment.getInstance()
    }
    private val weeklyFragment: WeeklyFragment by lazy {
        WeeklyFragment.getInstance()
    }
    private val dailyFragment: DailyFragment by lazy {
        DailyFragment.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        fab_add_schedule.setOnClickListener {
            fab_add_schedule.isEnabled = false
            startActivityForResult(Intent(this, AddScheduleActivity::class.java), 1001)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        fab_add_schedule.isEnabled = true
    }

    override fun onResume() {
        super.onResume()
        setCachedView()
    }

    private fun setCachedView() {
        val latestFragment = SharedPreferenceUtil(applicationContext).get(PREF_KEY_LAST_FRAGMENT)

        if (latestFragment == PREF_MONTHLY) {
            bottom_navigation.selectedItemId = R.id.navigation_monthly
        }
        if (latestFragment == PREF_WEEKLY) {
            bottom_navigation.selectedItemId = R.id.navigation_weekly
        }
        if (latestFragment == PREF_DAILY) {
            bottom_navigation.selectedItemId = R.id.navigation_daily
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_monthly -> {
                replaceFragment(R.id.fl_container, monthlyFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_weekly -> {
                replaceFragment(R.id.fl_container, weeklyFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_daily -> {
                replaceFragment(R.id.fl_container, dailyFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onDestroy() {
        monthlyFragment.destroy()
        weeklyFragment.destroy()
        dailyFragment.destroy()
        super.onDestroy()
    }
}
