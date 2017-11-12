package tsybl.mindgames.entities


data class Task(val name: String,
                val isDoubleExp: Boolean,
                val bestScore: Int,
                private val taskType: Int) : TaskType {


    override fun getListItemType(): Int {
        return taskType
    }
}