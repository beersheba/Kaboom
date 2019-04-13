package me.rankov.kaboom.login

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import me.rankov.kaboom.R
import org.jetbrains.anko.support.v4.toast

class LoginCountryFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = view.findViewById<Button>(R.id.country_next_button)
        button?.setOnClickListener {
            toast("Going to main screen") //To change body of created functions use File | Settings | File Templates.
        }
    }
}
