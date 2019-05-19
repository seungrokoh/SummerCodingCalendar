package toy.project.davidoh.summercodingcalendar.ui.add

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_add_schedule.*
import kotlinx.android.synthetic.main.fragment_add_schedule.view.*
import toy.project.davidoh.summercodingcalendar.R
import toy.project.davidoh.summercodingcalendar.ui.add.presenter.AddScheduleContractor
import toy.project.davidoh.summercodingcalendar.ui.add.presenter.AddSchedulePresenter
import toy.project.davidoh.summercodingcalendar.util.Injection
import toy.project.davidoh.summercodingcalendar.util.nowLocalDate

class AddScheduleFragment : Fragment(), AddScheduleContractor.View, CompoundButton.OnCheckedChangeListener {

    override var isActive: Boolean = false
        get() = isAdded

    private val addSchedulePresenter: AddSchedulePresenter by lazy {
        AddSchedulePresenter(this,
                Injection.provideTaskRepository(context!!)
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_add_schedule, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        widgetInit(view)
    }

    private fun widgetInit(view: View) {
        view.mcv_add.apply {
            state().edit()
                    .setMinimumDate(CalendarDay.from(2018, 10, 1))
                    .setCalendarDisplayMode(CalendarMode.WEEKS).commit()
            setSelectedDate(nowLocalDate())
            setTitleFormatter { calendarDay -> "${calendarDay.year}년 ${calendarDay.month}월" }
        }

        switch_mode.setOnCheckedChangeListener(this)

        view.btn_add_schedule.setOnClickListener {
            addSchedulePresenter.addSchedule(view.mcv_add.selectedDate!!,
                    view.et_title.text.toString(),
                    view.et_description.text.toString())
        }

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

    override fun setAddButtonEnable(enable: Boolean) {
        btn_add_schedule.isEnabled = enable
    }

    override fun finish() {
        destroyInstance()
        activity?.finish()
    }

    override fun destroyInstance() {
        INSTANCE = null
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        val mode = if (isChecked) {
            CalendarMode.MONTHS} else {
            CalendarMode.WEEKS}

        mcv_add.state().edit().setCalendarDisplayMode(mode).commit()
    }

    companion object {
        private var INSTANCE: AddScheduleFragment? = null

        fun getInstance(): AddScheduleFragment {
            if (INSTANCE == null) {
                synchronized(AddScheduleFragment::class) {
                    INSTANCE = AddScheduleFragment()
                }
            }
            return INSTANCE!!
        }
    }
}