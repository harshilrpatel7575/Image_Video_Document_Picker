package com.example.demo2

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.demo2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var selectedImageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnPickImage.setOnClickListener {
            pickImageFromGallery()
        }

        binding.btnPickDocument.setOnClickListener {
            pickDocument()
        }

        binding.btnPickVideo.setOnClickListener {
            pickVideoFromGallery()
        }

//        binding.btnSaveText.setOnClickListener {
//            saveTextToFile()
//        }

//        val textFile = File(filesDir, "abc.txt")
//        if (textFile.exists()) {
//            val text = textFile.readText()
//            binding.editText.setText(text)
//    }
}
    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    private fun pickDocument() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/*"
        startActivityForResult(intent, REQUEST_DOCUMENT_PICK)
    }

    private fun pickVideoFromGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "video/*"
        startActivityForResult(intent, REQUEST_VIDEO_PICK)
    }

//    private fun saveTextToFile() {
//        val text = binding.editText.text.toString()
//        val textFile = File(filesDir, "abc.txt")
//        textFile.writeText(text)
//        Toast.makeText(this, "Text saved to abc.txt", Toast.LENGTH_SHORT).show()
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_PICK -> {
                    selectedImageUri = data?.data
                    binding.imageView.visibility = View.VISIBLE
                    binding.imageView.setImageURI(selectedImageUri)
                }
                REQUEST_DOCUMENT_PICK -> {
                    val selectedDocumentUri = data?.data
                    val fileName = selectedDocumentUri?.lastPathSegment
                    binding.textView.visibility = View.VISIBLE
                    binding.textView.text = "Selected Document: $fileName"
                }
                REQUEST_VIDEO_PICK -> {
                    val selectedVideoUri = data?.data
                    binding.videoView.visibility = View.VISIBLE
                    binding.videoView.setVideoURI(selectedVideoUri)
                    binding.videoView.start()
                }
            }
        }
    }

    companion object {
        private const val REQUEST_IMAGE_PICK = 1
        private const val REQUEST_DOCUMENT_PICK = 2
        private const val REQUEST_VIDEO_PICK = 3
    }
}