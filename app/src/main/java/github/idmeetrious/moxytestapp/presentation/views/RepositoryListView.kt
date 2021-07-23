package github.idmeetrious.moxytestapp.presentation.views

import github.idmeetrious.moxytestapp.domain.entities.Repository
import moxy.MvpView
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution

@AddToEndSingle
interface RepositoryListView: MvpView {

    // Web requests
    fun loading()
    fun success()
    fun data(list: List<Repository>)
    fun error(msg: String)

    // Downloading
    @OneExecution
    fun saveFileTo(file: ByteArray, fileName: String)
    fun progress()
    fun complete()
    fun failure(msg: String)
}