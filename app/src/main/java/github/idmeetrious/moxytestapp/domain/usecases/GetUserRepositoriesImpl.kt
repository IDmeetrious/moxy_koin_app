package github.idmeetrious.moxytestapp.domain.usecases

import github.idmeetrious.moxytestapp.domain.entities.Repository
import github.idmeetrious.moxytestapp.domain.repositories.BaseRepository
import kotlinx.coroutines.flow.Flow

class GetUserRepositoriesImpl(
    private val repository: BaseRepository
) : GetUserRepositories {
    override suspend fun invoke(user: String): Flow<List<Repository>> =
        repository.getUserRepositories(user)
}