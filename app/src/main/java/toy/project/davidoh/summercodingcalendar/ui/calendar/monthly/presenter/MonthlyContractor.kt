package toy.project.davidoh.summercodingcalendar.ui.calendar.monthly.presenter

import com.prolificinteractive.materialcalendarview.CalendarDay

interface MonthlyContractor {
    interface View {
        var isActive: Boolean

        fun showSuccessMessage(message: String)
        fun showInfoMessage(message: String)
        fun showErrorMessage(message: String)

        fun showDecorateOnCalendar(schedules: MutableList<CalendarDay>)
    }

    interface Presenter {
        fun loadSchedulesAllDay()
    }
}