package toy.project.davidoh.summercodingcalendar.ui.calendar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_calendar.*
import toy.project.davidoh.summercodingcalendar.R
import toy.project.davidoh.summercodingcalendar.listener.FragmentChangeCallback
import toy.project.davidoh.summercodingcalendar.ui.add.AddScheduleDialog
import toy.project.davidoh.summercodingcalendar.ui.calendar.monthly.MonthlyFragment
import toy.project.davidoh.summercodingcalendar.util.replaceFragment

class CalendarActivity : AppCompatActivity(), FragmentChangeCallback {
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
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

        replaceFragment(R.id.fl_container, MonthlyFragment.newInstance())
        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        fab_add_schedule.setOnClickListener {
            AddScheduleDialog.getInstance()
                    .show(supportFragmentManager, "AddScheduleDialog")
        }
    }

    override fun onReplaceFragment(fragment: Fragment, tag: String?) {
        replaceFragment(R.id.fl_container, fragment, tag)
    }
}
