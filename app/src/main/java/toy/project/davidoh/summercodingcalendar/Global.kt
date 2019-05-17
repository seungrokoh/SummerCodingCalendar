package toy.project.davidoh.summercodingcalendar

import androidx.fragment.app.Fragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import toy.project.davidoh.summercodingcalendar.ui.calendar.monthly.MonthlyFragment
import toy.project.davidoh.summercodingcalendar.util.nowLocalDate

object Global {
    var cachedSelectedDate: CalendarDay? = null
        get() = field ?: CalendarDay.from(nowLocalDate())

    var cachedFragment: Fragment = MonthlyFragment.getInstance()
}