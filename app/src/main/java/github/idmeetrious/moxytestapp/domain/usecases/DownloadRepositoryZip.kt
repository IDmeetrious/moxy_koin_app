package github.idmeetrious.moxytestapp.domain.usecases

import okhttp3.ResponseBody

interface DownloadRepositoryZip {
    suspend operator fun invoke(fullName: String): ResponseBody
}