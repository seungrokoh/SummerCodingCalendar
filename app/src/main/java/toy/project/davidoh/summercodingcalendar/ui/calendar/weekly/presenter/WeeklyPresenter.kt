package toy.project.davidoh.summercodingcalendar.ui.calendar.weekly.presenter

import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.coroutines.Dispatchers
import toy.project.davidoh.summercodingcalendar.data.Schedule
import toy.project.davidoh.summercodingcalendar.data.source.Result
import toy.project.davidoh.summercodingcalendar.data.source.SchedulesRepository
import toy.project.davidoh.summercodingcalendar.ui.calendar.adapter.model.ScheduleModel
import toy.project.davidoh.summercodingcalendar.util.launchSilent
import toy.project.davidoh.summercodingcalendar.util.logE
import kotlin.coroutines.CoroutineContext

class WeeklyPresenter(
    private val view: WeeklyContractor.View,
    private val scheduleModel: ScheduleModel,
    private val schedulesRepository: SchedulesRepository,
    private val uiContext: CoroutineContext = Dispatchers.Main
) : WeeklyContractor.Presenter {
    init {
        scheduleModel.onclick = { position ->
            logE(scheduleModel.getItem(position).toString())
        }
    }

    override fun getSchedulesOnDay(date: CalendarDay) = launchSilent(uiContext) {
        val result = schedulesRepository.getSchedulesOnDay(date)
        scheduleModel.clear()
        if (result is Result.Success) {
            result.data.forEach {
                scheduleModel.addItem(it)
            }
            if (view.isActive) {
                view.showScheduleRecyclerView()
                scheduleModel.notifyDataSetChange()
            }
        } else {
            if (view.isActive) {
                scheduleModel.notifyDataSetChange()
                view.showScheduleEmptyView()
            }
        }
    }

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