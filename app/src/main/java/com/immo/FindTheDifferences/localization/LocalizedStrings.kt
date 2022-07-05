package com.immo.FindTheDifferences.localization

import java.util.Locale


val RUSSIAN = Locale("ru")
val BELARUSIAN = Locale("be")

val setSupportedLocalesNow = registerSupportedLocales(RUSSIAN, BELARUSIAN)

fun getTranslatable(
    ruText: String,
    belText: String
): Localization.() -> String =
    Translatable(
        "",
        hashMapOf(
            RUSSIAN to ruText,
            BELARUSIAN to belText
        )
    )



