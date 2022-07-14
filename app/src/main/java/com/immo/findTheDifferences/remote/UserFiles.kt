package com.immo.findTheDifferences.remote

data class UserFiles(
    var filesData: List<FilesData>,
    var indexes: List<Int>,
    var currLvl: Int
)
