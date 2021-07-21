package github.idmeetrious.moxytestapp.domain.usecases

import github.idmeetrious.moxytestapp.domain.entities.Repository
import kotlinx.coroutines.flow.Flow

interface GetUserRepositories {
    suspend fun invoke(user: String): Flow<List<Repository>>
}