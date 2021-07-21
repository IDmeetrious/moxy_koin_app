package github.idmeetrious.moxytestapp.domain.entities

data class Repository(
    val id: Long,
    val name: String,
    val fullName: String,
    val description: String,
    val url: String
)
