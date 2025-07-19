package kt.ktor.domain

import org.springframework.data.relational.core.mapping.Table

enum class TrackerType { SMALL, MEDIUM, BIG }
enum class PetType { CAT, DOG }

@Table(name = "pets")
data class Pet(
    val id: Long? = null,

    val trackerType: TrackerType,

    val petType: PetType,

    val ownerId: Int,

    val inZone: Boolean,

    val lostTracker: Boolean? = false // only for cats
) {
    init {
        if (petType == PetType.DOG) {
            require(lostTracker == false) { "Dogs cannot have lostTracker" }
        }

        if (petType == PetType.CAT) {
            require(
                trackerType in setOf(
                    TrackerType.SMALL,
                    TrackerType.BIG
                )
            ) { "Cat trackers can only be in Small and big" }
        }
    }
}
