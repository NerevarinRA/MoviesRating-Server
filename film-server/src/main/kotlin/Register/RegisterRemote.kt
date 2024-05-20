package Register

import kotlinx.serialization.Serializable

@Serializable
    data class RegisterReceiveRemote(
    val userName: String,
    val email: String,
    val passwd: String
//    val avatar: image
)
