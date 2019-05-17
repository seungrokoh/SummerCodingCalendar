package toy.project.davidoh.summercodingcalendar.ui.calendar.daily.presenter

import com.prolificinteractive.materialcalendarview.CalendarDay

interface DailyContractor {
    interface View {
        fun showCurrentDate()
    }

    interface Presenter {
        fun getSchedulesOnDay()

        fun movePreDay()
        fun moveNextDay()
    }
}