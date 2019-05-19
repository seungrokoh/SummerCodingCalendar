package toy.project.davidoh.summercodingcalendar.ui.calendar.daily.presenter

interface DailyContractor {
    interface View {
        var isActive: Boolean

        fun showSuccessMessage(message: String)
        fun showInfoMessage(message: String)
        fun showErrorMesage(message: String)

        fun showScheduleRecyclerView()
        fun showScheduleEmptyView()

        fun showCurrentDate()
    }

    interface Presenter {
        fun getSchedulesOnDay()

        fun movePreDay()
        fun moveNextDay()
    }
}