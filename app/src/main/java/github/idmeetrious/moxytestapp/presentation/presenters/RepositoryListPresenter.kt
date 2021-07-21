package github.idmeetrious.moxytestapp.presentation.presenters

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import github.idmeetrious.moxytestapp.data.network.AuthRequests
import github.idmeetrious.moxytestapp.domain.entities.Repository
import github.idmeetrious.moxytestapp.domain.usecases.DownloadRepositoryZip
import github.idmeetrious.moxytestapp.domain.usecases.GetUserRepositories
import github.idmeetrious.moxytestapp.presentation.views.RepositoryListView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL

private const val TAG = "RepositoryListPresent"

class RepositoryListPresenter : MvpPresenter<RepositoryListView>(), KoinComponent {
    private val getUserReposUseCase: GetUserRepositories by inject()
    private val downloadRepositoryZipUseCase: DownloadRepositoryZip by inject()
    private val context: Context by inject()

    private var _repositories: MutableStateFlow<List<Repository>> = MutableStateFlow(emptyList())
    val repositories get() = _repositories

    lateinit var retrofit: Retrofit
    lateinit var api: AuthRequests

    init {
        retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(AuthRequests::class.java)
    }

    fun getUserRepos(user: String) {
        viewState.loading()
        presenterScope.launch(Dispatchers.IO) {
            getUserReposUseCase.invoke(user)
                .onCompletion { e ->
                    if (e == null) viewState.success()
                    else e.message?.let { viewState.error(it) }
                }
                .flowOn(Dispatchers.Main)
                .collect {
                    if (!it.isNullOrEmpty()) {
                        _repositories.value = it
                        viewState.data(it)
                    } else {
                        Log.e(TAG, "--> getUserRepos: Empty")
                        presenterScope.launch(Dispatchers.Main) {
                            viewState.error("Can't find any repositories")
                        }
                    }
                }
        }
    }

    fun downloadRepo(item: Repository) {
        Log.i(TAG, "--> downloadRepo: ${item.fullName}")
        val isExist = item.fullName.trim().isNotEmpty()
        if (isExist) {
            viewState.loading()
            try {
//                presenterScope.launch(Dispatchers.IO) {
//                    val response = api.downloadUserRepositoryZip(item.fullName)
//                        .execute()
//                    if (response.isSuccessful && response.code() == 200){
//                        presenterScope.launch(Dispatchers.Main) { viewState.success() }
//                        Log.i(TAG, "--> downloadRepo: ${response.body()?.contentLength()}bytes")
//                    }
//                }
                presenterScope.launch(Dispatchers.IO) {
                    val url = "https://github.com/${item.fullName}/archive/master.zip"
                    val input = URL(url).openStream()
                    var buffer = byteArrayOf()
//                    saveOnStorage(item.name, input.readBytes())
                    try {
                        buffer = input.readBytes()
                    } catch (e: IOException) {
                        Log.e(TAG, "--> downloadRepo: ${e.message}")
                    } finally {
                        if (input.available() == 0) {
                            Log.i(TAG, "--> downloadRepo: ${buffer.size}bytes")
                            presenterScope.launch(Dispatchers.Main) {
                                viewState.saveTo(buffer, item.name)
                            }.invokeOnCompletion {
                                if (it == null) {
                                    presenterScope.launch(Dispatchers.Main) {
                                        viewState.success()
                                    }
                                }
                            }
                            input.close()
                        }
                    }

                }

            } catch (e: IOException) {
                Log.e(TAG, "--> downloadRepo: ${e.message}")
            }
        }
    }
//    fun downloadRepo(item: Repository) {
//        Log.i(TAG, "--> downloadRepo: ${item.fullName}")
//        val isExist = item.fullName.trim().isNotEmpty()
//        if (isExist) {
//            viewState.loading()
//            presenterScope.launch(Dispatchers.IO) {
//                downloadRepositoryZipUseCase.invoke(item.fullName)
//                    .catch {
//                        it.message?.let { msg -> viewState.error(msg) }
//                    }
//                    .onCompletion { if (it == null) viewState.success() }
//                    .flowOn(Dispatchers.Main)
//                    .collect {
//                        Log.i(TAG, "--> downloadRepo: ${it.size}bytes")
////                        saveOnStorage(item.name, it)
//                    }
//            }
//        }
//    }

    private fun saveOnStorage(name: String, data: ByteArray) {
        try {
            val storage: File? = getStorageDir()
            val out = FileOutputStream("$storage/${name}.zip")
                .let {
                    it.runCatching { write(data) }
                        .onSuccess {
                            presenterScope.launch(Dispatchers.Main) {
                                viewState.success()
                            }
                        }
                        .onFailure { e ->
                            e.message?.let { msg ->
                                presenterScope.launch(Dispatchers.Main) {
                                    viewState.error(msg)
                                }
                            }
                        }
                }
        } catch (e: IOException) {
            presenterScope.launch(Dispatchers.Main) {
                e.message?.let { msg ->
                    viewState.error(msg)
                }
            }
        }
    }

    private fun getStorageDir(): File? {
        if (Build.VERSION.SDK_INT < 30)
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        else
            return context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
    }

    fun pendingSaveTo(uri: Uri, data: ByteArray) {
        Log.i(TAG, "--> pendingSaveTo: ${uri.encodedPath}")
        val out = context.contentResolver.openOutputStream(uri)
        out?.let {
            it.runCatching { write(data) }
                .onSuccess {
                    presenterScope.launch(Dispatchers.Main) {
                        viewState.success()
                    }
                    out.close()
                }
                .onFailure { e ->
                    e.message?.let { msg ->
                        presenterScope.launch(Dispatchers.Main) {
                            viewState.error(msg)
                        }
                    }
                    out.close()
                }
        }
    }

}