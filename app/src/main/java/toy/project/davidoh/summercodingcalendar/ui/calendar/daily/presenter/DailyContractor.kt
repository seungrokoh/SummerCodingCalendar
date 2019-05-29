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

        fun showDeleteDialog(position: Int)

        fun destroy()
    }

    interface Presenter {
        fun getSchedulesOnDay()
        fun deleteSchedule(position: Int)

        fun movePreDay()
        fun moveNextDay()
    }
}