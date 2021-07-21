package github.idmeetrious.moxytestapp.presentation.views

import github.idmeetrious.moxytestapp.domain.entities.Repository
import moxy.MvpView
import moxy.viewstate.strategy.StateStrategyType
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution

@AddToEndSingle
interface RepositoryListView: MvpView {

    fun loading()
    fun success()
    fun data(list: List<Repository>)
    fun error(msg: String)

    @OneExecution
    fun saveTo(data: ByteArray, name: String)
}