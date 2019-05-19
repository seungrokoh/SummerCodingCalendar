package toy.project.davidoh.summercodingcalendar

import com.prolificinteractive.materialcalendarview.CalendarDay
import toy.project.davidoh.summercodingcalendar.util.nowLocalDate

object Global {
    var cachedSelectedDate: CalendarDay? = null
        get() = field ?: CalendarDay.from(nowLocalDate())

    var isSplash = false

    const val PREF_MONTHLY = "PREF_MONTHLY"
    const val PREF_WEEKLY = "PREF_WEEKLY"
    const val PREF_DAILY = "PREF_DAILY"
    const val PREF_NAME = "toy.project.davidoh.summer.calendar"
    const val PREF_DEFAULT_VALUE = PREF_MONTHLY
    const val PREF_KEY_LAST_FRAGMENT = "LAST_FRAGMENT"

    const val DATABASE_NAME = "Schedule.db"
}