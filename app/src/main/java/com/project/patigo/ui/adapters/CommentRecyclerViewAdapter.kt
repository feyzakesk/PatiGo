package com.project.patigo.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.patigo.data.entity.Comment
import com.project.patigo.databinding.SingleCommentLayoutBinding

class CommentRecyclerViewAdapter(
    private val mContext: Context,
    private val commentList: List<Comment>,
) : RecyclerView.Adapter<CommentRecyclerViewAdapter.CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = SingleCommentLayoutBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = commentList[position]
        holder.bind(comment)
    }

    override fun getItemCount(): Int = commentList.size

    inner class CommentViewHolder(var view: SingleCommentLayoutBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(comment: Comment) {

            view.commentContent.text = comment.comment
            view.commentDate.text = comment.date
            view.commentRate.text = comment.vote.toString()
            view.commentName.text = buildString {
                append(comment.userName)
                append(" ")
                append(comment.userSurname)
            }

        }
    }
}