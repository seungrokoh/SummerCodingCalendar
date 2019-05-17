package toy.project.davidoh.summercodingcalendar.ui.calendar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_calendar.*
import toy.project.davidoh.summercodingcalendar.Global.cachedFragment
import toy.project.davidoh.summercodingcalendar.R
import toy.project.davidoh.summercodingcalendar.ui.add.AddScheduleDialog
import toy.project.davidoh.summercodingcalendar.ui.calendar.monthly.MonthlyFragment
import toy.project.davidoh.summercodingcalendar.ui.calendar.weekly.WeeklyFragment
import toy.project.davidoh.summercodingcalendar.util.replaceFragment

class CalendarActivity : AppCompatActivity() {
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                replaceFragment(R.id.fl_container, MonthlyFragment.getInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                replaceFragment(R.id.fl_container, WeeklyFragment.getInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        replaceFragment(R.id.fl_container, cachedFragment)

        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        bottom_navigation.setCachedView()

        fab_add_schedule.setOnClickListener {
            AddScheduleDialog.getInstance()
                    .show(supportFragmentManager, "AddScheduleDialog")
        }
    }

    private fun BottomNavigationView.setCachedView() {
        when(cachedFragment) {
            is MonthlyFragment -> {
                selectedItemId = R.id.home
            }
            is WeeklyFragment -> {
                selectedItemId = R.id.navigation_dashboard
            }
        }
    }
}
