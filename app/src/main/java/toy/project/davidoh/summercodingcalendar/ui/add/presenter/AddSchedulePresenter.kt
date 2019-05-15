package toy.project.davidoh.summercodingcalendar.ui.add.presenter

import android.text.TextUtils
import com.prolificinteractive.materialcalendarview.CalendarDay
import toy.project.davidoh.summercodingcalendar.data.source.SchedulesRepository

class AddSchedulePresenter (private var view: AddScheduleContractor.View?,
                            private val schedulesRepository: SchedulesRepository)
    : AddScheduleContractor.Presenter {
    override fun addSchedule(date: CalendarDay, title: String, description: String) {
        if (isEmpty(date, title, description)) {
            schedulesRepository.addSchedule(date, title, description)
        }
    }

    private fun isEmpty(date: CalendarDay?, title: String?, description: String?) : Boolean{
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