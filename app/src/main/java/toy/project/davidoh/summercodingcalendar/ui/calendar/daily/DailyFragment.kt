package toy.project.davidoh.summercodingcalendar.ui.calendar.daily

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_calendar.view.*
import toy.project.davidoh.summercodingcalendar.R

class DailyFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
        = inflater.inflate(R.layout.fragment_daily, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {

        private var INSTANCE: DailyFragment? = null

        fun getInstance() : DailyFragment {
            if (INSTANCE == null) {
                INSTANCE = DailyFragment()
            }
            return INSTANCE!!
        }
    }
}