package den.ter.recyclerviw.model

import com.github.javafaker.Faker
import java.util.*

typealias UsersListener = (users:List<User>) -> Unit

class UserService {

    private var users = mutableListOf<User>()

    private val listeners = mutableSetOf<UsersListener>()

    init {
        val faker = Faker.instance()
        IMAGES.shuffle()
        users = (1..100).map {
            User(
                id = it.toLong(),
                name = faker.name().name(),
                company = faker.company().name(),
                photo = IMAGES[it % IMAGES.size]
            )
        }.toMutableList()
    }

    fun getUsers(): List<User> {
        return users
    }

    fun deleteUser(user: User) {
        val index = users.indexOfFirst { it.id == user.id }
        if (index != -1) users.removeAt(index)
        notifyChanges()
    }

    fun moveUser(user: User, move: Int) {
        val oldIndex = users.indexOfFirst { it.id == user.id }
        if (oldIndex == -1) return
        val newIndex = oldIndex + move
        if (newIndex < 0 || newIndex >= users.size) return
        Collections.swap(users,oldIndex,newIndex)
        notifyChanges()
    }

    fun addListener(listener:UsersListener){
        listeners.add(listener)
        listener.invoke(users)
    }
    fun removeListener(listener:UsersListener){
        listeners.remove(listener)
    }

    private fun notifyChanges(){
        listeners.forEach { it.invoke(users) }
    }

    companion object {
        private val IMAGES = mutableListOf(
            "https://ru-static.z-dn.net/files/d82/b23810c70267b2222e4b50be3cb2d7d0.jpg",
            "https://steamuserimages-a.akamaihd.net/ugc/1698402776116313451/993A8BB58E84D0960A398BF731A257A4DB09F261/",
            "https://pp.userapi.com/tcjg5tNJTPwviiObZ1cqdadGI-rlp1IBQsJuHw/DWEX2gYqaMQ.jpg",
            "https://i10.fotocdn.net/s128/a33933e259527da6/public_pin_l/2912620197.jpg",
            "https://i.ytimg.com/vi/DmTZgXisqzg/maxresdefault.jpg",
            "https://r4.mt.ru/r30/photo7840/20298485049-0/jpeg/bp.jpeg"
        )
    }
}