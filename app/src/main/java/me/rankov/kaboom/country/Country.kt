package me.rankov.kaboom.country

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Country(val name: String,
                   val flag: String,
                   val population: Long,
                   val latlng: List<Double>) : Parcelable
