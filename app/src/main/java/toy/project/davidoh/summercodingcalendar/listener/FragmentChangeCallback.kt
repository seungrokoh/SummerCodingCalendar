package toy.project.davidoh.summercodingcalendar.listener

import androidx.fragment.app.Fragment

interface FragmentChangeCallback {
    fun onReplaceFragment(fragment: Fragment, tag: String? = null)
}