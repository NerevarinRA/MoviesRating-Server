package Register

import Users.UserDTO
import Users.UserModel
import Users.UserModel.fetchEmail
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class RegisterController(private val call:ApplicationCall) {

    fun validateEmail(email: String): Boolean {
        val emailRegex = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")
        return emailRegex.matches(email)
    }
    suspend fun registerNewUser(){
        try{
            val userName = call.request.queryParameters["name"]!!
            val email = call.request.queryParameters["email"]!!
            val passwd = call.request.queryParameters["passwd"]!!
        }
        catch (e: Exception){
            println("No parametr")
        }

        val registerReceiveRemote = RegisterReceiveRemote(
            userName = call.request.queryParameters["name"]!!,
            email = call.request.queryParameters["email"]!!,
            passwd = call.request.queryParameters["passwd"]!!
        )

        if (fetchEmail(registerReceiveRemote.email)){
            if ((registerReceiveRemote.userName.length<=10)) {
                if (UserModel.fetchUser(registerReceiveRemote.userName)) {
                    call.respond(
                        HttpStatusCode.NoContent,
                        "Такой пользователь уже существует, придумайте что-нибудь еще ;)"
                    )
                } else {
                    if (!validateEmail(registerReceiveRemote.email))
                        call.respond(HttpStatusCode.NoContent, "Почта введена неправильно")
                    else {
                        UserModel.insert(
                            UserDTO(
                                nameUser = registerReceiveRemote.userName,
                                email = registerReceiveRemote.email,
                                passwd = registerReceiveRemote.passwd//,
                            )
                        )
                        call.respond(HttpStatusCode.OK, "Вы успешно зарегистрировались")
                    }
                }
            } else {
                call.respond(HttpStatusCode.NoContent, "Слишком длинный nik, придумайте что-нибудь покороче")
            }
        }
        else
            call.respond(HttpStatusCode.NoContent, "К этой почте уже привязан аккаунт")
    }


}