package github.idmeetrious.moxytestapp.domain.usecases

import github.idmeetrious.moxytestapp.domain.repositories.BaseRepository

class DownloadRepositoryZipImpl(
    private val baseRepository: BaseRepository
) : DownloadRepositoryZip {
    override suspend operator fun invoke(fullName: String): String =
        baseRepository.downloadRepository(fullName)
//    override suspend fun invoke(fullName: String): Flow<ByteArray> =
//        baseRepository.downloadRepository(fullName)
}