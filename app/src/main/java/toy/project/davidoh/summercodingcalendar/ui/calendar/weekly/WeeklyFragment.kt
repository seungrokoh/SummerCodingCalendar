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
import kotlinx.android.synthetic.main.fragment_weekly.*
import kotlinx.android.synthetic.main.include_calendar.*
import toy.project.davidoh.summercodingcalendar.Global.cachedFragment
import toy.project.davidoh.summercodingcalendar.Global.cachedSelectedDate
import toy.project.davidoh.summercodingcalendar.R
import toy.project.davidoh.summercodingcalendar.ui.calendar.adapter.SchedulesListAdapter
import toy.project.davidoh.summercodingcalendar.ui.calendar.monthly.MonthlyFragment
import toy.project.davidoh.summercodingcalendar.ui.calendar.weekly.presenter.WeeklyContractor
import toy.project.davidoh.summercodingcalendar.ui.calendar.weekly.presenter.WeeklyPresenter
import toy.project.davidoh.summercodingcalendar.util.Injection
import toy.project.davidoh.summercodingcalendar.util.decorator.EventDecorator

class WeeklyFragment : Fragment(), WeeklyContractor.View,
    OnDateSelectedListener {

    private val weeklyPresenter: WeeklyContractor.Presenter by lazy {
        WeeklyPresenter(
            this,
            schedulesAdapter,
            Injection.provideTaskRepository(activity?.applicationContext!!)
        )
    }

    private val schedulesAdapter: SchedulesListAdapter by lazy {
        SchedulesListAdapter(this@WeeklyFragment.context!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_weekly, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cacheFragment()
        widgetInit()

        weeklyPresenter.loadSchedulesAllDay()
        weeklyPresenter.getSchedulesOnDay(cachedSelectedDate!!)
    }

    private fun widgetInit() {

        material_claendar.setViewInit(CalendarMode.WEEKS).apply {
            setOnDateChangedListener(this@WeeklyFragment)
        }

        rv_schedules.apply {
            adapter = schedulesAdapter
            layoutManager = LinearLayoutManager(this@WeeklyFragment.context)
        }
    }

    private fun cacheFragment() {
        cachedFragment = this
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
        cachedSelectedDate = date
        weeklyPresenter.getSchedulesOnDay(cachedSelectedDate!!)
    }

    override fun showDecorateOnCalendar(schedules: MutableList<CalendarDay>) {
        material_claendar.addDecorator(
            EventDecorator(
                Color.RED,
                schedules
            )
        )
    }

    companion object {
        private var INSTANCE: WeeklyFragment? = null

        fun getInstance(): WeeklyFragment {
            if (INSTANCE == null) {
                return WeeklyFragment()
            }
            return INSTANCE!!
        }
    }
}