package me.rankov.kaboom.country.details

import me.rankov.kaboom.country.Weapon

object Weapons {
    private val all = mutableListOf<Weapon>()
    private val weapons = mutableListOf<Weapon>()
    private val cures = mutableListOf<Weapon>()

    fun init(allWeapons: List<Weapon>) {
        all.clear()
        weapons.clear()
        cures.clear()
        all.addAll(allWeapons)
        all.forEach {
            when (it.polarity) {
                "0" -> weapons.add(it)
                "1" -> cures.add(it)
            }
        }
    }

    fun getAll(): List<Weapon> {
        return all
    }

    fun getWeapons(): List<Weapon> {
        return weapons
    }

    fun getCures(): List<Weapon> {
        return cures
    }

    fun getWeaponsNames(): List<String> {
        return getNames(getWeapons())
    }

    fun getCuresNames(): List<String> {
        return getNames(getCures())
    }

    private fun getNames(weapons: List<Weapon>): List<String> {
        val titles = mutableListOf<String>()
        weapons.forEach {
            titles.add(it.name)
        }
        return titles
    }
}