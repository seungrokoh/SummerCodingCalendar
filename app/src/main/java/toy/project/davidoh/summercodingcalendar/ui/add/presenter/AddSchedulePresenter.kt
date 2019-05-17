package toy.project.davidoh.summercodingcalendar.ui.add.presenter

import android.text.TextUtils
import com.prolificinteractive.materialcalendarview.CalendarDay
import toy.project.davidoh.summercodingcalendar.data.Schedule
import toy.project.davidoh.summercodingcalendar.data.source.SchedulesDataSource
import toy.project.davidoh.summercodingcalendar.data.source.SchedulesRepository

class AddSchedulePresenter(
    private var view: AddScheduleContractor.View?,
    private val schedulesRepository: SchedulesRepository
) : AddScheduleContractor.Presenter {
    override fun addSchedule(date: CalendarDay, title: String, description: String) {
        view?.showProgress()
        view?.setAddButtonEnable(false)
        if (isEmpty(date, title, description)) {
            schedulesRepository.addSchedule(Schedule(
                title = title,
                description = description,
                date = date
            ), object : SchedulesDataSource.InsertScheduleCallback {
                override fun onScheduleInserted() {
                    view?.setAddButtonEnable(true)
                    view?.dialogDismiss()
                    view?.hideProgress()
                }

                override fun onInsertFailed() {
                    view?.setAddButtonEnable(true)
                    view?.hideProgress()
                }
            })
        }
    }

    private fun isEmpty(date: CalendarDay?, title: String?, description: String?): Boolean {
        if (date == null) {
            view?.showInfoMessage("날짜를 선택해주세요.")
            return false
        }
        if (TextUtils.isEmpty(title)) {
            view?.showInfoMessage("제목을 입력해주세요.")
            return false
        }
        if (TextUtils.isEmpty(description)) {
            view?.showInfoMessage("내용을 입력해주세요.")
            return false
        }
        return true
    }
}