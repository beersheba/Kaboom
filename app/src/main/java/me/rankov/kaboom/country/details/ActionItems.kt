package me.rankov.kaboom.country.details

import me.rankov.kaboom.country.ActionItem

object ActionItems {
    private val all = mutableListOf<ActionItem>()
    private val weapons = mutableListOf<ActionItem>()
    private val cures = mutableListOf<ActionItem>()

    fun init(items: List<ActionItem>) {
        all.clear()
        weapons.clear()
        cures.clear()
        all.addAll(items)
        all.forEach {
            when (it.polarity) {
                "0" -> weapons.add(it)
                "1" -> cures.add(it)
            }
        }
    }

    fun getAll(): List<ActionItem> {
        return all
    }

    fun getWeapons(): List<ActionItem> {
        return weapons
    }

    fun getCures(): List<ActionItem> {
        return cures
    }

    fun getWeaponsNames(): List<String> {
        return getNames(getWeapons())
    }

    fun getCuresNames(): List<String> {
        return getNames(getCures())
    }

    private fun getNames(items: List<ActionItem>): List<String> {
        val titles = mutableListOf<String>()
        items.forEach {
            titles.add(it.name)
        }
        return titles
    }
}