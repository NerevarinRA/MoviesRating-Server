package Comments

import kotlinx.serialization.Serializable

@Serializable
data class Comment(
    val idComment:Int,
    val textOfComment: String,
    val nameUser: String,
    val idFilm: Int
)
