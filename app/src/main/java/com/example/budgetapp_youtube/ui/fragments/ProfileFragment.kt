package com.example.budgetapp_youtube.ui.fragments

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.budgetapp_youtube.R
import com.example.budgetapp_youtube.databinding.FragmentProfileBinding
import com.example.budgetapp_youtube.entities.Profile
import com.example.budgetapp_youtube.ui.viewmodels.ProfileViewModel
import com.example.budgetapp_youtube.util.Constants.PREFERENCE_NAME
import com.example.budgetapp_youtube.util.Constants.PREFERENCE_PROFILE_EXISTANCE_KEY
import com.example.budgetapp_youtube.util.InternalStoragePhoto
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    lateinit var binding: FragmentProfileBinding
    private val profileViewModel: ProfileViewModel by viewModels()
    private lateinit var filePath: Uri
    private lateinit var bitmap: Bitmap
    private lateinit var myPref:SharedPreferences


    private val takePhoto =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            filePath = result
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(requireContext().contentResolver!!, filePath)
                bitmap = ImageDecoder.decodeBitmap(source)
            }
            saveImageToInternalStorage("profile", bitmap)

        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        myPref = requireContext().getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
        if (myPref.contains(PREFERENCE_PROFILE_EXISTANCE_KEY)){
            changeViewVisibilityPostRegistration()
        }else{
            changeViewVisibilityForRegistration()
        }

        binding.profileImage.setOnClickListener {
            takePhoto.launch("image/*")
        }

        profileViewModel.profileLiveData.observe(viewLifecycleOwner){profile->
            if (profile!!.size >=1){
                viewLifecycleOwner.lifecycleScope.launch {
                    val listOfImage = loadImageFromInternalStorage()
                    for (i in listOfImage){
                        if (i.name.contains("profile")){
                            Glide.with(requireContext()).load(i.bitmap).circleCrop().into(binding.profileImage)
                        }
                    }

                    binding.bankName.setText(profile!![0].bankName)
                    binding.initialBalance.setText(profile!![0].initialBalance.toString())
                    binding.currentBalance.setText(profile!![0].currentBalance.toString())
                    binding.materialCheckBox.isChecked = profile!![0].primaryBank
                    binding.profileName.setText(profile!![0].name)
                    binding.profileEmail.setText(profile!![0].email)
                }
            }else{
                Toast.makeText(requireContext(),"Complete Profile",Toast.LENGTH_SHORT).show()
            }
        }


        binding.submitProfile.setOnClickListener {
            submitData(
                binding.profileName.text.toString(),
                binding.profileEmail.text.toString(),
                binding.bankName.text.toString(),
                binding.initialBalance.text.toString(),
                binding.materialCheckBox.isChecked
            )
        }
    }

    private fun submitData(
        profileName: String,
        profileEmail: String,
        bankName: String,
        initialBalance: String,
        checked: Boolean
    ) {

        profileViewModel.insertProfileData(
            Profile(
                name = profileName,
                email = profileEmail,
                profileImageFilePath = filePath.toString(),
                bankName = bankName,
                currentBalance = initialBalance.toFloat(),
                initialBalance = initialBalance.toFloat(),
                primaryBank =  checked
            )
        )
        val editor = myPref.edit()
        editor.putBoolean(PREFERENCE_PROFILE_EXISTANCE_KEY,true)
        editor.apply()
        findNavController().navigate(R.id.action_profileFragment_to_caledarViewFragment)

    }


    private fun changeViewVisibilityForRegistration() {
        binding.submitProfile.visibility = View.VISIBLE
        binding.updateCurrentBalance.visibility = View.GONE
        binding.balanceLayout.visibility = View.GONE
    }

    private fun changeViewVisibilityPostRegistration() {
        binding.submitProfile.visibility = View.GONE
        binding.updateCurrentBalance.visibility = View.VISIBLE
        binding.balanceLayout.visibility = View.VISIBLE
    }


    private fun saveImageToInternalStorage(fileName: String, bitmap: Bitmap): Boolean {
        return try {
            requireContext().openFileOutput("$fileName.jpg", MODE_PRIVATE).use { outputSream ->
                if (bitmap.compress(Bitmap.CompressFormat.JPEG, 95, outputSream)) {
                    throw IOException("Could not save Bitmap")
                }
            }
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }

    }

    private suspend fun loadImageFromInternalStorage(): List<InternalStoragePhoto> {
        return withContext(Dispatchers.IO) {
            val files = requireContext().filesDir.listFiles()
            files.filter {
                it.canRead() && it.isFile && it.name.endsWith(".jpg")
            }.map {
                val bytes = it.readBytes()
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                InternalStoragePhoto(it.name, bitmap)
            }
        }
    }

}