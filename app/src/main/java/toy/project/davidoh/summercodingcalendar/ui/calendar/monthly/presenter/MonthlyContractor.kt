package toy.project.davidoh.summercodingcalendar.ui.calendar.monthly.presenter

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
    }

    interface Presenter {

    }
}