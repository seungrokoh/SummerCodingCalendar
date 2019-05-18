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
import kotlinx.android.synthetic.main.include_calendar.*
import toy.project.davidoh.summercodingcalendar.Global.PREF_MONTHLY
import toy.project.davidoh.summercodingcalendar.Global.cachedSelectedDate
import toy.project.davidoh.summercodingcalendar.R
import toy.project.davidoh.summercodingcalendar.ui.calendar.monthly.presenter.MonthlyContractor
import toy.project.davidoh.summercodingcalendar.ui.calendar.monthly.presenter.MonthlyPresenter
import toy.project.davidoh.summercodingcalendar.util.Injection
import toy.project.davidoh.summercodingcalendar.util.SharedPreferenceUtil
import toy.project.davidoh.summercodingcalendar.util.decorator.EventDecorator
import toy.project.davidoh.summercodingcalendar.util.logE

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

        monthlyPresenter.loadSchedulesAllDay()
    }

    private fun initWidget() {
        material_claendar.setViewInit(CalendarMode.MONTHS).apply {
            setOnDateChangedListener(this@MonthlyFragment)
        }
    }

    private fun cacheFragment() {
        SharedPreferenceUtil(activity?.applicationContext!!).put("LAST_FRAGMENT", PREF_MONTHLY)
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
        logE(schedules.toString())
        material_claendar.addDecorator(
                EventDecorator(
                        Color.RED,
                        schedules
                )
        )
    }

    override fun onDateSelected(widget: MaterialCalendarView, date: CalendarDay, selected: Boolean) {
        cachedSelectedDate = date
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
