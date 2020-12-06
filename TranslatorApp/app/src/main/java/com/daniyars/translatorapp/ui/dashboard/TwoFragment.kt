package com.daniyars.translatorapp.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.daniyars.translatorapp.R
import com.daniyars.translatorapp.local.LocalPreferences
import com.daniyars.translatorapp.models.User
import com.daniyars.translatorapp.ui.adapters.UserListAdapter
import kotlinx.android.synthetic.main.fragment_two.*

class TwoFragment : Fragment(R.layout.fragment_two) {
    private var listOfUsers = mutableListOf<User>()
    private var userListAdapter: UserListAdapter? = null
    private lateinit var sharedPreferences: LocalPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = LocalPreferences(context)
        /*val userList = sharedPreferences.getUserList()
        listOfUsers.addAll(userList)*/

        initializeComponents()
    }

    private fun initializeComponents() {
        userListAdapter =
            activity?.applicationContext?.let { UserListAdapter(listOfUsers) }
        val userListLayoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        usersRecycler.layoutManager = userListLayoutManager
        usersRecycler.adapter = userListAdapter

        val users = listOf(
            User(
                "Daniyar Slamkul",
                "dslamkul14@gmail.com",
                "99 мин",
                "2201",
                "https://lh3.googleusercontent.com/a-/AOh14Gg6aLbSAOGuAURSWoua4-aSnYZ38NhF329xf9hhiA=s96-c"
            ),
            User(
                "Denzel Vashington",
                "dslamkul14@gmail.com",
                "97 мин",
                "5771",
                "https://lh3.googleusercontent.com/a-/AOh14GjRiwDi9QsKKNfSGLhnT6a1A_qR6WWFZVjwGdZYXk0=s96-c"
            ),
            User(
                "Daniyar Slamkul",
                "dslamkul14@gmail.com",
                "12 мин",
                "155",
                "https://lh4.googleusercontent.com/-Jvpt9MelOlM/AAAAAAAAAAI/AAAAAAAAAAA/AMZuucn3kwljfMib_lTue0DphB5yrCp0xQ/s96-c/photo.jpg"
            ),
            User(
                "Daniyar Slamkul",
                "dslamkul14@gmail.com",
                "99 мин",
                "2201",
                "https://lh3.googleusercontent.com/a-/AOh14Gg6aLbSAOGuAURSWoua4-aSnYZ38NhF329xf9hhiA=s96-c"
            ),
            User(
                "Denzel Vashington",
                "dslamkul14@gmail.com",
                "97 мин",
                "5771",
                "https://lh3.googleusercontent.com/a-/AOh14GjRiwDi9QsKKNfSGLhnT6a1A_qR6WWFZVjwGdZYXk0=s96-c"
            ),
            User(
                "Daniyar Slamkul",
                "dslamkul14@gmail.com",
                "12 мин",
                "155",
                "https://lh4.googleusercontent.com/-Jvpt9MelOlM/AAAAAAAAAAI/AAAAAAAAAAA/AMZuucn3kwljfMib_lTue0DphB5yrCp0xQ/s96-c/photo.jpg"
            )
        )

        listOfUsers.addAll(users)

        userListAdapter?.listOfUsers = listOfUsers
        userListAdapter?.notifyDataSetChanged()
    }
}