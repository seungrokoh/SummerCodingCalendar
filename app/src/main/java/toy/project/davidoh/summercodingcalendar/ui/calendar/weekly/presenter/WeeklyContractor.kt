package toy.project.davidoh.summercodingcalendar.ui.calendar.weekly.presenter

import com.prolificinteractive.materialcalendarview.CalendarDay
import toy.project.davidoh.summercodingcalendar.data.Schedule

interface WeeklyContractor {
    interface View {
        var isActive: Boolean

        fun showSuccessMessage(message: String)
        fun showInfoMessage(message: String)
        fun showErrorMesage(message: String)

        fun showScheduleRecyclerView()
        fun showScheduleEmptyView()

        fun showDecorateOnCalendar(schedules: MutableList<CalendarDay>)
        fun deleteDecorateOnCalendar(date: CalendarDay)

        fun showDeleteDialog(position: Int)

        fun destroy()
    }

    interface Presenter {
        fun drawEventsOnCalendar()

        fun getSchedulesOnDay(date: CalendarDay)

        fun deleteSchedule(position: Int)
    }
}