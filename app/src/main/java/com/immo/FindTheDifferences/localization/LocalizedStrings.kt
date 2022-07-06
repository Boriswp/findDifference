package com.immo.FindTheDifferences.localization

import java.util.Locale


val RUSSIAN = Locale("ru")
val ENGLISH = Locale("en")
val TURKEY = Locale("tr")
val PORTUGUESE = Locale("pt")

val setSupportedLocalesNow = registerSupportedLocales(RUSSIAN, ENGLISH, TURKEY, PORTUGUESE)

fun getTranslatable(
    ruText: String,
    enText: String,
    tuText: String,
    poText: String,
): Localization.() -> String =
    Translatable(
        enText,
        hashMapOf(
            RUSSIAN to ruText,
            ENGLISH to enText,
            TURKEY to tuText,
            PORTUGUESE to poText
        )
    )



