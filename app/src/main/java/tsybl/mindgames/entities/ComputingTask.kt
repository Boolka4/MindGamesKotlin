package tsybl.mindgames.entities

import tsybl.mindgames.data.ComputingType

class ComputingTask(var question: String,
                    var isRight: Boolean) {
    lateinit var type: ComputingType
     var first: Int = 0
     var second: Int = 0
     var answer: Int = 0

}