package Comments


import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object CommentModel: Table("comment") {

    private val user = CommentModel.varchar("user", 10)
    private val textOfComment = CommentModel.text("text")
    private val idComment = CommentModel.integer("id_comment")
    private val idFilm = CommentModel.integer("id_film")


    fun insert(commentDTO: CommentDTO){// подумать!!!!!
        val lastID = getLastId()
        println(lastID)
        transaction {
            CommentModel.insert {
                it[user]=commentDTO.nameUser
                it[textOfComment]=commentDTO.textOfComment
                it[idFilm]=commentDTO.idFilm
                it[idComment]=lastID+1
                // id проверить что с ним делать
            }
        }
    }
    fun getLastId():Int{
        var lastId:Int=0
        transaction {
            val commentModel = CommentModel.selectAll().lastOrNull()!!
//            if (commentModel[CommentModel.idComment] != null)
            lastId=commentModel[CommentModel.idComment]
        }
//        val lastId = CommentModel.select{CommentModel.idComment. }
//            .orderBy(CommentModel.idComment to SortOrder.ASC)
//            .limit(1)
//            .map { it[CommentModel.idComment] }
//            .singleOrNull()
        return lastId
    }
    fun getComments(idFilm: Int, offset:Long=0): List<CommentDTO> {
        val data = mutableListOf<CommentDTO>()
        transaction {
            val commentModel = CommentModel.select {CommentModel.idFilm eq idFilm }.limit(10, offset).toList()
//            if (commentModel.empty())
//                return@transaction null

            for (row in commentModel) {
                val comment = CommentDTO(
                    textOfComment = row[CommentModel.textOfComment],
                    nameUser = row[CommentModel.user],
                    idFilm = row[CommentModel.idFilm]
                )
                data.add(comment)
            }
        }
        return data
    }

}