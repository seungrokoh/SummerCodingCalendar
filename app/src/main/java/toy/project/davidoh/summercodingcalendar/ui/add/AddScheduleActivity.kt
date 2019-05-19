package toy.project.davidoh.summercodingcalendar.ui.add

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import toy.project.davidoh.summercodingcalendar.R
import toy.project.davidoh.summercodingcalendar.util.replaceFragment

class AddScheduleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_schedule)
        replaceFragment(R.id.fl_container, AddScheduleFragment.getInstance())
    }
}
