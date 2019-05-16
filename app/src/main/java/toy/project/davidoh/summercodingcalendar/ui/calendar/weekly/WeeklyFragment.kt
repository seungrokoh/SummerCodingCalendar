package toy.project.davidoh.summercodingcalendar.ui.calendar.weekly

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import kotlinx.android.synthetic.main.fragment_monthly.*
import kotlinx.android.synthetic.main.fragment_weekly.*
import toy.project.davidoh.summercodingcalendar.R
import toy.project.davidoh.summercodingcalendar.data.source.SchedulesRepository
import toy.project.davidoh.summercodingcalendar.data.source.local.ScheduleDatabase
import toy.project.davidoh.summercodingcalendar.data.source.local.SchedulesLocalDataSource
import toy.project.davidoh.summercodingcalendar.ui.calendar.weekly.presenter.WeeklyContractor
import toy.project.davidoh.summercodingcalendar.ui.calendar.weekly.presenter.WeeklyPresenter
import toy.project.davidoh.summercodingcalendar.util.nowDate

class WeeklyFragment : Fragment(), WeeklyContractor.View {

    private val weeklyPresenter: WeeklyContractor.Presenter by lazy {
        WeeklyPresenter(this,
                SchedulesRepository.getInstance(
                        SchedulesLocalDataSource.getInstance(
                                ScheduleDatabase.getInstance(context!!).SchedulesDao()
                        )
                ))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_weekly, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        widgetInit()
        weeklyPresenter.loadScheduleOnDay(mcv_weekly.selectedDate!!)
    }

    private fun widgetInit() {
        mcv_weekly.apply {
            state().edit()
                    .setMinimumDate(CalendarDay.from(2018, 10, 1))
                    .setCalendarDisplayMode(CalendarMode.WEEKS)
                    .commit()
            setTitleFormatter { calendarDay -> "${calendarDay.year}년 ${calendarDay.month}월" }
            selectionColor = resources.getColor(R.color.colorAccent)
            setSelectedDate(nowDate())
        }
    }

    companion object {
        private var INSTANCE: WeeklyFragment? = null

        fun getInstance(): WeeklyFragment {
            if (INSTANCE == null) {
                synchronized(WeeklyFragment::class) {
                    if (INSTANCE == null) {
                        INSTANCE = WeeklyFragment()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}