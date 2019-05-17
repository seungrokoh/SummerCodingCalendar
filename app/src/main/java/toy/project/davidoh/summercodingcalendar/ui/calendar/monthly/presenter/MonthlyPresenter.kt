package toy.project.davidoh.summercodingcalendar.ui.calendar.monthly.presenter

import com.prolificinteractive.materialcalendarview.CalendarDay
import toy.project.davidoh.summercodingcalendar.data.Schedule
import toy.project.davidoh.summercodingcalendar.data.source.SchedulesDataSource
import toy.project.davidoh.summercodingcalendar.data.source.SchedulesRepository

class MonthlyPresenter(private val view: MonthlyContractor.View,
                       private val schedulesRepository: SchedulesRepository)
    : MonthlyContractor.Presenter {

    override fun loadSchedulesAllDay() {
        schedulesRepository.getSchedulesAllDay(object : SchedulesDataSource.LoadSchedulesCallback {
            override fun onSchedulesLoaded(schedules: List<Schedule>) {
                addDotDecorator(schedules)
            }

            override fun onDataNotAvailable() {
                // Nothing...
            }
        })

    }

    private fun addDotDecorator(schedules: List<Schedule>) {
        val dateList = mutableListOf<CalendarDay>()
        for (item in schedules) {
            dateList.add(item.date)
        }
        view.showDecorateOnCalendar(dateList)
    }

}