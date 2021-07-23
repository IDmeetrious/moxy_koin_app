package github.idmeetrious.moxytestapp.data.network

import github.idmeetrious.moxytestapp.data.network.dto.RepositoryDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Streaming

interface AuthRequests {
    @GET("/users/{username}/repos")
    suspend fun getUserRepositories(
        @Path("username") userName: String
    ): List<RepositoryDto>

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/repos/{fullname}/zipball/")
    suspend fun downloadUserRepositoryZip(
        @Path("fullname") fullName: String
    ): String
}