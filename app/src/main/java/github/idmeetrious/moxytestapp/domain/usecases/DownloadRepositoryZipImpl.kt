package github.idmeetrious.moxytestapp.domain.usecases

import github.idmeetrious.moxytestapp.domain.repositories.BaseRepository
import okhttp3.ResponseBody

class DownloadRepositoryZipImpl(
    private val baseRepository: BaseRepository
) : DownloadRepositoryZip {
    override suspend operator fun invoke(fullName: String): ResponseBody =
        baseRepository.downloadRepository(fullName)
}