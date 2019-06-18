package com.mobiledev.edu.instakek.ui.activity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.OnProgressListener
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.mobiledev.edu.instakek.R
import com.mobiledev.edu.instakek.utils.AuthUtils
import com.squareup.picasso.Picasso
import java.io.IOException


class CreatePostActivity : AppCompatActivity() {


    companion object {
        private val PICK_IMAGE_REQUEST = 234
    }

    private val Tag = "CreatePostActivity"

    private var  buttonChoose: Button? = null;
    private var buttonUpload: Button? = null;

    //ImageView
    private var imageView: ImageView? = null;

    private var filePath: Uri? = null;

    private lateinit var url :String;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        buttonChoose = findViewById(R.id.buttonChoose)
        buttonUpload = findViewById(R.id.buttonUpload)

        imageView = findViewById(R.id.imageView)


        //attaching listener
        buttonChoose!!.setOnClickListener{showFileChooser()}
        buttonUpload!!.setOnClickListener{uploadFile()}




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


    fun uploadFile(){
        Log.d(Tag,"upload photo")
        uploadFiles(filePath, FirebaseStorage.getInstance().reference,this,AuthUtils.CURRENT_USER_ID)
    }




    fun uploadFiles(filePath: Uri?, storageReference: StorageReference, appContext: Context, userId:Long) {
        //if there is a file to upload
        Log.d("TagActivity",filePath.toString())
        if (filePath != null) {
            Log.d("TagActivity","Photo")
            //displaying a progress dialog while upload is going on
            val progressDialog = ProgressDialog(appContext)
            progressDialog.setTitle("Uploading")
            progressDialog.show()

            val imageName:String
            imageName = userId.toString().plus('_').plus(java.util.Calendar.getInstance().timeInMillis)



            val riversRef = storageReference.child("images/".plus(imageName).plus(".jpg"))
            riversRef.putFile(filePath)
                    .addOnSuccessListener(object : OnSuccessListener<UploadTask.TaskSnapshot> {
                        override  fun onSuccess(taskSnapshot: UploadTask.TaskSnapshot) {

                            progressDialog.dismiss()

                            //and displaying a success toast
                            Toast.makeText(appContext, "File Uploaded ", Toast.LENGTH_LONG).show()



                            Picasso.get()
                                    .load("gs://instakekandroid.appspot.com/images/".plus(imageName).plus(".jpg"))
                                    //.fit()
                                    .into(imageView)
                        }

                        //TODO from here insert with  "gs://instakekandroid.appspot.com/images/".plus(imageName).plus(".jpg")
                    })
                    .addOnFailureListener(object : OnFailureListener {
                        override  fun onFailure(@NonNull exception: Exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss()

                            //and displaying error message
                            Toast.makeText(appContext, exception.message, Toast.LENGTH_LONG).show()
                        }
                    })
                    .addOnProgressListener(object : OnProgressListener<UploadTask.TaskSnapshot> {
                        override fun onProgress(taskSnapshot: UploadTask.TaskSnapshot) {
                            //calculating progress percentage
                            val progress = 100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount()

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + progress.toInt() + "%...")
                        }
                    })

        } else {
            //you can display an error toast
        }//if there is not any file
    }


}