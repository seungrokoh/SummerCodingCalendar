package toy.project.davidoh.summercodingcalendar.ui.add.presenter

import android.text.TextUtils
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.coroutines.Dispatchers
import toy.project.davidoh.summercodingcalendar.data.Schedule
import toy.project.davidoh.summercodingcalendar.data.source.SchedulesDataSource
import toy.project.davidoh.summercodingcalendar.data.source.SchedulesRepository
import toy.project.davidoh.summercodingcalendar.util.launchSilent
import kotlin.coroutines.CoroutineContext

class AddSchedulePresenter(
    private var view: AddScheduleContractor.View,
    private val schedulesRepository: SchedulesRepository
) : AddScheduleContractor.Presenter {

    override fun addSchedule(
        date: CalendarDay,
        title: String,
        description: String
    ) {
        view.setAddButtonEnable(false)
        if (isEmpty(date, title, description)) {
            schedulesRepository.addSchedule(Schedule(
                title = title,
                description = description,
                date = date
            ), object : SchedulesDataSource.InsertScheduleCallback {
                override fun onScheduleInserted() {
                    if (view.isActive) {
                        view.setAddButtonEnable(true)
                        view.showSuccessMessage("등록 완료")
                        view.finish()
                    }
                }

                override fun onInsertFailed() {
                    if (view.isActive) {
                        view.setAddButtonEnable(true)
                        view.showErrorMessage("일정 입력에 실패하였습니다.")
                    }
                }
            })
        }
    }

    private fun isEmpty(date: CalendarDay?, title: String?, description: String?): Boolean {
        if (date == null) {
            view.showInfoMessage("날짜를 선택해주세요.")
            return false
        }
        if (TextUtils.isEmpty(title)) {
            view.showInfoMessage("제목을 입력해주세요.")
            return false
        }
        if (TextUtils.isEmpty(description)) {
            view.showInfoMessage("내용을 입력해주세요.")
            return false
        }
        return true
    }
}