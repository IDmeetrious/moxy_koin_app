package github.idmeetrious.moxytestapp.data.datasource.remote

import github.idmeetrious.moxytestapp.domain.entities.Repository
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    suspend fun getUserRepositories(name: String): Flow<List<Repository>>

    //    suspend fun downloadRepository(fullName: String): Flow<ByteArray>
    suspend fun downloadRepository(fullName: String): String
}