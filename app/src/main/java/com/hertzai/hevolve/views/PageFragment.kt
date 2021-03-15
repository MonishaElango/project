package com.hertzai.hevolve.views

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.PermissionChecker.checkCallingOrSelfPermission
import androidx.fragment.app.Fragment
import com.hertzai.hevolve.R
import kotlinx.android.synthetic.main.activity_fourth_page.*


@Suppress("DEPRECATED_IDENTITY_EQUALS")
class PageFragment : Fragment() {
    var position: Int = -1
    val REQUEST_CODE_STORAGE_PERMS = 321


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        position = arguments?.getInt("POSITION")!!

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layoutId = if (position == 1) {
            R.layout.activity_first_page
        } else if (position == 2) {
            R.layout.activity_second_page
        } else if (position == 3) {
            R.layout.activity_third_page
        } else {
            R.layout.activity_fourth_page
        }
        return layoutInflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (position) {
            1 -> return
            2 -> return
            3 -> return
            4 -> {

                getStartedBtn.setOnClickListener {

                    val settings: SharedPreferences = activity!!.getSharedPreferences("prefName", Context.MODE_PRIVATE)
                    val editor: SharedPreferences.Editor = settings.edit()
                    editor.putBoolean("firstRun", true)
                    editor.commit()

                    if (!hasPermissions()) {
                        // your app doesn't have permissions, ask for them.
                        requestNecessaryPermissions()
                    }
                    else {
                        val intent = Intent(activity!!, SignUpActivity::class.java)
                        startActivity(intent)
                        activity!!.finish()
                    }
                }
            }

        }

    }

    private fun requestNecessaryPermissions() {

        // make array of permissions which you want to ask from user.

        // make array of permissions which you want to ask from user.
        val permissions = arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // have arry for permissions to requestPermissions method.
            // and also send unique Request code.
            requestPermissions(permissions, REQUEST_CODE_STORAGE_PERMS)
        }
    }


    private fun hasPermissions(): Boolean {
        var res = 0
        // list all permissions which you want to check are granted or not.
        // list all permissions which you want to check are granted or not.
        val permissions = arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        for (perms in permissions) {
            res = checkCallingOrSelfPermission(activity!!, perms)
            if (res != PackageManager.PERMISSION_GRANTED) {
                // it return false because your app dosen't have permissions.
                return false
            }
        }
        // it return true, your app has permissions.
        // it return true, your app has permissions.
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        var allowed = true
        when (requestCode) {
            REQUEST_CODE_STORAGE_PERMS -> for (res in grantResults) {
                // if user granted all required permissions then 'allowed' will return true.
                allowed = allowed && res == PackageManager.PERMISSION_GRANTED
            }
            else ->                 // if user denied then 'allowed' return false.
                allowed = false
        }
        if (allowed) {
            // if user granted permissions then do your work.
            val intent = Intent(activity!!, SignUpActivity::class.java)
            startActivity(intent)

        } else {
            // else give any custom waring message.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO)) {
                    Toast.makeText(this.context, "Audio Permissions denied", Toast.LENGTH_SHORT).show()
                } else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Toast.makeText(this.context, "Storage Permissions denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}


