package br.com.devcave.redis.service

import br.com.devcave.redis.domain.InstanceDefinition
import br.com.devcave.redis.repository.ApplicationRepository
import br.com.devcave.redis.repository.InstanceRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class InstanceService(
    private val instanceRepository: InstanceRepository,
    private val applicationRepository: ApplicationRepository,
    @Value("\${instances.ttl}")
    private val timeoutInMinutes: Long
) {

    fun registerInstance(instanceDefinition: InstanceDefinition) {
        instanceRepository.saveInstance(instanceDefinition, timeoutInMinutes)
        applicationRepository.addInstanceToApplication(
            instanceDefinition.application,
            instanceDefinition.uuid
        )
    }

    fun deleteInstance(instanceDefinition: InstanceDefinition) {
        instanceRepository.removeInstance(instanceDefinition)
        applicationRepository.removeInstanceFromApplication(
            instanceDefinition.application,
            instanceDefinition.uuid
        )
    }
}