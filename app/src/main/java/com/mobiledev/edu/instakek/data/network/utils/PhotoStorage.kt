package com.mobiledev.edu.instakek.data.network.utils

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.support.annotation.NonNull
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.OnProgressListener
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask


class PhotoStorage {

//    var mStorageRef : StorageReference? =null
//
//    fun onCreate(){
//
//        val storage = FirebaseStorage.getInstance()
//        // Create a storage reference from our app
//        mStorageRef = storage.reference
//
//        // imagesRef now points to "images"
//        var imagesRef: StorageReference? = storageRef.child("images")
//
//
//        // imagesRef still points to "images"
//        var spaceRef = storageRef.child("images/space.jpg")
//        // [END create_child_reference]
//
//
//        // imagesRef now points to 'images'
//        imagesRef = spaceRef.parent
//
//
//        val rootRef = spaceRef.root
//
//        val earthRef = spaceRef.parent?.child("earth.jpg")
//
//        val nullRef = spaceRef.root.parent
//
//    }
//
//    private fun downloadFile(url :String,imageView: ImageView)
//    {
//        val storage = FirebaseStorage.getInstance()
//        // Create a storage reference from our app
//        var storageRef = storage.reference
//
//        storageRef.child(url).downloadUrl.addOnSuccessListener {
//            // Got the download URL for 'users/me/profile.png'
////            val downloadUri = task.result
////            var bitmap = Bitmap(it)
////            imageView.setImageBitmap(it)
//            val bitmap = it.run { thread {  } }
//            imageView.setImageBitmap(bitmap)
//        }.addOnFailureListener {
//            // Handle any errors
//        }
//
//    }
//


    public fun uploadFile(filePath: Uri?,storageReference: StorageReference,appContext: Context) {
        //if there is a file to upload
        Log.d("TagActivity",filePath.toString())
        if (filePath != null) {
            Log.d("TagActivity","Photo")
            //displaying a progress dialog while upload is going on
            val progressDialog = ProgressDialog(appContext)
            progressDialog.setTitle("Uploading")
            progressDialog.show()

            val riversRef = storageReference.child("images/pic.jpg")
            riversRef.putFile(filePath)
                    .addOnSuccessListener(object : OnSuccessListener<UploadTask.TaskSnapshot> {
                        override  fun onSuccess(taskSnapshot: UploadTask.TaskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            progressDialog.dismiss()

                            //and displaying a success toast
                            Toast.makeText(appContext, "File Uploaded ", Toast.LENGTH_LONG).show()
                        }
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