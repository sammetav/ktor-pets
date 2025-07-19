package kt.ktor.domain

import org.springframework.data.relational.core.mapping.Table


enum class TrackerType { SMALL, MEDIUM, BIG }
enum class PetType { CAT, DOG }

@Table(name = "pet")
data class Pet(
    val id: Long,

    val trackerType: TrackerType,

    val petType: PetType,

    val ownerId: Int,

    val inZone: Boolean,

    val lostTracker: Boolean, //only for cats
)