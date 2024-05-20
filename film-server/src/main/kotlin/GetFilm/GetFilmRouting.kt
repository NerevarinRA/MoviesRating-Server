package GetFilm

import Films.FilmModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.nio.charset.Charset

fun Application.configureGetFilmRouting() {
    routing {
//        val queryParameters: ContentDisposition.Parameters  = request.queryParameters
//        val param1: String? = request.queryParameters["param1"]

        get("/getAllFilmsRatingDESC") {
            val offset = call.request.queryParameters["offset"]!!.toLong()
            val filmDTO = FilmModel.getFilmsSortedByRatingDESC(offset)
            call.respond(HttpStatusCode.OK, filmDTO)
        }
        get("/getAllFilmsRatingASC") {
            val offset = call.request.queryParameters["offset"]!!.toLong()
            val filmDTO = FilmModel.getFilmsSortedByRatingASC(offset)
            call.respond(HttpStatusCode.OK, filmDTO)
        }
        get("/getAllFilmsNameDESC") {
            val offset = call.request.queryParameters["offset"]!!.toLong()
            val filmDTO = FilmModel.getFilmsSortedByNameDESC(offset)
            call.respond(HttpStatusCode.OK, filmDTO)
        }
        get("/getAllFilmsNameASC") {
            val offset = call.request.queryParameters["offset"]!!.toLong()
            val filmDTO = FilmModel.getFilmsSortedByNameASC(offset)
            call.respond(HttpStatusCode.OK, filmDTO)
        }
        get("/getAllFilmsYearDESC") {
            val offset = call.request.queryParameters["offset"]!!.toLong()
            val filmDTO = FilmModel.getFilmsSortedByYearDESC(offset)
            call.respond(HttpStatusCode.OK, filmDTO)
        }
        get("/getAllFilmsYearASC") {
            val offset = call.request.queryParameters["offset"]!!.toLong()
            val filmDTO = FilmModel.getFilmsSortedByYearASC(offset)
            call.respond(HttpStatusCode.OK, filmDTO)
        }
        get("/getFilm") {
            val idFilm = call.request.queryParameters["idFilm"]
//            val idFilm = 1
            if (idFilm==null)
                call.respond(HttpStatusCode.BadRequest, "not idFilm")
            val filmDTO = FilmModel.findIdFilm(idFilm?.toIntOrNull()!!)
//            val filmDTO = FilmModel.findIdFilm(idFilm)
            call.respond(HttpStatusCode.OK, filmDTO)
        }
        get("/findFilmByStyle") {
//            val test: String = "%детектив%"
            val style = call.request.queryParameters["style"]
//            println(style)
            if (style == null) {
                call.respond(HttpStatusCode.NoContent, "The style was not found")
            }
            val styles = style!!.split(",")
            var resStyles ="%"
            for (i in styles)
            {
                resStyles = resStyles.plus("$i%")
            }
            println(resStyles)
            val filmDTO = FilmModel.findFilmsByStyle(resStyles)
            call.respond(HttpStatusCode.OK, filmDTO)
        }
//        get("/findFilmByStyleAll"){
//            val filmDTO = FilmModel.findFilmsByStyleALL()
//            call.respond(filmDTO)
//        }
        get("/findFilmByName"){
            val name = call.request.queryParameters["name"]
            println(name)
            if (name == null) {
                call.respond(HttpStatusCode.NotFound, "The name was not found")
            }
            else {
                var newName = String(
                    name.toString().toByteArray(
                        Charset.forName("utf-8")
                    ), Charset.forName("utf-8")
                )
                println(newName)
                newName = newName.plus("%")
                val filmDTO = FilmModel.findFilm(newName)
                if (filmDTO!!.isEmpty())
                    call.respond(HttpStatusCode.NoContent, "The film was not found")
                else
                    call.respond(HttpStatusCode.OK, filmDTO)
            }
        }

    }
}