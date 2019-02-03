package me.rankov.kaboom.login

import me.rankov.kaboom.BasePresenter

interface LoginContract {
    interface View {
        fun selectCountry()
        fun login()
    }

    interface Presenter : BasePresenter {
        fun onCountrySelect()
        fun onLogin()
    }

}