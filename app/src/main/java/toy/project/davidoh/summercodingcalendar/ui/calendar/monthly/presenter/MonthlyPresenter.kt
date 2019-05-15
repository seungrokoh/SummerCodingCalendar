package toy.project.davidoh.summercodingcalendar.ui.calendar.monthly.presenter

import com.prolificinteractive.materialcalendarview.CalendarDay
import org.threeten.bp.LocalDate
import toy.project.davidoh.summercodingcalendar.data.Schedule
import toy.project.davidoh.summercodingcalendar.data.source.SchedulesDataSource
import toy.project.davidoh.summercodingcalendar.data.source.SchedulesRepository
import java.util.ArrayList

class MonthlyPresenter(private val view: MonthlyContractor.View,
                       private val repository: SchedulesRepository)
    : MonthlyContractor.Presenter {
    override fun loadSechedules() {
//        var temp = LocalDate.now().minusMonths(2)
//        val dates = ArrayList<CalendarDay>()
//        for (i in 0..29) {
//            val day = CalendarDay.from(temp)
//            dates.add(day)
//            temp = temp.plusDays(5)
//        }

        repository.getSchedules(object : SchedulesDataSource.LoadSchedulesCallback {
            override fun onSchedulesLoaded(scheduls: ArrayList<CalendarDay>) {
                view.showSchedules(scheduls)
            }

            override fun onDataNotAvailable() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

    }

}