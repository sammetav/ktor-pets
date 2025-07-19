package kt.ktor

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kt.ktor.domain.Pet
import kt.ktor.domain.PetType
import kt.ktor.domain.TrackerType
import kt.ktor.repository.PetRepository
import kt.ktor.service.PetService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class PetServiceTest : BaseIntegrationTest() {

    @Autowired
    lateinit var petService: PetService

    @Autowired
    lateinit var petRepository: PetRepository

    @BeforeEach
    fun setup() = runBlocking {
        petRepository.deleteAll()

        val pets = listOf(
            Pet(null, TrackerType.SMALL, PetType.CAT, 1, inZone = false, lostTracker = true),
            Pet(null, TrackerType.SMALL, PetType.CAT, 1, inZone = true, lostTracker = true),
            Pet(null, TrackerType.MEDIUM, PetType.DOG, 1, inZone = false),
            Pet(null, TrackerType.SMALL, PetType.CAT, 1, inZone = true, lostTracker = true)
        )
        pets.forEach { petRepository.save(it) }
    }

    @Test
    fun `get all pets`() = runTest {
        Assertions.assertEquals(petService.findAllPets().toList().size, 4)
    }

    @Test
    fun `get pets outside of zone`() = runTest {
        Assertions.assertEquals(petService.findPetsOutsideOfZone().toList().size, 2)
    }

    @Test
    fun `get pets outside of zone grouped`() = runTest {
        Assertions.assertEquals(
            petService.getPetsOutsideOfZoneAndGrouped().filter { it.key == PetType.DOG }.values.size, 1
        )
    }
}
