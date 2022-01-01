package org.wit.lighthouse.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.wit.lighthouse.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "lighthouses.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<LighthouseModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class LighthouseJSONStore(private val context: Context) : LighthouseStore {

    var lighthouses = mutableListOf<LighthouseModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<LighthouseModel> {
        logAll()
        return lighthouses
    }

    override fun findById(id: Long): LighthouseModel? {
       val foundLighthouse: LighthouseModel? = lighthouses.find { it.id == id}
        return foundLighthouse
    }

    override fun create(lighthouse: LighthouseModel) {
        lighthouse.id = generateRandomId()
        lighthouses.add(lighthouse)
        serialize()
    }


    override fun update(lighthouse: LighthouseModel) {
        val lighthousesList = findAll() as ArrayList<LighthouseModel>
        var foundlighthouse: LighthouseModel? = lighthousesList.find { p -> p.id == lighthouse.id }
        if (foundlighthouse != null) {
            foundlighthouse.title = lighthouse.title
            foundlighthouse.description = lighthouse.description
            foundlighthouse.image = lighthouse.image
            foundlighthouse.lat = lighthouse.lat
            foundlighthouse.lng = lighthouse.lng
            foundlighthouse.zoom = lighthouse.zoom
        }
        serialize()
    }
    /*override fun delete(lighthouse: LighthouseModel){
        lighthouses.remove(lighthouse)
        serialize()
    }*/
    override fun delete(lighthouse: LighthouseModel) {
        val foundLighthouse: LighthouseModel? = lighthouses.find { it.id == lighthouse.id }
        lighthouses.remove(foundLighthouse)
        serialize() }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(lighthouses, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        lighthouses = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        lighthouses.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}

