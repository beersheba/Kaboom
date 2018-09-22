package me.rankov.hth.country

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Country(val name: String, val flag: String, val population: Long) : Parcelable
