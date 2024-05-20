package Register

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*


fun Application.configureRegisterRouting() {
    install(ContentNegotiation) {
        json()
    }
//    install(ContentNegotiation) {
//        json()
//        serialization {
//            register(ContentType.Application.Json, ByteBufferChannel::class, JsonContentTransformer())
//        }
//    }
    install(StatusPages) {
//        exception{
//            val call.respond(HttpStatusCode.InternalServerError, cause.localizedMessage)
//        }
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause" , status = HttpStatusCode.InternalServerError)
        }
    }
    routing {
        post("/register") {
            val registerController = RegisterController(call)
            registerController.registerNewUser()
//            return@post
        }

    }
}
