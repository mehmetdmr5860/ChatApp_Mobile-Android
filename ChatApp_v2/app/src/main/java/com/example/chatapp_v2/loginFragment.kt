package com.example.chatapp_v2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.chatapp_v2.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class loginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase Auth
        auth = Firebase.auth

        val currentUser = auth.currentUser

        if(currentUser != null){
            val action = loginFragmentDirections.actionLoginFragmentToChatFragment()
            findNavController().navigate(action)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signupButton.setOnClickListener {
            // kayıt işlemi

            auth.createUserWithEmailAndPassword(binding.emailText.text.toString(), binding.passwordText.text.toString()).addOnSuccessListener {
                //kullanıcı oluşturuldu

                val action = loginFragmentDirections.actionLoginFragmentToChatFragment()
                findNavController().navigate(action)



            }.addOnFailureListener {exception ->
                //Hatalı işlem ve Exception olarak verildi
                Toast.makeText(requireContext(),exception.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
        binding.loginButton.setOnClickListener {
            //giriş işlemleri
            auth.signInWithEmailAndPassword(binding.emailText.text.toString(), binding.passwordText.text.toString()).addOnSuccessListener{
                //giriş yapıldı
                val action = loginFragmentDirections.actionLoginFragmentToChatFragment()
                findNavController().navigate(action)

            }.addOnFailureListener {
                //giriş hatalı
                Toast.makeText(requireContext(),it.localizedMessage, Toast.LENGTH_LONG).show()

            }


        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}