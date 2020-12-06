package com.daniyars.translatorapp.ui.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.daniyars.translatorapp.R
import com.daniyars.translatorapp.models.User
import kotlinx.android.synthetic.main.user_list_items.view.*

class UserListAdapter(var listOfUsers: MutableList<User>) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
        return ViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return listOfUsers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOfUsers[position], position)
    }

    inner class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(
        R.layout.user_list_items, parent, false)) {

        private val userNumber = itemView.userNumber
        private val userImage = itemView.userImage
        private val userName = itemView.nameTextView
        private val userWords = itemView.totalWordsTextView
        private val userTime = itemView.totalTimeSpentTextView

        fun bind(user: User, position: Int) {
            userNumber.text = (position + 1).toString()

            val imageUri = Uri.parse(user.imgURI)
            Glide.with(context).load(imageUri).into(userImage)

            userName.text = user.name
            userWords.text = user.word
            userTime.text = user.time
        }
    }
}