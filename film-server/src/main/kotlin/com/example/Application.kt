package com.example

import GetFilm.configureGetCommentRouting
import Register.configureRegisterRouting
import com.example.plugins.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import org.jetbrains.exposed.sql.Database
import GetFilm.configureGetFilmRouting
import UserRating.configureUserRating
import Сollection.configureCollectionRouting
import GetFilm.configureGetRatingAndCollectionRouting
import Authorization.configureAuthorizationRouting
fun main() {
    //embeddedServer(CIO, port = 8080, host = "192.168.43.164", module = Application::module).start(wait = true)
    embeddedServer(CIO, port = 8080, host = "192.168.0.12", module = Application::module).start(wait = true)

}

fun Application.module() {


    try {
        Database.connect(
            "jdbc:postgresql://localhost:5432/CkFilmBase",
            "org.postgresql.Driver",
            "postgres",
            "123"
        )
    }
    catch (e:Exception){
        println("Database is not connection")
    }
    configureRouting()
    configureRegisterRouting()
    configureGetFilmRouting()
    configureGetCommentRouting()
    configureUserRating()
    configureCollectionRouting()
    configureGetRatingAndCollectionRouting()
    configureAuthorizationRouting()
}
