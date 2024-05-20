package Films

import org.jetbrains.exposed.sql.*

import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File
import java.util.*
import kotlin.math.roundToInt

object FilmModel: Table("film") {
    private val idFilm = FilmModel.integer("id_film")
    private val nameFilm = FilmModel.varchar("name_film", 20)
    private val rating = FilmModel.float("rating")
    private val year = FilmModel.varchar("year", 4)
    private val director = FilmModel.varchar("director", 15)
    private val styles = FilmModel.varchar("styles", 100)
    private val annotation = FilmModel.text("annotation")
    private val wallpaper = FilmModel.varchar("wallpaper", 100)
    private val numberOfRatings = FilmModel.integer("number_of_ratings")
    private val styles_array = arrayOf(FilmModel.varchar("styles_array", 100))

    fun getFilmsSortedByRatingDESC (offset: Long = 0):List<FilmsDTO>{
        var data = mutableListOf<FilmsDTO>()
        transaction {
            val filmModel = FilmModel.select(Op.TRUE)// { FilmModel.nameFilm.eq(nameFilm) }
                .orderBy(FilmModel.rating to SortOrder.DESC)
                .limit(20, offset)
                .toList()
            data = creatingList(filmModel)
        }
        return data

    }
    fun getFilmsSortedByRatingASC (offset: Long = 0):List<FilmsDTO>{
        var data = mutableListOf<FilmsDTO>()
        transaction {
            val filmModel = FilmModel.select { FilmModel.nameFilm.eq(nameFilm) }
                .orderBy(FilmModel.rating to SortOrder.ASC)
                .limit(20, offset)
                .toList()

            data = creatingList(filmModel)
        }
        return data

    }
    fun getFilmsSortedByNameASC (offset: Long = 0):List<FilmsDTO>{
        var data = mutableListOf<FilmsDTO>()
        transaction {
            val filmModel = FilmModel.select { FilmModel.nameFilm.eq(nameFilm) }
                .orderBy(FilmModel.nameFilm to SortOrder.ASC)
                .limit(20, offset)
                .toList()

            data = creatingList(filmModel)
        }
        return data

    }
    fun getFilmsSortedByNameDESC (offset: Long = 0):List<FilmsDTO>{
        var data = mutableListOf<FilmsDTO>()
        transaction {
            val filmModel = FilmModel.select { FilmModel.nameFilm.eq(nameFilm) }
                .orderBy(FilmModel.nameFilm to SortOrder.DESC)
                .limit(20, offset)
                .toList()
            data = creatingList(filmModel)
        }
        return data

    }
    fun getFilmsSortedByYearDESC (offset: Long = 0):List<FilmsDTO>{
        var data = mutableListOf<FilmsDTO>()
        transaction {
            val filmModel = FilmModel.select { FilmModel.nameFilm.eq(nameFilm) }
                .orderBy(FilmModel.year to SortOrder.DESC)
                .limit(20, offset)
                .toList()

            data = creatingList(filmModel)
        }
        return data

    }
    fun getFilmsSortedByYearASC (offset: Long = 0):List<FilmsDTO>{ // старые вперед
        var data = mutableListOf<FilmsDTO>()
        transaction {
            val filmModel = FilmModel.select { FilmModel.nameFilm.eq(nameFilm) }
                .orderBy(FilmModel.year to SortOrder.ASC)
                .limit(20, offset)
                .toList()

            data = creatingList(filmModel)
        }
        return data

    }
    fun findFilm (nameFilm: String):List<FilmsDTO>?{
        var data = mutableListOf<FilmsDTO>()
        val film = try {
            transaction {
                val filmModel = FilmModel.select {

                    FilmModel.nameFilm.like(capitalizeFirstLetter(nameFilm))
                }
                    .orderBy(FilmModel.nameFilm to SortOrder.ASC)
                    .toList()

                data = creatingList(filmModel)
            }
        }
        catch (e: Exception){
            null
        }
        if (film != null) {
            return data
        }
        return null
    }
    fun capitalizeFirstLetter(input: String): String {
        return input.substring(0, 1).uppercase() + input.substring(1)
    }
    fun findIdFilm (idFilm: Int):FilmsDTO {
        return transaction {
            val filmModel = FilmModel.select { FilmModel.idFilm.eq(idFilm) }.single()
            val imageFile = File(filmModel[FilmModel.wallpaper])
            val imageBytes = imageFile.readBytes()
            val base64Image = Base64.getEncoder().encodeToString(imageBytes)
//            val number = 5.6789
            val roundedRating = (filmModel[FilmModel.rating].toDouble() * 100.0).roundToInt() / 100.0.toFloat()
            FilmsDTO(
                idFilm = filmModel[FilmModel.idFilm],
                nameFilm = filmModel[FilmModel.nameFilm],
                rating = roundedRating,//filmModel[FilmModel.rating],
                year = filmModel[FilmModel.year],
                director = filmModel[FilmModel.director],
                styles = filmModel[FilmModel.styles],
                annotation = filmModel[FilmModel.annotation],
                wallpaper = base64Image//filmModel[FilmModel.wallpaper],
//                wallpaper_str = base64Image// filmModel[FilmModel.wallpaper]
            )
        }
    }
//    @Nullable
    fun findFilmsByStyle (style: String):List<FilmsDTO>{
        var data = mutableListOf<FilmsDTO>()// listOf<FilmDTO>()
        transaction {
            val filmModel = FilmModel.select { FilmModel.styles.like(style) }.toList() //.where(FilmModel.styles.like("%триллер%"))
            data = creatingList(filmModel)
        }
        return data
    }
    fun ratingUpdate(idFilm: Int, addRating: Float){
        var numTotalRatings: Int = 0 // количество оценивших
        var avgRating = 0f// текущий рейтинг

        transaction {
            avgRating = FilmModel.select { FilmModel.idFilm eq idFilm }
                        .map { it[FilmModel.rating] }.single()
            numTotalRatings = FilmModel.select { FilmModel.idFilm eq idFilm }
                .map { it[FilmModel.numberOfRatings]}.single()
        }

        // Обновляем общий рейтинг и количество голосов для дальнейших расчетов
        numTotalRatings += 1
        val updatedRating = (avgRating*(numTotalRatings-1)+addRating)/numTotalRatings//(numTotalRatings * avgRating + k * newRating) / (numTotalRatings + k)
        transaction {
            // Здесь выполняем запрос к базе данных для обновления рейтинга для конкретного фильма
            FilmModel.update({ FilmModel.idFilm eq idFilm }) {
                it[FilmModel.rating] = updatedRating
                it[FilmModel.numberOfRatings] = numTotalRatings
            }
        }
    }
    fun ratingReUpdate(oldUserRating:Float ,idFilm: Int, newRating: Float){
        var numTotalRatings: Int = 0 // количество оценивших
        var avgRating = 0f// текущий рейтинг
//        var oldRating = 0f// старый рейтинг
        transaction {
            avgRating = FilmModel.select { FilmModel.idFilm eq idFilm }
                .map { it[FilmModel.rating] }.single()
            numTotalRatings = FilmModel.select { FilmModel.idFilm eq idFilm }
                .map { it[FilmModel.numberOfRatings]}.single()
//            numTotalRatings = FilmModel.select { FilmModel.idFilm eq idFilm }
//                .map { it[FilmModel.numberOfRatings]}.single()
        }
        val updatedRating = (avgRating*numTotalRatings-oldUserRating+newRating)/numTotalRatings//(avgRating*(numTotalRatings-1)+addRating)/numTotalRatings//(numTotalRatings * avgRating + k * newRating) / (numTotalRatings + k)
        transaction {
            // Здесь выполняем запрос к базе данных для обновления рейтинга для конкретного фильма
            FilmModel.update({ FilmModel.idFilm eq idFilm }) {
                it[FilmModel.rating] = updatedRating
//                it[FilmModel.numberOfRatings] = numTotalRatings
            }
        }

    }
    fun returnUserCollectionOfFilms(idFilms: Array<Int>):List<FilmsDTO>{
        val data = mutableListOf<FilmsDTO>()
        transaction {
            for (id in idFilms) {
                val filmModel = FilmModel.select { FilmModel.idFilm eq id }.single()
                val imageFile = File(filmModel[FilmModel.wallpaper])
                val imageBytes = imageFile.readBytes()
                val base64Image = Base64.getEncoder().encodeToString(imageBytes)
                val roundedRating = (filmModel[FilmModel.rating].toDouble() * 100.0).roundToInt() / 100.0.toFloat()
                val film = FilmsDTO(
                    idFilm = filmModel[FilmModel.idFilm],
                    nameFilm = filmModel[FilmModel.nameFilm],
                    rating = roundedRating,
                    year = filmModel[FilmModel.year],
                    director = filmModel[FilmModel.director],
                    styles = filmModel[FilmModel.styles],
                    annotation = filmModel[FilmModel.annotation],
                    wallpaper = base64Image
                )
                data.add(film)
            }
        }
        return data
    }

    private fun creatingList(filmModel:List<ResultRow>):MutableList<FilmsDTO>{
        val data = mutableListOf<FilmsDTO>()// listOf<FilmDTO>()
        for (row in filmModel) {
            val imageFile = File(row[FilmModel.wallpaper])
            val imageBytes = imageFile.readBytes()
            val base64Image = Base64.getEncoder().encodeToString(imageBytes)
            val roundedRating = (row[FilmModel.rating].toDouble() * 100.0).roundToInt() / 100.0.toFloat()
            val film = FilmsDTO(
                idFilm = row[FilmModel.idFilm],
                nameFilm = row[FilmModel.nameFilm],
                rating = roundedRating,
                year = row[FilmModel.year],
                director = row[FilmModel.director],
                styles = row[FilmModel.styles],
                annotation = row[FilmModel.annotation],
                wallpaper = base64Image
            )
            data.add(film)
        }
        return data
    }

//    fun findFilmsByStyleALL ():List<FilmsDTO>{ // работает, но проблема с разным количеством параметров
////        return listOf(
////        if (style == null)
//        val st1:String ="% драма%"//styles[0]
//        val st2:String = "% боевик%"//styles[1]
//        val tmp:String = "%$st1%$st2%"//"ALL(ARRAY['%$st1%','%$st2%'])"
////        tmp = "драма"
//        val data = mutableListOf<FilmsDTO>()// listOf<FilmDTO>()
//        transaction {
//            val filmModel = FilmModel.select { FilmModel.styles.like(st1) or FilmModel.styles.like(st2)}.toList() //.where(FilmModel.styles.like("%триллер%"))
////            val filmModel2 = FilmModel.select(FilmModel.styles.like(st1))
////            println(filmModel2)
//            for (row in filmModel) {
//                val film = FilmsDTO(
//                    nameFilm = row[FilmModel.nameFilm],
//                    rating = row[FilmModel.rating],
//                    styles = row[FilmModel.styles],
//                    wallpaper = row[FilmModel.wallpaper]
//                )
//                data.add(film)
//            }
//        }
//        return data
//    }
//fun findFilmsByStyleALL ():List<FilmsDTO>{ // не работающий на массивах строк
////        return listOf(
////        if (style == null)
//    val st1:String ="% драма%"//styles[0]
//    val st2:String = "% боевик%"//styles[1]
//    val tmp:String = "%$st1%$st2%"//"ALL(ARRAY['%$st1%','%$st2%'])"
////        tmp = "драма"
//    val data = mutableListOf<FilmsDTO>()// listOf<FilmDTO>()
//    transaction {
//        val filmModel = FilmModel.select{
//            FilmModel.styles_array like any(arrayOf("%боевик%", "%драма%"))}.single()
////            Film(
////                it[FilmModel.idFilm],
////                it[FilmModel.nameFilm],
////                it[FilmModel.wallpaper],
////                it[FilmModel.rating],
////                it[FilmModel.year],
////                it[FilmModel.director],
////                it[FilmModel.styles,
//////                it[FilmModel.poster],
////                it[FilmModel.annotation]
////            )
////        }
//
////        val filmModel = FilmModel.select { FilmModel.styles.like(st1) or FilmModel.styles.like(st2)}.toList() //.where(FilmModel.styles.like("%триллер%"))
////        val filmModel2 = FilmModel.select(FilmModel.styles.like(st1))
////        println(filmModel2)
////        for (row in filmModel) {
////            val film = FilmsDTO(
////                nameFilm = row[FilmModel.nameFilm],
////                rating = row[FilmModel.rating],
////                styles = row[FilmModel.styles],
////                wallpaper = row[FilmModel.wallpaper]
////            )
////            data.add(film)
////        }
//    }
//    return data
//}
}