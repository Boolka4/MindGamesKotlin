package tsybl.mindgames.presentation.taskList

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_calculation.view.*
import tsybl.mindgames.R
import tsybl.mindgames.entities.Task
import tsybl.mindgames.entities.TaskType

class TaskListAdapter(private val context: Context,
                      private val gameClick: (Int) -> Unit,
                      private val moreClick: (Int) -> Unit) : RecyclerView.Adapter<TaskListAdapter.TaskHolder>() {

    private lateinit var tasks: List<Task>


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TaskHolder {
        val view: View = when (viewType) {
            TaskType.colorRush -> LayoutInflater.from(parent!!.context).inflate(R.layout.item_color_rush, parent, false)
            TaskType.computing -> LayoutInflater.from(parent!!.context).inflate(R.layout.item_calculation, parent, false)
            TaskType.reactionTap -> LayoutInflater.from(parent!!.context).inflate(R.layout.item_reaction_tap, parent, false)
            TaskType.schulteNumbers -> LayoutInflater.from(parent!!.context).inflate(R.layout.item_schulte_numbers, parent, false)
            else -> LayoutInflater.from(parent!!.context).inflate(R.layout.item_memory, parent, false)
        }
        return TaskHolder(view)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.bindTask(tasks[position], position, context, gameClick, moreClick)
    }

    override fun getItemCount() = tasks.size
    override fun getItemViewType(position: Int) = tasks[position].getListItemType()

    fun setData(data: List<Task>) {
        tasks = data;
        notifyDataSetChanged()
    }

    class TaskHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindTask(task: Task, position: Int, context: Context,
                     gameClick: (Int) -> Unit,
                     moreClick: (Int) -> Unit) {
            itemView.tvBestScore.text = context.getString(R.string.best, task.bestScore)
            itemView.tvTaskName.text = task.name
            itemView.rlContainer.setOnClickListener { gameClick(position) }
            itemView.ivInformation.setOnClickListener { moreClick(position) }
        }
    }
}