package github.idmeetrious.moxytestapp.domain.usecases

import github.idmeetrious.moxytestapp.domain.repositories.BaseRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.Call

class DownloadRepositoryZipImpl(
    private val baseRepository: BaseRepository
) : DownloadRepositoryZip {
    override fun invoke(fullName: String): Call<ResponseBody> =
        baseRepository.downloadRepository(fullName)
//    override suspend fun invoke(fullName: String): Flow<ByteArray> =
//        baseRepository.downloadRepository(fullName)
}