package github.idmeetrious.moxytestapp.domain.repositories

import github.idmeetrious.moxytestapp.domain.entities.Repository
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.Call

interface BaseRepository {
    suspend fun getUserRepositories(user: String): Flow<List<Repository>>
    fun downloadRepository(fullName: String): Call<ResponseBody>
//    suspend fun downloadRepository(fullName: String): Flow<ByteArray>
}