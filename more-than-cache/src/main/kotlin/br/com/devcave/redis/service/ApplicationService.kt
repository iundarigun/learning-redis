package br.com.devcave.redis.service

import br.com.devcave.redis.domain.InstanceDefinition
import br.com.devcave.redis.repository.ApplicationRepository
import br.com.devcave.redis.repository.InstanceRepository
import org.springframework.stereotype.Service

@Service
class ApplicationService(
    private val instanceRepository: InstanceRepository,
    private val applicationRepository: ApplicationRepository
) {
    fun findByApplicationId(applicationId: String): Set<InstanceDefinition> {
        val instances = applicationRepository.findInstancesByApplicationId(applicationId)
        return instances.mapNotNull { instance ->
            getInstance(applicationId, instance)
        }.toSet()
    }

    fun findByApplicationIdByLua(applicationId: String): Set<InstanceDefinition> {
        return applicationRepository.findInstancesByApplicationIdLua(applicationId).mapNotNull {
            instanceRepository.getInstance(applicationId, it)
        }.toSet()
    }

    private fun getInstance(applicationId: String, instance: String): InstanceDefinition? {
        val instanceDefinition = instanceRepository.getInstance(applicationId, instance)
        if (instanceDefinition == null) {
            applicationRepository.removeInstanceFromApplication(applicationId, instance)
        }
        return instanceDefinition
    }
}