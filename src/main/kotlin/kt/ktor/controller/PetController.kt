package kt.ktor.controller

import kotlinx.coroutines.flow.Flow
import kt.ktor.domain.Pet
import kt.ktor.domain.PetType
import kt.ktor.domain.TrackerType
import kt.ktor.service.PetService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/pets")
class PetController(
    val petService: PetService
) {
    @PostMapping
    suspend fun addPet(@RequestBody pet: Pet): Pet {
        return petService.savePet(pet)
    }

    @GetMapping
    fun findAll(): Flow<Pet> {
        return petService.findAllPets()
    }

    @GetMapping("{id}")
    suspend fun findById(@PathVariable id: Long): Pet? {
        return petService.findPetById(id)
    }

    @GetMapping("/outside-zone")
    fun findOutsideZone(): Flow<Pet> {
        return petService.findPetsOutsideOfZone()
    }

    @GetMapping("/outside-zone/grouped")
    suspend fun findPetsOutsideZoneGrouped(): Map<PetType, Map<TrackerType, Int>> {
        return petService.getPetsOutsideOfZoneAndGrouped()
    }
}