package toy.project.davidoh.summercodingcalendar.ui.calendar.daily

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_daily.*
import toy.project.davidoh.summercodingcalendar.Global.cachedSelectedDate
import toy.project.davidoh.summercodingcalendar.R
import toy.project.davidoh.summercodingcalendar.ui.calendar.adapter.SchedulesListAdapter
import toy.project.davidoh.summercodingcalendar.ui.calendar.daily.presenter.DailyContractor
import toy.project.davidoh.summercodingcalendar.ui.calendar.daily.presenter.DailyPresenter
import toy.project.davidoh.summercodingcalendar.util.Injection

class DailyFragment : Fragment(), DailyContractor.View {
    private val dailyPresenter: DailyContractor.Presenter by lazy {
        DailyPresenter(this,
                schedulesListAdapter,
                Injection.provideTaskRepository(context!!)
        )
    }

    private val schedulesListAdapter: SchedulesListAdapter by lazy {
        SchedulesListAdapter(context!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_daily, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        widgetInit()
        dailyPresenter.getSchedulesOnDay()
    }

    private fun widgetInit() {
        rv_schedules.apply {
            adapter = schedulesListAdapter
            layoutManager = LinearLayoutManager(this@DailyFragment.context)
        }

        iv_left.setOnClickListener {
            dailyPresenter.movePreDay()
        }

        iv_right.setOnClickListener {
            dailyPresenter.moveNextDay()
        }

        tv_date.text = getCurrentDayToString()
    }

    override fun showCurrentDate() {
        tv_date.text = getCurrentDayToString()
    }

    private fun getCurrentDayToString(): String = "${cachedSelectedDate?.year}년 " +
            "${cachedSelectedDate?.month}월 " +
            "${cachedSelectedDate?.day}일"

    companion object {

        private var INSTANCE: DailyFragment? = null

        fun getInstance(): DailyFragment {
            if (INSTANCE == null) {
                INSTANCE = DailyFragment()
            }
            return INSTANCE!!
        }
    }
}