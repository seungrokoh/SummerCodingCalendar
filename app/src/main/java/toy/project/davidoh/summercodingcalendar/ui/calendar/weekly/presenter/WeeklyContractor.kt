package toy.project.davidoh.summercodingcalendar.ui.calendar.weekly.presenter

import com.prolificinteractive.materialcalendarview.CalendarDay

interface WeeklyContractor {
    interface View {
        var isActive: Boolean

        fun showSuccessMessage(message: String)
        fun showInfoMessage(message: String)
        fun showErrorMesage(message: String)

        fun showScheduleRecyclerView()
        fun showScheduleEmptyView()

        fun showDecorateOnCalendar(schedules: MutableList<CalendarDay>)

        fun destroy()
    }

    interface Presenter {
        fun drawEventsOnCalendar()

        fun getSchedulesOnDay(date: CalendarDay)
    }
}