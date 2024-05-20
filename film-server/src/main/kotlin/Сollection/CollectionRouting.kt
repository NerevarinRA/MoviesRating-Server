package Сollection

import Films.FilmModel.returnUserCollectionOfFilms
import Films.FilmsDTO
import Users.UserModel.addFilmToCollection
import Users.UserModel.delFilmFromCollection
import Users.UserModel.getUserCollection
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureCollectionRouting() {
    routing {
        get("/getCollection"){
            val userName = call.request.queryParameters["userName"]!!.toString()
            val collection = getUserCollection(userName)
            if (collection!=null) {
                val filmsDTO = returnUserCollectionOfFilms(collection)
                call.respond(HttpStatusCode.OK, filmsDTO)
            }
            else
                call.respond(HttpStatusCode.NoContent, listOf<FilmsDTO>())
        }
        post("/addFilmToCollection"){
            val userName = call.request.queryParameters["userName"]!!.toString()
            val idFilm = call.request.queryParameters["idFilm"]!!.toInt()
            addFilmToCollection(userName, idFilm)
            call.respond(HttpStatusCode.OK, "Film add to collection")
        }
        post("/delFilmFromCollection"){
            val userName = call.request.queryParameters["userName"]!!.toString()
            val idFilm = call.request.queryParameters["idFilm"]!!.toInt()
            delFilmFromCollection(userName, idFilm)
            call.respond(HttpStatusCode.OK, "Film del from collection")
        }
    }
}