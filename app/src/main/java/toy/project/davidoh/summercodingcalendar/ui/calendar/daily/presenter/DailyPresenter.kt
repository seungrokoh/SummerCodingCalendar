package toy.project.davidoh.summercodingcalendar.ui.calendar.daily.presenter

import kotlinx.coroutines.Dispatchers
import toy.project.davidoh.summercodingcalendar.Global.cachedSelectedDate
import toy.project.davidoh.summercodingcalendar.data.source.Result
import toy.project.davidoh.summercodingcalendar.data.source.SchedulesRepository
import toy.project.davidoh.summercodingcalendar.ui.calendar.adapter.model.ScheduleModel
import toy.project.davidoh.summercodingcalendar.util.launchSilent
import toy.project.davidoh.summercodingcalendar.util.nextDay
import toy.project.davidoh.summercodingcalendar.util.preDay
import kotlin.coroutines.CoroutineContext

class DailyPresenter(private val view: DailyContractor.View,
                     private val scheduleModel: ScheduleModel,
                     private val schedulesRepository: SchedulesRepository,
                     private val uiContext: CoroutineContext = Dispatchers.Main)
    : DailyContractor.Presenter {
    init {
        scheduleModel.onclick = { position ->
            view.showDeleteDialog(position)
        }
    }

    override fun getSchedulesOnDay() = launchSilent(uiContext) {
        val result = schedulesRepository.getSchedulesOnDay(cachedSelectedDate!!)
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

    override fun deleteSchedule(position: Int) = launchSilent(uiContext) {
        val result = schedulesRepository.deleteSchedule(scheduleModel.getItem(position))

        if (result > 0) {
            if (view.isActive) {
                view.showSuccessMessage("삭제 완료")
                scheduleModel.removeItem(position)
                scheduleModel.notifyDataSetChange()
                if (scheduleModel.isEmpty()) {
                    view.showScheduleEmptyView()
                }
            }
        } else {
            if (view.isActive) {
                view.showErrorMesage("삭제 실패")
            }
        }
    }

    override fun movePreDay() {
        cachedSelectedDate = preDay(cachedSelectedDate!!)
        getSchedulesOnDay()
        view.showCurrentDate()
    }

    override fun moveNextDay() {
        cachedSelectedDate = nextDay(cachedSelectedDate!!)
        getSchedulesOnDay()
        view.showCurrentDate()
    }


}