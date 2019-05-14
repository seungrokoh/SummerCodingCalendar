package toy.project.davidoh.summercodingcalendar.ui.calendar.monthly.presenter

import com.prolificinteractive.materialcalendarview.CalendarDay
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
        fun navigateToAdd()
        fun showSchedules(schedules: ArrayList<CalendarDay>)
    }

    interface Presenter {
        fun loadSechedules()
    }
}