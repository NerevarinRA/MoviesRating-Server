package UserRating

import Films.FilmModel.ratingReUpdate
import UserRating.UserRatingModel.setNewUserRating
import UserRating.UserRatingModel.updateUserRating
import io.ktor.server.application.*
import io.ktor.server.response.*

class UserRatingController(private val call: ApplicationCall){
    private val newUserRating = UserRatingDTO(
        idFilm = call.request.queryParameters["idFilm"]!!.toInt(),
        userName = call.request.queryParameters["userName"]!!.toString(),
        userRating = call.request.queryParameters["userRating"]!!.toFloat()
    )
    suspend fun setNewRating(){
//        val newUserRating = call.receive<UserRatingDTO>()
        setNewUserRating(newUserRating)
//        ratingUpdate(newUserRating.idFilm, newUserRating.userRating)
        call.respond("Successfully!!")
//
//        ratingReUpdate(
//            oldUserRating,
//            newUserRating.idFilm,
//            newUserRating.userRating)
    }
    suspend fun updatingRating(){
//        val updateUserRating = call.receive<UserRatingDTO>()
        val oldUserRating = updateUserRating(newUserRating)
        ratingReUpdate(
            oldUserRating,
            newUserRating.idFilm,
            newUserRating.userRating)
    }
}