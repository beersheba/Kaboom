package me.rankov.kaboom.di

import dagger.Component
import me.rankov.kaboom.country.list.CountriesListActivity

@Component(modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: CountriesListActivity)

}