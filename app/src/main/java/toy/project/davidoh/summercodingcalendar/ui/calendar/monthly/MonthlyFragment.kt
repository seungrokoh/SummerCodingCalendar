package toy.project.davidoh.summercodingcalendar.ui.calendar.monthly

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_monthly.*
import toy.project.davidoh.summercodingcalendar.R
import toy.project.davidoh.summercodingcalendar.data.Schedule
import toy.project.davidoh.summercodingcalendar.data.source.SchedulesRepository
import toy.project.davidoh.summercodingcalendar.data.source.local.ScheduleDatabase
import toy.project.davidoh.summercodingcalendar.data.source.local.SchedulesLocalDataSource
import toy.project.davidoh.summercodingcalendar.ui.calendar.monthly.presenter.MonthlyContractor
import toy.project.davidoh.summercodingcalendar.ui.calendar.monthly.presenter.MonthlyPresenter
import toy.project.davidoh.summercodingcalendar.util.EventDecorator
import toy.project.davidoh.summercodingcalendar.util.logE
import toy.project.davidoh.summercodingcalendar.util.nowDate
import java.util.*

class MonthlyFragment : Fragment(),
    MonthlyContractor.View,
    OnDateSelectedListener,
    OnMonthChangedListener {
    companion object {

        fun newInstance(): MonthlyFragment {
            return MonthlyFragment()
        }
    }

    private val monthlyPresenter: MonthlyPresenter by lazy {
        MonthlyPresenter(
            this,
            SchedulesRepository.getInstance(
                SchedulesLocalDataSource.getInstance(
                    ScheduleDatabase.getInstance(context!!).SchedulesDao()
                )
            )
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_monthly, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initWidget()
    }

    private fun initWidget() {
        mcv_monthly.apply {
            setOnDateChangedListener(this@MonthlyFragment)
            state().edit().setMinimumDate(CalendarDay.from(2018, 10, 1)).commit()
            setTitleFormatter { calendarDay -> "${calendarDay.month}월" }
            setOnMonthChangedListener(this@MonthlyFragment)
            selectionColor = resources.getColor(R.color.colorAccent)
            setSelectedDate(nowDate())
        }
        monthlyPresenter.loadSechedules()
    }

    override fun showProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

    override fun navigateToWeekly() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun navigateToDaily() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showDecorateOnCalendar(schedules: MutableList<CalendarDay>) {
        logE(schedules.toString())
        mcv_monthly.addDecorator(EventDecorator(Color.RED, schedules))
    }

    override fun onDateSelected(widget: MaterialCalendarView, date: CalendarDay, selected: Boolean) {
        logE("onDateSelected : date : $date")
    }

    override fun onMonthChanged(widget: MaterialCalendarView?, date: CalendarDay?) {
        logE("onMonthChanged")
    }
}
