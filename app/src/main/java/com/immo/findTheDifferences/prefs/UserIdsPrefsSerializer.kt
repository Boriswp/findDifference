package com.immo.findTheDifferences.prefs

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Serializable
class UserIdsPrefs(
    val ids: List<Int> = emptyList()
)

object UserIdsPrefsSerializer : Serializer<UserIdsPrefs> {
    override val defaultValue = UserIdsPrefs()

    override suspend fun readFrom(input: InputStream): UserIdsPrefs {
        try {
            return Json.decodeFromString(
                UserIdsPrefs.serializer(), input.readBytes().decodeToString()
            )
        } catch (serialization: SerializationException) {
            throw CorruptionException("Unable to read UserPrefs", serialization)
        }
    }

    override suspend fun writeTo(t: UserIdsPrefs, output: OutputStream) {
        output.write(Json.encodeToString(UserIdsPrefs.serializer(), t).encodeToByteArray())
    }
}