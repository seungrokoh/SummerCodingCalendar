package toy.project.davidoh.summercodingcalendar.`interface`

import androidx.fragment.app.Fragment

interface FragmentChangeCallback {
    fun onReplaceFragment(fragment: Fragment, tag: String? = null)
}