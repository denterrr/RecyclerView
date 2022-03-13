package den.ter.recyclerviw

import android.app.Application
import den.ter.recyclerviw.model.UserService

class App : Application() {
    val userService = UserService()
}