package Users

import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
//    val idUser:Int,
    val nameUser:String,
    val email:String,
//    val avatar:
    val passwd: String,
//    val collection: Array<Int>
)