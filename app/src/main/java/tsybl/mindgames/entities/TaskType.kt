package tsybl.mindgames.entities

interface TaskType {
    companion object {
        const val colorRush: Int = 1
        const val computing: Int = 2
        const val reactionTap: Int = 3
        const val schulteNumbers: Int = 4
        const val memory: Int = 5
    }

    fun getListItemType(): Int
}