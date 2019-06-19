package com.mobiledev.edu.instakek.ui.activity

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatEditText
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.OnProgressListener
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.mobiledev.edu.instakek.R
import com.mobiledev.edu.instakek.data.database.entity.Post
import com.mobiledev.edu.instakek.data.database.entity.PostContent
import com.mobiledev.edu.instakek.ui.viewModel.PostViewModel
import com.mobiledev.edu.instakek.utils.AuthUtils
import java.io.IOException
import java.util.*


class CreatePostActivity : AppCompatActivity() {
    private var mPostViewModel: PostViewModel? = null

    companion object {
        private val PICK_IMAGE_REQUEST = 234
    }

    private val Tag = "CreatePostActivity"

    private var buttonChoose: Button? = null;
    private var buttonUpload: Button? = null;
    private var backButton: ImageButton? = null;
    private var description: AppCompatEditText? = null

    //ImageView
    private var imageView: ImageView? = null;

    private var filePath: Uri? = null;

    private lateinit var url: String;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        buttonChoose = findViewById(R.id.buttonChoose)
        buttonUpload = findViewById(R.id.buttonUpload)
        backButton = findViewById(R.id.back_button)
        description = findViewById(R.id.caption_input)

        imageView = findViewById(R.id.imageView)


        //attaching listener
        buttonChoose!!.setOnClickListener { showFileChooser() }
        buttonUpload!!.setOnClickListener { uploadFile() }
        backButton!!.setOnClickListener { goBack() }

        mPostViewModel = ViewModelProviders.of(this).get(PostViewModel::class.java)

    }

    private fun goBack() {
        var intent: Intent = Intent(this, ProfileActivity::class.java)

        startActivity(intent)
        finish()
    }


    private fun showFileChooser() {
        val intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    //handling the image chooser activity result
    protected override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data!!.getData() != null) {
            filePath = data!!.getData()
            try {

                val bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath)
                imageView!!.setImageBitmap(bitmap)

            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }


    fun uploadFile() {
        Log.d(Tag, "upload photo")
        uploadFiles(filePath, FirebaseStorage.getInstance().reference, this, AuthUtils.CURRENT_USER_ID)
    }


    fun uploadFiles(filePath: Uri?, storageReference: StorageReference, appContext: Context, userId: Long) {
        //if there is a file to upload
        Log.d("TagActivity", filePath.toString())
        if (filePath != null) {
            Log.d("TagActivity", "Photo")
            //displaying a progress dialog while upload is going on
            //val progressDialog = ProgressDialog(appContext)
            //progressDialog.setTitle("Uploading")
            //progressDialog.show()

            val imageName: String
            imageName = userId.toString().plus('_').plus(java.util.Calendar.getInstance().timeInMillis)

            url = "https://firebasestorage.googleapis.com/v0/b/instakekandroid.appspot.com/o/images%2F".plus(imageName).plus(".jpg?")

            val riversRef = storageReference.child("images/".plus(imageName).plus(".jpg"))
            riversRef.putFile(filePath)
                    .addOnSuccessListener(object : OnSuccessListener<UploadTask.TaskSnapshot> {
                        override fun onSuccess(taskSnapshot: UploadTask.TaskSnapshot) {
                            Log.d("LOaded","!!!!!!!!!")
                            //progressDialog.dismiss()

                            //and displaying a success toast
                            //Toast.makeText(appContext, "File Uploaded ", Toast.LENGTH_LONG).show()


                            var post: Post = Post(150, 0, Calendar.getInstance().time,
                                    null, description!!.text.toString())
                            post.contents = listOf(PostContent(150, 150, url))
                            if(mPostViewModel!=null) {
                                mPostViewModel!!.insertPost(post)
                            }
                            else{Log.d("Problem","Big")}

                        }


                        //TODO from here insert with  "gs://instakekandroid.appspot.com/images/".plus(imageName).plus(".jpg")
                    })
                    .addOnFailureListener(object : OnFailureListener {
                        override fun onFailure(@NonNull exception: Exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            //progressDialog.dismiss()

                            //and displaying error message
                            Toast.makeText(appContext, exception.message, Toast.LENGTH_LONG).show()
                        }
                    })
                    .addOnProgressListener(object : OnProgressListener<UploadTask.TaskSnapshot> {
                        override fun onProgress(taskSnapshot: UploadTask.TaskSnapshot) {
                            //calculating progress percentage
                            val progress = 100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount()

                            //displaying percentage in progress dialog
                            //progressDialog.setMessage("Uploaded " + progress.toInt() + "%...")
                        }
                    })

        } else {
            //you can display an error toast
        }//if there is not any file


        var intent: Intent = Intent(this, HomeActivity::class.java)

        startActivity(intent)
        finish()

    }


}