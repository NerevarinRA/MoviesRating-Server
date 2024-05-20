package UserRating

import Films.FilmModel
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object UserRatingModel: Table("user_rating") {
    private val userName = UserRatingModel.varchar("user_name",10)
    private val idFilm = UserRatingModel.integer("id_film")
    private val userRating = UserRatingModel.float("user_rating")

    fun setNewUserRating(userRatingDTO: UserRatingDTO){
        transaction {
            val notUserRating = UserRatingModel.select {
                (UserRatingModel.userName eq userRatingDTO.userName) and
                        (UserRatingModel.idFilm eq userRatingDTO.idFilm)
            }.map {  }.isEmpty()
            if (notUserRating) {
                UserRatingModel.insert {
                    it[userName] = userRatingDTO.userName
                    it[idFilm] = userRatingDTO.idFilm
                    it[userRating] = userRatingDTO.userRating
                }
                FilmModel.ratingUpdate(userRatingDTO.idFilm, userRatingDTO.userRating)
            }
            else{ // дописать изменение рейтинга пользователем
                val oldUserRating = UserRatingModel.updateUserRating(userRatingDTO)
                FilmModel.ratingReUpdate(
                    oldUserRating,
                    userRatingDTO.idFilm,
                    userRatingDTO.userRating
                )
            }
        }
    }
    fun getUserRating(idFilm: Int, userName: String):Float{
        var userIsNotRate: Boolean = false
        var currentUserRating: Float = 0f
        transaction {
            userIsNotRate = UserRatingModel.select {
                (UserRatingModel.userName eq userName ) and
                        (UserRatingModel.idFilm eq idFilm) }
                .map { it[UserRatingModel.userRating] }.isEmpty()

            if (!userIsNotRate)
                currentUserRating = UserRatingModel
                    .select {
                        (UserRatingModel.userName eq userName ) and
                        (UserRatingModel.idFilm eq idFilm)
                    }
                    .map { it[UserRatingModel.userRating] }
                    .single()
        }
        return currentUserRating

    }
    fun updateUserRating(userRatingDTO: UserRatingDTO):Float{
        val oldUserRating = transaction {
            UserRatingModel.select { (UserRatingModel.userName eq userRatingDTO.userName) and
                    (UserRatingModel.idFilm eq userRatingDTO.idFilm)  }
                .map { it[UserRatingModel.userRating] }.single()
        }
        transaction {
            UserRatingModel.update({
                (UserRatingModel.userName eq userRatingDTO.userName) and
                (UserRatingModel.idFilm eq userRatingDTO.idFilm) }){
                it[UserRatingModel.userRating] = userRatingDTO.userRating
            }
        }
        return oldUserRating
    }
}