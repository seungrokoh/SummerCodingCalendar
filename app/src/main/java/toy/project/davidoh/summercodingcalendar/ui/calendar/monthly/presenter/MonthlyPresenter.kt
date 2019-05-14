package toy.project.davidoh.summercodingcalendar.ui.calendar.monthly.presenter

import com.prolificinteractive.materialcalendarview.CalendarDay
import org.threeten.bp.LocalDate
import java.util.ArrayList

class MonthlyPresenter(private val view: MonthlyContractor.View)
    : MonthlyContractor.Presenter {
    override fun loadSechedules() {
        var temp = LocalDate.now().minusMonths(2)
        val dates = ArrayList<CalendarDay>()
        for (i in 0..29) {
            val day = CalendarDay.from(temp)
            dates.add(day)
            temp = temp.plusDays(5)
        }

        view.showSchedules(dates)
    }

}