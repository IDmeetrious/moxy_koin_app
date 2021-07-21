package github.idmeetrious.moxytestapp.data.mappers

import github.idmeetrious.moxytestapp.data.network.dto.RepositoryDto
import github.idmeetrious.moxytestapp.domain.entities.Repository

class DtoMapper {
    fun mapToEntity(dto: RepositoryDto): Repository =
        Repository(
            id = dto.id,
            name = dto.name,
            fullName = dto.fullName,
            description = dto.description ?: "",
            url = dto.url
        )
}