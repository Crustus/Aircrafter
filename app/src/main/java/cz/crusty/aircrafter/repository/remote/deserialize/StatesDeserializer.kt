package cz.crusty.aircrafter.repository.remote.deserialize

import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import cz.crusty.aircrafter.repository.remote.model.StatesResponse
import java.lang.reflect.Type


class StatesDeserializer : JsonDeserializer<StatesResponse.States> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): StatesResponse.States {

        val items: ArrayList<StatesResponse.State> = ArrayList()

        val jsonArray = json!!.asJsonArray

        for (item in jsonArray) {
            val array = item.asJsonArray
            items.add(StatesResponse.State(
                array[0].asString,
                getOrNull(array, 1)?.asString,
                array[2].asString,
                array[3].asInt,
                array[4].asInt,
                getOrNull(array, 5)?.asDouble,
                getOrNull(array, 6)?.asDouble,
                getOrNull(array, 7)?.asFloat,
                array[8].asBoolean,
                getOrNull(array, 9)?.asFloat,
                getOrNull(array, 10)?.asFloat,
                getOrNull(array, 11)?.asFloat,
                null, // not parsed now
                getOrNull(array, 13)?.asFloat,
                getOrNull(array, 14)?.asString,
                array[15].asBoolean,
                array[16].asInt
                ))
        }

        return StatesResponse.States(items)
    }

    private fun getOrNull(array: JsonArray, index: Int): JsonElement? {
        val element = array[index]
        return if (element.isJsonNull) null else element
    }
}