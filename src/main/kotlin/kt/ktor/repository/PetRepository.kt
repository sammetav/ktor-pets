package kt.ktor.repository

import kotlinx.coroutines.flow.Flow
import kt.ktor.domain.Pet
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PetRepository : CoroutineCrudRepository<Pet, Long> {
    fun findAllByInZoneFalse(): Flow<Pet>
}