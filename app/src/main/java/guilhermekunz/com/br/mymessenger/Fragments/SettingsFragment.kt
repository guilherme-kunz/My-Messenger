package guilhermekunz.com.br.mymessenger.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import guilhermekunz.com.br.mymessenger.ModelClasses.Users
import guilhermekunz.com.br.mymessenger.R
import kotlinx.android.synthetic.main.fragment_settings.view.*


class SettingsFragment : Fragment() {

    var usersReference: DatabaseReference? = null
    var firebaseUser: FirebaseUser? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        firebaseUser = FirebaseAuth.getInstance().currentUser
        usersReference = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUser!!.uid)

        usersReference!!.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()){
                    val user: Users? = p0.getValue(Users::class.java)

                    if (context!=null) {
                        view.username_settings.text = user!!.username
                        Picasso.get().load(user.profile).into(view.profile_image_settings)
                        Picasso.get().load(user.cover).into(view.cover_image_settings)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        return view
    }

}