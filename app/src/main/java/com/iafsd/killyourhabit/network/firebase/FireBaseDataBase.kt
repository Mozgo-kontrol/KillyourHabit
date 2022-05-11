package com.iafsd.killyourhabit.network.firebase


import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.iafsd.killyourhabit.data.User
import com.iafsd.killyourhabit.tools.Tools
import io.reactivex.Completable
import io.reactivex.Single
import java.lang.reflect.Type
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FireBaseDataSource @Inject constructor() {

    private val TAG: String = FireBaseDataSource::class.java.simpleName
    private var _auth : FirebaseAuth = Firebase.auth
    private var _currentUser: FirebaseUser? = _auth.currentUser
    private var _databaseRefer: DatabaseReference =
        Firebase.database.reference.child(NodeNames.USERS)

    fun createUserWithEmailAndPassword(email: String, password: String): Single<String> {
        return Single.create { emitter ->
            var result = ""
            _auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        if (_auth.currentUser != null)
                            updateUserProfileInfo(
                                _auth.currentUser!!,
                                Tools.splitEmailGetFirstPart(email),
                                null
                            )
                        emitter.onSuccess(_auth.currentUser?.uid.toString())
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.wtf(TAG, "createUserWithEmail:failure", task.exception)
                        task.exception?.let { emitter.onError(it)}
                    }
                }
        }

    }

    fun signInWithEmailAndPassword(email: String, password: String): Single<FirebaseUser> {
        return Single.create { emitter ->
            _auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information

                        Log.d(TAG, "signInWithEmail:success")
                        _auth.currentUser?.let { emitter.onSuccess(it) }

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.wtf(TAG, "signInWithEmail:failure", task.exception)
                        task.exception?.let { emitter.onError(it) }
                    }
                }
        }
    }


    fun updateUserProfileInfo(fireBaseUser: FirebaseUser, name: String, url: Uri?) {
        //A Object to make an request for firebase

        val request = UserProfileChangeRequest.Builder()
            .setDisplayName(name)
            .setPhotoUri(url)
            .build()
        //update user name and photo
        fireBaseUser.updateProfile(request).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.w(TAG, "Profile updated success")
                writeNewUserToDataBase(fireBaseUser)
            } else {
                Log.e(TAG, "Profile updated success", task.exception)
            }
        }
    }

    private fun writeNewUserToDataBase(fireBaseUser: FirebaseUser) {

        val user = User(
            fireBaseUser.uid,
            fireBaseUser.displayName.toString(),
            fireBaseUser.email.toString(),
            Date().time,
            Date().time,
            false
        )
        _databaseRefer
            .child(fireBaseUser.uid)
            .setValue(user)
            .addOnCompleteListener {
                Log.e(TAG, "write new user data to database: success")
            }.addOnFailureListener {
                Log.e(TAG, "Profile updated success", it.cause)
            }
    }

    fun getUserById(): Single<User?> {
        val userId = _currentUser?.uid ?: "userIsNotRegistered"
        return Single.create { emitter ->
            _databaseRefer.child(userId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        val id = dataSnapshot.child(NodeNames.USERID).value ?: "Notdefined"
                        val nickname = dataSnapshot.child(NodeNames.NICKNAME).value ?: "Not defined"
                        val email = dataSnapshot.child(NodeNames.EMAIL).value ?: "Not defined"
                        val registerDate =
                            dataSnapshot.child(NodeNames.REGISTERDATE).value ?: 1020303003L
                        val birthDayDate =
                            dataSnapshot.child(NodeNames.BIRTHDAYDATE).value ?: 1020303003L
                        val isEmailValidated : Any =
                            dataSnapshot.child(NodeNames.ISEMAILVALIDATED).value ?: false

                        val gson = Gson()
                        val collectionType: Type = object : TypeToken<User>() {}.type
                      //  val s: User = gson.fromJson(dataSnapshot, collectionType)
                        val s = User(id as String, nickname as String, email as String,
                            registerDate as Long, birthDayDate as Long, isEmailValidated as Boolean
                        )
                        Log.e(TAG, "getUserById : $s")
                        emitter.onSuccess(s)
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.e(TAG, "getUserById Error: ${databaseError.message}")
                        emitter.onError(databaseError.toException())
                    }
                })
        }
    }


    fun signOut(): Completable {
        return Completable.create { emitter ->
            _auth.signOut()
            emitter.onComplete()
        }
    }

    fun signInWithEmailAndPassword2(email: String, password: String): Task<AuthResult> {
       return _auth.signInWithEmailAndPassword(email, password)

    }



}
