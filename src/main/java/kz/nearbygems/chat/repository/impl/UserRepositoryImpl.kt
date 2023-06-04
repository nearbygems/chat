package kz.nearbygems.chat.repository.impl

import kz.nearbygems.chat.db.Database
import kz.nearbygems.chat.model.User
import kz.nearbygems.chat.repository.UserRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(private val database: Database) : UserRepository {

    override fun save(user: User) {
        database.users[user.name] = user
    }

    override fun getByUsername(username: String): User? = database.users[username]

}