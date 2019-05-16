package toy.project.davidoh.summercodingcalendar.ui.calendar.monthly.presenter

import com.prolificinteractive.materialcalendarview.CalendarDay
import toy.project.davidoh.summercodingcalendar.data.Schedule
import java.util.ArrayList

interface MonthlyContractor {
    interface View {
        fun showProgress()
        fun hideProgress()

        fun showSuccessMessage(message: String)
        fun showInfoMessage(message: String)
        fun showErrorMessage(message: String)

        fun navigateToWeekly()
        fun navigateToDaily()
        fun showDecorateOnCalendar(schedules: MutableList<CalendarDay>)
    }

    interface Presenter {
        fun loadSechedules()
    }
}