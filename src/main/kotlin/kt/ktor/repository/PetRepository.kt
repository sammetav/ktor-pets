package kt.ktor.repository

import kotlinx.coroutines.flow.Flow
import kt.ktor.domain.Pet
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface PetRepository : CoroutineCrudRepository<Pet, Long> {
    fun findAllByInZoneFalse(): Flow<Pet>
}
