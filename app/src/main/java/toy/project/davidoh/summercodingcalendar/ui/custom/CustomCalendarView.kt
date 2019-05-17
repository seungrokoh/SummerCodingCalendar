package toy.project.davidoh.summercodingcalendar.ui.custom

import android.content.Context
import android.util.AttributeSet
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import toy.project.davidoh.summercodingcalendar.Global.cachedSelectedDate
import toy.project.davidoh.summercodingcalendar.R
import toy.project.davidoh.summercodingcalendar.util.decorator.TodayDecorator
import toy.project.davidoh.summercodingcalendar.util.nowLocalDate

class CustomCalendarView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null) :
        MaterialCalendarView(context, attrs) {

    fun setViewInit(mode: CalendarMode) : CustomCalendarView {
        state().edit()
            .setMinimumDate(CalendarDay.from(2018, 10, 1))
            .setMaximumDate(CalendarDay.from(2030, 10, 1))
            .setCalendarDisplayMode(mode)
            .commit()

        currentDate = cachedSelectedDate
        selectedDate = cachedSelectedDate
        selectionColor = resources.getColor(R.color.colorAccent)
        setTitleFormatter { calendarDay -> "${calendarDay.year}년 ${calendarDay.month}월" }
        addDecorator(TodayDecorator(CalendarDay.from(nowLocalDate())))
        return this
    }
}