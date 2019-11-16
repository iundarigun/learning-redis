package br.com.devcave.redis.repository

import br.com.devcave.redis.domain.Programmer

interface ProgrammerRepository {
    fun setProgrammerAsString(idKey: String, programmer: String)

    fun getProgrammerAsString(idKey: String): String

    fun addToProgrammersList(programmer: Programmer)

    fun getProgrammersListMembers(): List<Programmer>

    fun getProgrammerListCount(): Long
}