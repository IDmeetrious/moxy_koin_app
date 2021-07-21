package github.idmeetrious.moxytestapp.data.network

import github.idmeetrious.moxytestapp.data.network.dto.RepositoryDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface AuthRequests {
    @GET("/users/{username}/repos")
    suspend fun getUserRepositories(
        @Path("username") userName: String
    ): List<RepositoryDto>

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/repos/{fullname}/zipball/")
    fun downloadUserRepositoryZip(
        @Path("fullname") fullName: String
    ): Call<ResponseBody>
}