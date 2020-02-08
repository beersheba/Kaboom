package me.rankov.kaboom.di

import android.app.Activity
import dagger.Module
import dagger.Provides
import me.rankov.kaboom.ScreenNavigator

@Module
class ActivityModule(val activity: Activity) {

    @Provides
    fun activity(): Activity {
        return activity
    }

    @Provides
    fun screenNavigator(activity: Activity): ScreenNavigator {
        return ScreenNavigator(activity)
    }

}