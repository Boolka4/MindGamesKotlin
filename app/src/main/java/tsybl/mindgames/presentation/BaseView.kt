package tsybl.mindgames.presentation

interface BaseView<in T> {

    fun setPresenter(presenter: T)

}