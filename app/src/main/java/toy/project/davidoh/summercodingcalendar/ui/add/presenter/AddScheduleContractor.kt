package toy.project.davidoh.summercodingcalendar.ui.add.presenter

import com.prolificinteractive.materialcalendarview.CalendarDay

interface AddScheduleContractor {
    interface View {
        fun showSuccessMessage(message: String)
        fun showInfoMessage(message: String)
        fun showErrorMessage(message: String)

        fun dialogDismiss()
    }
    interface Presenter {
        fun addSchedule(date: CalendarDay, title: String, description: String)
    }
}