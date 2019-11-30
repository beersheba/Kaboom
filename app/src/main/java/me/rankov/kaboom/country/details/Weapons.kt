package me.rankov.kaboom.country.details

import me.rankov.kaboom.country.Weapon

object Weapons {
    private val all = mutableListOf<Weapon>()
    private val weapons = mutableListOf<Weapon>()
    private val healers = mutableListOf<Weapon>()

    fun init(allWeapons: List<Weapon>) {
        all.clear()
        weapons.clear()
        healers.clear()
        all.addAll(allWeapons)
        all.forEach {
            when (it.polarity) {
                "0" -> weapons.add(it)
                "1" -> healers.add(it)
            }
        }
    }

    fun getAll(): List<Weapon> {
        return all
    }

    fun getWeapons(): List<Weapon> {
        return weapons
    }

    fun getHealers(): List<Weapon> {
        return healers
    }
}