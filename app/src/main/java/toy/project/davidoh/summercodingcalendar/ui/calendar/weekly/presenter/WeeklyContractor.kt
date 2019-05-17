package toy.project.davidoh.summercodingcalendar.ui.calendar.weekly.presenter

import com.prolificinteractive.materialcalendarview.CalendarDay

interface WeeklyContractor {
    interface View {
        fun showSuccessMessage(message: String)
        fun showInfoMessage(message: String)
        fun showErrorMesage(message: String)

        fun showDecorateOnCalendar(schedules: MutableList<CalendarDay>)
    }

    interface Presenter {
        fun loadSchedulesAllDay()

        fun getSchedulesOnDay(date: CalendarDay)
    }
}