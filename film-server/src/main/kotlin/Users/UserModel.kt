package Users

import UserRating.UserRatingModel
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.jetbrains.exposed.sql.*
import javax.management.Query.and

object UserModel: Table("users") {
//    private val idUser = UserModel.integer("id_user")
    private val nameUserBD = UserModel.varchar("user_name", 10)
    private val emailBD = UserModel.varchar("email", 100)
    private val passwd = UserModel.varchar("passwd", 512)
//    private val collection = UserModel.
    private val collection = UserModel.registerColumn<Array<Int>>("collection", IntegerArrayType())

//    private val avatar = UserModel.blob("avatar")


//    fun getIntegerArrayDataFromDatabase(): List<List<Int>> {
//        return transaction {
//            val resultSet = org.jetbrains.exposed.sql.transactions.transaction {
//                exec("SELECT user_name, collection\n" +
//                        "\tFROM public.users\n" +
//                        "\twhere collection && array[2]::int[];")
//            }
//            val data = mutableListOf<List<Int>>()
//            resultSet.forEach {
//                val arrayValues = it[0] as? Array<Int>
//                if (arrayValues != null) {
//                    data.add(arrayValues.toList())
//                }
//            }
//            return@transaction data
//        }
//    }
    fun insert(userDTO: UserDTO){
        transaction {
            UserModel.insert {
                it[nameUserBD]=userDTO.nameUser
                it[emailBD]=userDTO.email
                it[passwd]=userDTO.passwd
//                it[avatar]=userDTO.avatar
            }
        }
    }

    fun fetchUser(nameUser:String):Boolean {
            return transaction {
                UserModel.select {
                    (UserModel.nameUserBD eq nameUser)
                }
                    .map { }
                    .isNotEmpty()
            }
    }
    fun fetchEmail(email:String):Boolean {
        return transaction {
            UserModel.select {
                (UserModel.emailBD eq email)
            }
                .map { }
                .isEmpty()
        }
    }
    fun getUserCollection(userName: String):Array<Int>? {
        return try {
            transaction {
                UserModel.select { UserModel.nameUserBD.eq(userName) }.map { it[UserModel.collection] }.single()
            }
        }
        catch (e: Exception){
            null
        }
    }
    fun addFilmToCollection(userName:String, idFilm: Int){
        transaction {
            val collection = UserModel.select { UserModel.nameUserBD.eq(userName) }.map { it[UserModel.collection] }.firstOrNull()// .single()

            if (collection != null) {
                val newCollection = collection.plus(idFilm)
                UserModel.update({ UserModel.nameUserBD eq userName }) {
                    it[UserModel.collection] = newCollection
                }
            }
            else {
                val newCollection: Array<Int> = arrayOf(idFilm)
                UserModel.update({UserModel.nameUserBD eq userName} ){
                    it[UserModel.collection] = newCollection
                }
            }

        }
    }
    fun delFilmFromCollection(userName:String, idFilm: Int){
        transaction {
            val collection = UserModel.select { UserModel.nameUserBD.eq(userName) }.map { it[UserModel.collection] }.single()
            val newCollection = collection.filter { it != idFilm }.toTypedArray()
            UserModel.update({UserModel.nameUserBD eq userName} ){
                it[UserModel.collection] = newCollection
            }
        }
    }
    fun filmInCollection(idFilm: Int, userName: String):Boolean{
        var filmInCollection = false
        transaction {
            val collection = UserModel.select { UserModel.nameUserBD.eq(userName) }.map { it[UserModel.collection] }?.single()
            if (collection == null){
                filmInCollection = false
            }
            else {
                filmInCollection = collection.contains(idFilm)
            }
        }
        return filmInCollection
    }
    fun findUser(email: String):Boolean {
        return transaction {
            UserModel.select {
                UserModel.emailBD eq email
            }
                .map { it[UserModel.emailBD] }
                .isNotEmpty()
        }
    }
    fun passwordComparison(email: String, passwd:String):String?{
        return transaction {
            UserModel.select { (UserModel.emailBD eq email) and
                (UserModel.passwd eq passwd)}
                .map { it[UserModel.nameUserBD] }.firstOrNull()
        }
    }
}