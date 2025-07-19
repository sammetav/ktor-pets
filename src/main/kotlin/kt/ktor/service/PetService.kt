package kt.ktor.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kt.ktor.domain.Pet
import kt.ktor.domain.PetType
import kt.ktor.domain.TrackerType
import kt.ktor.repository.PetRepository
import org.springframework.stereotype.Service

@Service
class PetService(
    private val petRepository: PetRepository
) {
    suspend fun savePet(pet: Pet): Pet {
        return petRepository.save(pet)
    }

    suspend fun findPetById(id: Long): Pet? {
        return petRepository.findById(id)
    }

    fun findAllPets(): Flow<Pet> {
        return petRepository.findAll()
    }

    fun findPetsOutsideOfZone(): Flow<Pet> {
        return petRepository.findAllByInZoneFalse()
    }

    suspend fun getPetsOutsideOfZoneAndGrouped(): Map<PetType, Map<TrackerType, Int>> {
        val pets = petRepository.findAllByInZoneFalse().toList()
        return pets.groupBy { it.petType }
            .mapValues { (_, petsOfType) ->
                petsOfType.groupingBy { it.trackerType }.eachCount()
            }
    }
}
