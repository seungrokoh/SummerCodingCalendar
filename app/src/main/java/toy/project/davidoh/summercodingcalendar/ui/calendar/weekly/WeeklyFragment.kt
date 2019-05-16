package toy.project.davidoh.summercodingcalendar.ui.calendar.weekly

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_monthly.*
import kotlinx.android.synthetic.main.fragment_weekly.*
import toy.project.davidoh.summercodingcalendar.R
import toy.project.davidoh.summercodingcalendar.data.source.SchedulesRepository
import toy.project.davidoh.summercodingcalendar.data.source.local.ScheduleDatabase
import toy.project.davidoh.summercodingcalendar.data.source.local.SchedulesLocalDataSource
import toy.project.davidoh.summercodingcalendar.ui.calendar.adapter.SchedulesListAdapter
import toy.project.davidoh.summercodingcalendar.ui.calendar.weekly.presenter.WeeklyContractor
import toy.project.davidoh.summercodingcalendar.ui.calendar.weekly.presenter.WeeklyPresenter
import toy.project.davidoh.summercodingcalendar.util.EventDecorator
import toy.project.davidoh.summercodingcalendar.util.Injection
import toy.project.davidoh.summercodingcalendar.util.logE
import toy.project.davidoh.summercodingcalendar.util.nowDate

class WeeklyFragment : Fragment(), WeeklyContractor.View,
        OnDateSelectedListener {
    private val weeklyPresenter: WeeklyContractor.Presenter by lazy {
        WeeklyPresenter(this,
                schedulesAdapter,
                Injection.provideTaskRepository(activity?.applicationContext!!))
    }

    private val schedulesAdapter: SchedulesListAdapter by lazy {
        SchedulesListAdapter(this@WeeklyFragment.context!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_weekly, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        widgetInit()
        weeklyPresenter.loadScheduleAllDay()
    }

    private fun widgetInit() {
        mcv_weekly.apply {
            state().edit()
                    .setMinimumDate(CalendarDay.from(2018, 10, 1))
                    .setCalendarDisplayMode(CalendarMode.WEEKS)
                    .commit()
            setTitleFormatter { calendarDay -> "${calendarDay.year}년 ${calendarDay.month}월" }
            selectionColor = resources.getColor(R.color.colorAccent)
            setOnDateChangedListener(this@WeeklyFragment)
            setSelectedDate(nowDate())
        }

        rv_schedules.apply {
            adapter = schedulesAdapter
            layoutManager = LinearLayoutManager(this@WeeklyFragment.context)
        }
    }


    override fun showSuccessMessage(message: String) {
        Toasty.success(context!!, message, Toast.LENGTH_SHORT).show()
    }

    override fun showInfoMessage(message: String) {
        Toasty.info(context!!, message, Toast.LENGTH_SHORT).show()
    }

    override fun showErrorMesage(message: String) {
        Toasty.error(context!!, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDateSelected(widget: MaterialCalendarView, date: CalendarDay, selected: Boolean) {
        weeklyPresenter.loadScheduleOnDay(date)
    }

    override fun showDecorateOnCalendar(schedules: MutableList<CalendarDay>) {
//        logE(schedules.toString())
        mcv_weekly.addDecorator(EventDecorator(Color.RED, schedules))
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