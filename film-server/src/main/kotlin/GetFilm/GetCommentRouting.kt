package GetFilm

import Comments.CommentController
import Comments.CommentModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureGetCommentRouting() {
    routing {
        get("/getComments"){
            val idFilm = call.request.queryParameters["idFilm"]
            val offset = call.request.queryParameters["offset"]!!.toLong()
            if (idFilm==null)
                call.respond(HttpStatusCode.BadRequest, "not idFilm")
            val comments = CommentModel.getComments(idFilm?.toIntOrNull()!!, offset)
            if (comments.isEmpty())
                call.respond(HttpStatusCode.NoContent, comments) //"There are no comments"
            else
                call.respond(HttpStatusCode.OK, comments)
        }
        post("/addNewComment"){
            val commentController = CommentController(call)
            commentController.addNewComment()
        }

    }
}