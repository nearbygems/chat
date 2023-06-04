package kz.nearbygems.chat.repository.impl

import kz.nearbygems.chat.model.User
import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test

class UserRepositoryImplTest : RepositoryTest() {

    private val repository = UserRepositoryImpl(database)

    @Test
    fun `save user`() {

        val user = User("name", "password")

        //
        repository.save(user)
        //

        assertThat(database.users).containsEntry(user.name, user)
    }

    @Test
    fun `get user by username`() {

        val user = User("name", "password")
        database.users[user.name] = user

        //
        val savedUser = repository.getByUsername(user.name)
        //

        assertThat(savedUser).isNotNull
        assertThat(savedUser).isEqualTo(user)
    }

}