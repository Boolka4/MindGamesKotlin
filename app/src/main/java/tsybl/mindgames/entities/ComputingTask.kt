package tsybl.mindgames.entities

data class ComputingTask(var question: String,
                         var isRight: Boolean,
                         var first: Int,
                         var second: Int,
                         var answer: Int,
                         var position: Int,
                         var changed: String)
