package toy.project.davidoh.summercodingcalendar.ui.calendar.monthly

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_monthly.*
import toy.project.davidoh.summercodingcalendar.Global.PREF_KEY_LAST_FRAGMENT
import toy.project.davidoh.summercodingcalendar.Global.PREF_MONTHLY
import toy.project.davidoh.summercodingcalendar.Global.cachedSelectedDate
import toy.project.davidoh.summercodingcalendar.R
import toy.project.davidoh.summercodingcalendar.ui.calendar.monthly.presenter.MonthlyContractor
import toy.project.davidoh.summercodingcalendar.ui.calendar.monthly.presenter.MonthlyPresenter
import toy.project.davidoh.summercodingcalendar.util.Injection
import toy.project.davidoh.summercodingcalendar.util.SharedPreferenceUtil
import toy.project.davidoh.summercodingcalendar.util.decorator.EventDecorator
import toy.project.davidoh.summercodingcalendar.util.decorator.SaturdayDecorator
import toy.project.davidoh.summercodingcalendar.util.decorator.SunDayDecorator
import toy.project.davidoh.summercodingcalendar.util.decorator.TodayDecorator
import toy.project.davidoh.summercodingcalendar.util.nowLocalDate

class MonthlyFragment : Fragment(),
        MonthlyContractor.View,
        OnDateSelectedListener {

    override var isActive: Boolean = false
        get() = isAdded

    private val monthlyPresenter: MonthlyContractor.Presenter by lazy {
        MonthlyPresenter(
                this,
                Injection.provideTaskRepository(activity?.applicationContext!!)
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_monthly, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cacheFragment()
        initWidget()
    }

    override fun onResume() {
        super.onResume()
        mcv_monthly.apply {
            currentDate = cachedSelectedDate
            selectedDate = cachedSelectedDate
            selectionColor = resources.getColor(R.color.colorAccent)
        }
        monthlyPresenter.drawEventsOnCalendar()
    }

    private fun initWidget() {
        mcv_monthly.apply {
            state().edit()
                    .setMinimumDate(CalendarDay.from(1992, 7, 11))
                    .setMaximumDate(CalendarDay.from(2099, 7, 11))
                    .setCalendarDisplayMode(CalendarMode.MONTHS)
                    .commit()

            setTitleFormatter { calendarDay -> "${calendarDay.year}년 ${calendarDay.month}월" }
            setOnDateChangedListener(this@MonthlyFragment)
            addDecorators(SaturdayDecorator(),
                    SunDayDecorator() ,
                    TodayDecorator(CalendarDay.from(nowLocalDate()))
            )
        }
    }

    private fun cacheFragment() {
        SharedPreferenceUtil(activity?.applicationContext!!).put(PREF_KEY_LAST_FRAGMENT, PREF_MONTHLY)
    }

    override fun showSuccessMessage(message: String) {
        Toasty.success(context!!, message, Toast.LENGTH_SHORT).show()
    }

    override fun showInfoMessage(message: String) {
        Toasty.info(context!!, message, Toast.LENGTH_SHORT).show()
    }

    override fun showErrorMessage(message: String) {
        Toasty.error(context!!, message, Toast.LENGTH_SHORT).show()
    }

    override fun showDecorateOnCalendar(schedules: MutableList<CalendarDay>) {
        mcv_monthly.addDecorator(
                EventDecorator(
                        Color.BLACK,
                        schedules
                )
        )
    }

    override fun onDateSelected(widget: MaterialCalendarView, date: CalendarDay, selected: Boolean) {
        cachedSelectedDate = date
    }

    override fun destroy() {
        INSTANCE?.let {
            INSTANCE = null
        }
    }

    companion object {
        private var INSTANCE: MonthlyFragment? = null

        fun getInstance(): MonthlyFragment {
            if (INSTANCE == null) {
                return MonthlyFragment()
            }
            return INSTANCE!!
        }
    }
}
