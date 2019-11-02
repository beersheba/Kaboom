package me.rankov.kaboom.country.details

data class CountryAction(var userId: String = "",
                         var action: String = "",
                         var timestamp: String = "",
                         var source: String = "",
                         var target: String = "",
                         var targetId: String = "",
                         var weaponId: String = "",
                         var impact: String = "")