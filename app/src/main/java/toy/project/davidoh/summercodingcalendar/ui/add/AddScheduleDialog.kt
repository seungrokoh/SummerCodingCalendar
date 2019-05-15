package toy.project.davidoh.summercodingcalendar.ui.add

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.CalendarMode
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_add_schedule.*
import kotlinx.android.synthetic.main.fragment_add_schedule.view.*
import toy.project.davidoh.summercodingcalendar.R
import toy.project.davidoh.summercodingcalendar.data.source.SchedulesRepository
import toy.project.davidoh.summercodingcalendar.data.source.local.ScheduleDatabase
import toy.project.davidoh.summercodingcalendar.data.source.local.SchedulesLocalDataSource
import toy.project.davidoh.summercodingcalendar.ui.add.presenter.AddScheduleContractor
import toy.project.davidoh.summercodingcalendar.ui.add.presenter.AddSchedulePresenter
import toy.project.davidoh.summercodingcalendar.util.nowDate


class AddScheduleDialog : DialogFragment(), AddScheduleContractor.View, CompoundButton.OnCheckedChangeListener{

    private val addSchedulePresenter: AddSchedulePresenter by lazy {
        AddSchedulePresenter(this,
                SchedulesRepository.getInstance(
                        SchedulesLocalDataSource.getInstance(
                                ScheduleDatabase.getInstance(context!!).SchedulesDao()
                        )
                )
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_add_schedule, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        widgetInit(view)
    }

    private fun widgetInit(view: View) {
        isCancelable = false
        view.mcv_add.apply {
            state().edit()
                    .setMinimumDate(CalendarDay.from(2018, 10, 1))
                    .setCalendarDisplayMode(CalendarMode.WEEKS).commit()
            setSelectedDate(nowDate())
        }

        switch_mode.setOnCheckedChangeListener(this)

        view.btn_add_schedule.setOnClickListener {
            addSchedulePresenter.addSchedule(view.mcv_add.selectedDate!!,
                    view.et_title.text.toString(),
                    view.et_description.text.toString())
            dialogDismiss()
        }

    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(activity, theme) {
            override fun onBackPressed() {
                dialogDismiss()
            }
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

    override fun dialogDismiss() {
        INSTANCE = null
        this.dismiss()
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        val mode = if (isChecked) {CalendarMode.MONTHS} else {CalendarMode.WEEKS}

        this@AddScheduleDialog.mcv_add.state().edit().setCalendarDisplayMode(mode).commit()
    }

    companion object {
        private var INSTANCE: AddScheduleDialog? = null

        fun getInstance(): AddScheduleDialog {
            if (INSTANCE == null) {
                synchronized(AddScheduleDialog::class) {
                    INSTANCE = AddScheduleDialog()
                }
            }
            return INSTANCE!!
        }
    }
}