package github.idmeetrious.moxytestapp.domain.usecases

interface DownloadRepositoryZip {
    suspend operator fun invoke(fullName: String): String
//    suspend fun invoke(fullName: String): Flow<ByteArray>
}