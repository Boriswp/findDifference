package com.immo.findTheDifferences.localization

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

val main_title = getTranslatable(
    "Мозголомка, тренировка внимания",
    "Brain Game, Attention Training",
    "Brain Game, Attention Training",
    "Brain Game, Attention Training"
)

val main_play_button = getTranslatable("Начать", "PLAY", "Başlamak", "Começar")

val dialog_win = getTranslatable(
    "Молодец! Давай дальше!",
    "Well done! Move on!",
    "Aferin! Devam edelim!",
    "Bem feito! Ir em frente!"
)

val game_lvl_finished = getTranslatable("угадано", "guessed ", "'i tahmin etti", "adivinhou")

val game_lvl_of = getTranslatable("из", "out of", "'den", "de")

val game_end = getTranslatable(
    "Обновление будет доступно через неделю",
    "The update will be available in a week",
    "Güncelleme bir hafta içinde kullanıma sunulacak ",
    "A atualização estará disponível em uma semana"
)

val like_app = getTranslatable(
    "Нравится приложение? Оцени его ",
    "Like the app? rate it! ",
    "Siz uygulamayı beğendiniz mi?",
    "Siz uygulamayı beğendiniz mi?"
)

