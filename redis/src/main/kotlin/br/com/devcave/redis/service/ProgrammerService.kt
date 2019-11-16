package br.com.devcave.redis.service

import br.com.devcave.redis.domain.Programmer
import br.com.devcave.redis.repository.ProgrammerRepository
import org.springframework.stereotype.Service

@Service
class ProgrammerService(private val programmerRepository: ProgrammerRepository) {

    fun setProgrammerAsString(idKey:String, programmer:String){
        programmerRepository.setProgrammerAsString(idKey, programmer)
    }

    fun getProgrammerAsString(idKey:String): String{
        return programmerRepository.getProgrammerAsString(idKey)
    }

    fun addToProgrammersList(programmer: Programmer){
         programmerRepository.addToProgrammersList(programmer)
    }

    fun getProgrammersListMembers(): List<Programmer> {
        return programmerRepository.getProgrammersListMembers()
    }

    fun getProgrammerListCount(): Long{
        return programmerRepository.getProgrammerListCount()
    }

}