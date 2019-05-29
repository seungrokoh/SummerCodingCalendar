package toy.project.davidoh.summercodingcalendar.ui.calendar.weekly

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_weekly.*
import toy.project.davidoh.summercodingcalendar.Global.PREF_KEY_LAST_FRAGMENT
import toy.project.davidoh.summercodingcalendar.Global.PREF_WEEKLY
import toy.project.davidoh.summercodingcalendar.Global.cachedSelectedDate
import toy.project.davidoh.summercodingcalendar.R
import toy.project.davidoh.summercodingcalendar.ui.calendar.adapter.SchedulesListAdapter
import toy.project.davidoh.summercodingcalendar.ui.calendar.weekly.presenter.WeeklyContractor
import toy.project.davidoh.summercodingcalendar.ui.calendar.weekly.presenter.WeeklyPresenter
import toy.project.davidoh.summercodingcalendar.util.Injection
import toy.project.davidoh.summercodingcalendar.util.SharedPreferenceUtil
import toy.project.davidoh.summercodingcalendar.util.decorator.EventDecorator
import toy.project.davidoh.summercodingcalendar.util.decorator.SaturdayDecorator
import toy.project.davidoh.summercodingcalendar.util.decorator.SunDayDecorator
import toy.project.davidoh.summercodingcalendar.util.decorator.TodayDecorator
import toy.project.davidoh.summercodingcalendar.util.logE
import toy.project.davidoh.summercodingcalendar.util.nowLocalDate

class WeeklyFragment : Fragment(), WeeklyContractor.View,
    OnDateSelectedListener {

    override var isActive: Boolean = false
        get() = isAdded

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
        logE("onViewCreated")
    }

    override fun onResume() {
        super.onResume()
        logE("onResume")

        mcv_weekly.apply {
            currentDate = cachedSelectedDate
            selectedDate = cachedSelectedDate
            selectionColor = resources.getColor(R.color.colorAccent)
        }

        weeklyPresenter.drawEventsOnCalendar()
        weeklyPresenter.getSchedulesOnDay(cachedSelectedDate!!)
    }

    private fun widgetInit() {

        mcv_weekly.apply {
            state().edit()
                    .setMinimumDate(CalendarDay.from(1992, 7, 11))
                    .setMaximumDate(CalendarDay.from(2099, 7, 11))
                    .setCalendarDisplayMode(CalendarMode.WEEKS)
                    .commit()

            setTitleFormatter { calendarDay -> "${calendarDay.year}년 ${calendarDay.month}월" }
            setOnDateChangedListener(this@WeeklyFragment)
            addDecorators(SaturdayDecorator(),
                    SunDayDecorator() ,
                    TodayDecorator(CalendarDay.from(nowLocalDate()))
            )
        }

        rv_schedules.apply {
            adapter = schedulesAdapter
            layoutManager = LinearLayoutManager(this@WeeklyFragment.context)
        }
    }

    private fun cacheFragment() {
        SharedPreferenceUtil(activity?.applicationContext!!).put(PREF_KEY_LAST_FRAGMENT, PREF_WEEKLY)
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

    override fun showScheduleRecyclerView() {
        rv_schedules.visibility = View.VISIBLE
        tv_empty.visibility = View.GONE
    }

    override fun showScheduleEmptyView() {
        rv_schedules.visibility = View.GONE
        tv_empty.visibility = View.VISIBLE
    }

    override fun onDateSelected(widget: MaterialCalendarView, date: CalendarDay, selected: Boolean) {
        cachedSelectedDate = date
        weeklyPresenter.getSchedulesOnDay(cachedSelectedDate!!)
    }

    override fun showDecorateOnCalendar(schedules: MutableList<CalendarDay>) {
        mcv_weekly.addDecorator(
            EventDecorator(
                Color.BLACK,
                schedules
            )
        )
    }

    override fun showDeleteDialog(position: Int) {
        val builder = AlertDialog.Builder(this@WeeklyFragment.context!!)
        builder.setTitle("일정 삭제")
        builder.setMessage("선택한 일정을 삭제하시겠어요?")
        builder.setPositiveButton("삭제") { dialog, which -> weeklyPresenter.deleteSchedule(position) }
        builder.setNegativeButton("취소") { dialog, which ->  }
        builder.show()
    }

    override fun destroy() {
        INSTANCE?.let {
            INSTANCE = null
        }
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