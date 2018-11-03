package com.rowland.delivery.merchant.features.auth

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.rowland.delivery.merchant.features.auth.models.Group
import com.rowland.delivery.merchant.services.session.SessionManager
import durdinapps.rxfirebase2.RxFirebaseAuth
import durdinapps.rxfirebase2.RxFirebaseUser
import durdinapps.rxfirebase2.RxFirestore
import java.util.*
import javax.inject.Inject


/**
 * Created by Rowland on 5/2/2018.
 */

class EmailAuth @Inject
constructor(private val mAuthConfig: AuthConfig, private val mFirebaseAuth: FirebaseAuth, private val mFirebaseFirestone: FirebaseFirestore, private val mSessionManager: SessionManager) : Auth() {

    override fun login() {
        val credentials = mAuthConfig.callback.doEmailLogin()
        val email = credentials[CRED_EMAIL_KEY]
        val password = credentials[CRED_PASSWORD_KEY]

        RxFirebaseAuth.signInWithEmailAndPassword(mFirebaseAuth, email!!, password!!)
                .subscribe({ authResult ->
                    val firebaseUser = authResult.user
                    RxFirebaseUser.getIdToken(firebaseUser, true)
                            .subscribe { getTokenResult ->
                                mSessionManager.setLogin(getTokenResult.token!!)
                                mAuthConfig.callback.onLoginSuccess()
                            }
                }, { throwable -> mAuthConfig.callback.onLoginFailure(AuthException(throwable.message!!, throwable)) })
    }

    override fun logout(context: Context): Boolean {
        try {
            mSessionManager.logout()
            return true

        } catch (e: Exception) {
            Log.e("Logout Failed", e.message)
            return false
        }

    }

    override fun register() {
        val credentials = mAuthConfig.callback.doEmailRegister()
        val email = credentials[CRED_EMAIL_KEY]
        val password = credentials[CRED_PASSWORD_KEY]

        RxFirebaseAuth.createUserWithEmailAndPassword(mFirebaseAuth, email!!, password!!)
                .subscribe({ authResult ->
                    val firebaseUser = authResult.user
                    configureUserAccount(firebaseUser)
                }, { throwable -> mAuthConfig.callback.onLoginFailure(AuthException(throwable.message!!, throwable)) })

    }

    private fun configureUserAccount(firebaseUser: FirebaseUser) {

        val batch = mFirebaseFirestone.batch()

        val usersCollectionRef = mFirebaseFirestone.collection("users")
        batch.set(usersCollectionRef.document(firebaseUser.uid), firebaseUser)

        val groupUsersCollectionRef = mFirebaseFirestone.collection("groups")
        val member = HashMap<String, Any>()
        member[String.format("members.%s", firebaseUser.uid)] = true
        batch.update(groupUsersCollectionRef.document(Group.MERCHANT), member)

        RxFirestore.atomicOperation(batch).subscribe({
            RxFirebaseUser.getIdToken(firebaseUser, true).subscribe { getTokenResult ->
                mSessionManager.setLogin(getTokenResult.token!!)
                mAuthConfig.callback.onLoginSuccess()
                Toast.makeText(mAuthConfig.activity, "Account Setup Successful", Toast.LENGTH_SHORT).show()
            }
        }, { throwable ->
            mAuthConfig.callback.onLoginFailure(AuthException(throwable.message!!, throwable))
            Toast.makeText(mAuthConfig.activity, "Account Setup Unsuccessful", Toast.LENGTH_SHORT).show()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {

    }

    override fun passwordReset() {

    }

    companion object {

        val CRED_EMAIL_KEY = "email"
        val CRED_PASSWORD_KEY = "password"
    }

}
