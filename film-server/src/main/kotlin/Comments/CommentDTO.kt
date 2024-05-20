package Comments

import kotlinx.serialization.Serializable

@Serializable
data class CommentDTO(
    val textOfComment: String,
    val nameUser: String,
    val idFilm: Int
)
