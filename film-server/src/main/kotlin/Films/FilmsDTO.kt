package Films

import kotlinx.serialization.Serializable

@Serializable
data class FilmsDTO(
    val idFilm: Int,
    val nameFilm: String,//===
    val rating: Float,//===
    val director:String,
    val styles: String,//===
    val annotation: String,
    val year: String,
//    val poster: image
//    @Serializable(with = ExposedBlobSerializer::class)
    val wallpaper: String//ExposedBlob
)
