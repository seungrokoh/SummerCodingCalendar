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
            // 일정 삭제, 수정 기능 추가시 사용
            view.showDeleteDialog(position)
        }
    }

    override fun getSchedulesOnDay(date: CalendarDay) = launchSilent(uiContext) {
        val result = schedulesRepository.getSchedulesOnDay(date)
        scheduleModel.clear()
        if (result is Result.Success && result.data.isNotEmpty()) {
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

    override fun drawEventsOnCalendar() = launchSilent(uiContext) {
        val result = schedulesRepository.getSchedulesAllDay()
        if (result is Result.Success) {
            if (view.isActive) {
                addDotDecoratorOnCalendar(result.data)
            }
        }
    }

    private fun addDotDecoratorOnCalendar(schedules: List<Schedule>) {
        val dateList = mutableListOf<CalendarDay>()
        for (item in schedules) {
            dateList.add(item.date)
        }
        view.showDecorateOnCalendar(dateList)
    }

    private fun deleteDecoratorOncalendar(date: CalendarDay) {
        view.deleteDecorateOnCalendar(date)
    }

    override fun deleteSchedule(position: Int) = launchSilent(uiContext) {
        val result = schedulesRepository.deleteSchedule(scheduleModel.getItem(position))

        if (result > 0) {
            if (view.isActive) {
                view.showSuccessMessage("삭제 완료")
                val schedule = scheduleModel.getItem(position)

                scheduleModel.removeItem(position)
                scheduleModel.notifyDataSetChange()
                if (scheduleModel.isEmpty()) {
                    view.showScheduleEmptyView()
                    deleteDecoratorOncalendar(schedule.date)
                }
            }
        } else {
            if (view.isActive) {
                view.showErrorMesage("삭제 실패")
            }
        }
    }

}