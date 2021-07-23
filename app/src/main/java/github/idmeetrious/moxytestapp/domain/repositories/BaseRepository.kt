package github.idmeetrious.moxytestapp.domain.repositories

import github.idmeetrious.moxytestapp.domain.entities.Repository
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody

interface BaseRepository {
    suspend fun getUserRepositories(user: String): Flow<List<Repository>>
    suspend fun downloadRepository(fullName: String): ResponseBody
}