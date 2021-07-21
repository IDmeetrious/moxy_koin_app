package github.idmeetrious.moxytestapp.data.repositories

import github.idmeetrious.moxytestapp.data.datasource.remote.RemoteDataSource
import github.idmeetrious.moxytestapp.domain.entities.Repository
import github.idmeetrious.moxytestapp.domain.repositories.BaseRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.Call

class BaseRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : BaseRepository {
    override suspend fun getUserRepositories(user: String): Flow<List<Repository>> =
        remoteDataSource.getUserRepositories(user)

    override fun downloadRepository(fullName: String): Call<ResponseBody> =
        remoteDataSource.downloadRepository(fullName)

//override suspend fun downloadRepository(fullName: String): Flow<ByteArray> =
//        remoteDataSource.downloadRepository(fullName)

}