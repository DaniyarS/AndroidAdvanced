package com.daniyars.translatorapp.ui.mainNavFragments

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.daniyars.translatorapp.R
import com.daniyars.translatorapp.local.LocalPreferences
import com.daniyars.translatorapp.models.User
import com.daniyars.translatorapp.ui.adapters.ViewPagerAdapter
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.user_list_items.nameTextView
import java.lang.Math.abs
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap

private const val GOOGLE_SIGN_CODE = 100

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {
    private var firebaseAuth: FirebaseAuth? = null
    private val database = Firebase.database
    private val fireBaseDatabase = FirebaseDatabase.getInstance()
    private lateinit var sharedPreferences: LocalPreferences

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = LocalPreferences(context)

        if (sharedPreferences.getLogState() == true) {

            val totalSpentTime = "${getTotalTime()} мин"
            sharedPreferences.setTotalTime(totalSpentTime)

            val imageUri = Uri.parse(sharedPreferences.getUserAvatar())
            Glide.with(this).load(imageUri).into(avatarImageView)
            avatarImageView.clipToOutline = true
            avatarImageView.isVisible = true
            nameTextView.text = sharedPreferences.getUserName()
            emailTextView.text = sharedPreferences.getUserEmail()

            val newUser = User(
                sharedPreferences.getUserName().toString(),
                sharedPreferences.getUserEmail().toString(),
                sharedPreferences.getTotalTime().toString(),
                sharedPreferences.getTotalWords().toString(),
                sharedPreferences.getUserAvatar().toString()
            )

            writeToFBDB(newUser, sharedPreferences.getUID().toString())
            getUserList()

            val adapter = ViewPagerAdapter(fragmentManager)
            viewPager.adapter = adapter
            tabLayout.setupWithViewPager(viewPager)
        } else {
            signIn()
        }
    }

    private fun signIn() {
        firebaseAuth = FirebaseAuth.getInstance()

        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(this.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient =
            activity?.applicationContext?.let { GoogleSignIn.getClient(it, options) }

        val signInIntent = googleSignInClient?.signInIntent

        startActivityForResult(signInIntent, GOOGLE_SIGN_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                firebaseAuth?.signInWithCredential(credential)
                    ?.addOnCompleteListener { authResult ->
                        if (authResult.isSuccessful) {
                            firebaseAuth?.currentUser?.let { user ->
                                user.getIdToken(true).addOnCompleteListener {
                                    val start = Calendar.getInstance().timeInMillis
                                    val totalSpentTime = "${getTotalTime()} мин"
                                    sharedPreferences.setLogState(true)
                                    sharedPreferences.setTotalTime(totalSpentTime)
                                    sharedPreferences.setTotalStartTime("$start")

                                    user.displayName?.let { sharedPreferences.setUserName(it) }
                                    user.email?.let { sharedPreferences.setUserEmail(it) }
                                    user.photoUrl?.let { sharedPreferences.setUserAvatar(it.toString()) }

                                    val newUser = User(
                                        user.displayName.toString(),
                                        user.email.toString(),
                                        sharedPreferences.getTotalTime().toString(),
                                        sharedPreferences.getTotalWords().toString(),
                                        user.photoUrl.toString()
                                    )
                                    writeToFBDB(newUser, user.uid)
                                    readFBDB(user.uid)
                                    getUserList()
                                    sharedPreferences.setUID(user.uid)
                                }
                            }

                            val adapter = ViewPagerAdapter(fragmentManager)
                            viewPager.adapter = adapter
                            tabLayout.setupWithViewPager(viewPager)
                        } else {
                            Log.e("sign", "sign failed")
                        }
                    }
            } catch (e: ApiException) {
                e.printStackTrace()
            }
        }
    }

    private fun writeToFBDB(user: User, uid: String) {
        val dbRef = fireBaseDatabase.getReference("users")
        dbRef.child(uid).setValue(user)
    }

    private fun readFBDB(uid: String) {
        val reference = database.getReference("users/$uid")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.d("database error", error.message)
            }

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userData = dataSnapshot.getValue<HashMap<String, String>>()
                val name = userData?.get("name")
                val email = userData?.get("email")
                val words = userData?.get("word")
                val time = userData?.get("time")
                val imgURL = userData?.get("imgURI")

                context?.let { Glide.with(it).load(imgURL).into(avatarImageView) }
                avatarImageView.clipToOutline = true
                avatarImageView.isVisible = true
                nameTextView?.text = name
                emailTextView?.text = email
                sharedPreferences.setTotalWords(words.toString())
                sharedPreferences.setTotalTime(time.toString())
            }
        })
    }

    private fun getUserList() {
        val usersReference = database.getReference("users")
        usersReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Log.d("database error", error.message)
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val userList
//                        = dataSnapshot.getValue<List<HashMap<String, HashMap<String, String>>>>()
//
//                val convertedUser = userList?.map {
//                    return@map UserConverter().convert(it)
//                }
//
//                if (convertedUser != null) {
//                    sharedPreferences.setUserList(convertedUser)
//                }
            }
        })
    }

    private fun getTotalTime(): Long? {
        val start = sharedPreferences.getTotalStartTime()?.toLong()
        val end = sharedPreferences.getTotalEndTime()?.toLong()
        val totalTime = start?.let { end?.minus(it) }
        return totalTime?.let {
            TimeUnit.MILLISECONDS.toMinutes(
                abs(it)
            )
        }
    }
}