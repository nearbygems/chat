package kz.nearbygems.chat.repository.impl

import kz.nearbygems.chat.model.User
import kz.nearbygems.chat.repository.UserRepository
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap

@Repository
class UserRepositoryImpl : UserRepository {

    private val users = ConcurrentHashMap<String, User>()

    override fun save(user: User) {
        users[user.name] = user
    }

    override fun getByUsername(username: String): User? = users[username]

}