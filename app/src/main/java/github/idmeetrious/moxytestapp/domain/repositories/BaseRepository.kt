package github.idmeetrious.moxytestapp.domain.repositories

import github.idmeetrious.moxytestapp.domain.entities.Repository
import kotlinx.coroutines.flow.Flow

interface BaseRepository {
    suspend fun getUserRepositories(user: String): Flow<List<Repository>>
    suspend fun downloadRepository(fullName: String): String
//    suspend fun downloadRepository(fullName: String): Flow<ByteArray>
}