package github.idmeetrious.moxytestapp.data.datasource.remote

import android.util.Log
import github.idmeetrious.moxytestapp.data.mappers.DtoMapper
import github.idmeetrious.moxytestapp.data.network.AuthRequests
import github.idmeetrious.moxytestapp.domain.entities.Repository
import kotlinx.coroutines.flow.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "RemoteDataSourceImpl"
class RemoteDataSourceImpl(
    private val api: AuthRequests,
    private val mapper: DtoMapper
) : RemoteDataSource {
    override suspend fun getUserRepositories(name: String): Flow<List<Repository>> =
        flowOf(api.getUserRepositories(name))
            .map { list ->
                list.map { dto -> mapper.mapToEntity(dto) }
            }

    override fun downloadRepository(fullName: String): Call<ResponseBody> =
        api.downloadUserRepositoryZip(fullName)

//    override suspend fun downloadRepository(fullName: String): Flow<ByteArray> {
//        val call = api.downloadUserRepositoryZip(fullName)
//        var flow = flowOf(byteArrayOf())
//        call.enqueue(object : Callback<ResponseBody>{
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                if (response.isSuccessful && response.code() == 200) {
//                    Log.i(TAG, "--> onResponse: OK")
//                    response.body()?.bytes()?.let {
//                        Log.i(TAG, "--> onResponse: ${it.size}bytes")
//                        flow = flowOf(it)
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                Log.e(TAG, "--> onFailure: ${t.message}")
//            }
//        })
////        Log.i(TAG, "--> downloadRepository: ${response.}bytes")
////
////        response.let {
////            if (it.isSuccessful && it.code() == 200)
////                it.body()?.let { bytes ->
////                    Log.i(TAG, "--> downloadRepository: ${bytes.size / 1024}KB")
////                    return flowOf(bytes)
////                }
////        }
//        return flow
//    }


}