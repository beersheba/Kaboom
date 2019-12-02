package me.rankov.kaboom.country.details

data class CountryAction(var userid: String = "1",
                         var action: String = "0",
                         var timestamp: String = System.currentTimeMillis().toString(),
                         var source: String = "0,0",
                         var target: String = "100,100",
                         var targetid: String = "1",
                         var actionitemid: String = "1",
                         var impact: String = "1")