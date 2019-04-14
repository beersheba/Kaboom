package me.rankov.kaboom.login

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseUser
import me.rankov.kaboom.R
import me.rankov.kaboom.prefs

class LoginNameFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_name, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = view.findViewById(R.id.button_next) as Button
        val editName = view.findViewById(R.id.edit_name) as EditText
        val user = arguments?.get("user") as FirebaseUser
        val initName = user.email?.substringBefore("@") as String
        editName.setText(initName)

        button.setOnClickListener {
            prefs.nickname = initName
            findNavController().navigate(R.id.actionNameToCountry)
        }
    }
}
