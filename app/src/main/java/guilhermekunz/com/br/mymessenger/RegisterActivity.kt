package guilhermekunz.com.br.mymessenger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUsers: DatabaseReference
    private var firebaseUserID: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val toolbar: Toolbar = findViewById(R.id.toolbar_register)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Register"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            val intent = Intent(this@RegisterActivity, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        mAuth = FirebaseAuth.getInstance()

        register_btn.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val username: String = username_register.text.toString()
        val email: String = email_register.text.toString()
        val password: String = password_register.text.toString()

        when {
            username == "" -> {
                Toast.makeText(
                    this@RegisterActivity,
                    "Please insert the username",
                    Toast.LENGTH_SHORT
                ).show()
            }
            email == "" -> {
                Toast.makeText(
                    this@RegisterActivity,
                    "Please insert the E-mail",
                    Toast.LENGTH_SHORT
                ).show()
            }
            password == "" -> {
                Toast.makeText(
                    this@RegisterActivity,
                    "Please insert the password",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> {
                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            firebaseUserID = mAuth.currentUser!!.uid
                            refUsers = FirebaseDatabase.getInstance().reference.child("Users")
                                .child(firebaseUserID)
                            val userHashMap = HashMap<String, Any>()
                            userHashMap["uid"] = firebaseUserID
                            userHashMap["username"] = username
                            userHashMap["profile"] =
                                "https://firebasestorage.googleapis.com/v0/b/my-messenger-5193e.appspot.com/o/profile.png?alt=media&token=7b46176a-fb65-4465-9958-ec28529458a9"
                            userHashMap["cover"] =
                                "https://firebasestorage.googleapis.com/v0/b/my-messenger-5193e.appspot.com/o/cover.jpg?alt=media&token=fc34c5e0-f4b5-4c7e-9614-32f122ba83c2"
                            userHashMap["status"] = "offline"
                            userHashMap["search"] = username.toLowerCase()
                            userHashMap["facebook"] = "https://wwww.facebook.com"
                            userHashMap["instagram"] = "https://wwww.instagram.com"

                            refUsers.updateChildren(userHashMap)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                        startActivity(intent)
                                        finish()
                                    }
                                }
                        } else {
                            Toast.makeText(
                                this@RegisterActivity,
                                "Error message: " + task.exception!!.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }
}
