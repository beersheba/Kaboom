package me.rankov.kaboom.login

class LoginInteractor {

    interface OnLoginFinishedListener {
        fun onSuccess()
    }

    fun login(listener: OnLoginFinishedListener) {

    }
}