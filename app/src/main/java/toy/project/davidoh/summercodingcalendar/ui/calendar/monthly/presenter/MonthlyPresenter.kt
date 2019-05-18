package toy.project.davidoh.summercodingcalendar.ui.calendar.monthly.presenter

import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.coroutines.Dispatchers
import toy.project.davidoh.summercodingcalendar.data.Schedule
import toy.project.davidoh.summercodingcalendar.data.source.Result
import toy.project.davidoh.summercodingcalendar.data.source.SchedulesRepository
import toy.project.davidoh.summercodingcalendar.util.launchSilent
import kotlin.coroutines.CoroutineContext

class MonthlyPresenter(
    private val view: MonthlyContractor.View,
    private val schedulesRepository: SchedulesRepository,
    private val uiContext: CoroutineContext = Dispatchers.Main
) : MonthlyContractor.Presenter {

    override fun loadSchedulesAllDay() = launchSilent(uiContext) {
        val result = schedulesRepository.getSchedulesAllDay()
        if (result is Result.Success) {
            if (view.isActive) {
                addDotDecorator(result.data)
            }
        }
    }

    private fun addDotDecorator(schedules: List<Schedule>) {
        val dateList = mutableListOf<CalendarDay>()
        for (item in schedules) {
            dateList.add(item.date)
        }
        view.showDecorateOnCalendar(dateList)
    }

}