package github.idmeetrious.moxytestapp.domain.usecases

import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.Call

interface DownloadRepositoryZip {
    fun invoke(fullName: String): Call<ResponseBody>
//    suspend fun invoke(fullName: String): Flow<ByteArray>
}