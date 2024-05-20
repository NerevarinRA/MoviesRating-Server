package UserRating

import UserRating.UserRatingModel.updateUserRating
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureUserRating(){

    routing {
        post("/setNewUserRating"){

            val userRatingController = UserRatingController(call)
            userRatingController.setNewRating()
        }
//        post("/updatingUserRating"){
//            val userRatingController = UserRatingController(call)
//            userRatingController.updatingRating()
//            call.respond("The rating has been updated")
//        }
    }
}