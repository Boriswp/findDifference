package com.immo.findTheDifferences.remote


data class FilesData(
    var level_id: Int = 0,
    var picture_background: String = "",
    var picture_foreground: String = "",
    var padding_top: Int = 445,
    var padding_left: Int = 235,
    var object_width: Int = 80,
    var object_height: Int = 45
)
