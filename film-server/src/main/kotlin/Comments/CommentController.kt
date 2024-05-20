package Comments

import Comments.CommentModel.getLastId
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class CommentController(private val call: ApplicationCall) {
    private val idFilm = call.request.queryParameters["idFilm"]!!.toInt()
    private val textOfComment= call.request.queryParameters["textOfComment"]!!.toString()
    private val userName = call.request.queryParameters["nameUser"]!!.toString()
    suspend fun addNewComment(){
//        val newComment = call.receive<CommentDTO>()
//        val lastId:Int =
//        val newID:Int = getLastId()+1
//        if (lastId != null)
//        newID=lastId+1
        CommentModel.insert(
            CommentDTO(
//                idComment = newID,
                idFilm = idFilm,
                textOfComment = textOfComment,
                nameUser = userName
            )
        )
        call.respond(HttpStatusCode.OK, "New comment added")
    }

}