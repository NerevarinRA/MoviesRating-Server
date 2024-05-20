package GetFilm

import UserRating.UserRatingModel.getUserRating
import Users.UserModel.filmInCollection
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

fun Application.configureGetRatingAndCollectionRouting() {
    routing {
        get("/getRatingAndCollection"){
            val idFilm = call.request.queryParameters["idFilm"]!!.toInt()
            val userName = call.request.queryParameters["userName"]!!
            val userRatingCollection = UserRatingCollection(
                filmInCollection(idFilm, userName),//filmInCollection,
                getUserRating(idFilm, userName)//currentUserRating
            )
            call.respond(HttpStatusCode.OK, userRatingCollection)
        }
    }
}
@Serializable
data class UserRatingCollection(
    val filmInCollection :Boolean,
    val currentUserRating: Float
)