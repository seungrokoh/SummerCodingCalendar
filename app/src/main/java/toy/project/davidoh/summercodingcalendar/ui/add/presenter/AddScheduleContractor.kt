package toy.project.davidoh.summercodingcalendar.ui.add.presenter

import com.prolificinteractive.materialcalendarview.CalendarDay

interface AddScheduleContractor {
    interface View {
        fun showProgress()
        fun hideProgress()

        fun showSuccessMessage(message: String)
        fun showInfoMessage(message: String)
        fun showErrorMessage(message: String)

        fun dialogDismiss()

        fun setAddButtonEnable(enable: Boolean)
    }
    interface Presenter {
        fun addSchedule(date: CalendarDay, title: String, description: String)
    }
}