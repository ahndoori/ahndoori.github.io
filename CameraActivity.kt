package com.wjthinkbig.conceptvideo

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar

class CameraActivity : CommonActivity() {

	val PERMISSION_REQUEST=10000
	val ACTION_GET_CONTENT=10001
	var pointDisplay=Point()
	var pointPreview=Point()
	private lateinit var fragmentTransaction:FragmentTransaction
	val cameraFragment= CameraFragment()
	val drawFragment= DrawFragment()
	lateinit var sharedPreferences: SharedPreferences
	lateinit var layout: ConstraintLayout
	lateinit var imageView: ImageView
	lateinit var layoutMenu: ConstraintLayout
	lateinit var btnFullpage: Button
	lateinit var btnCamera: Button
	lateinit var btnDraw: Button
	val cropImageName="CROP.JPG"

	fun showSnackbarAlbum(){
		val snackbar: Snackbar =
			Snackbar.make(layout,getString(R.string.permission_camera_deny), Snackbar.LENGTH_LONG)
		val viewSnackbar = snackbar.view
		val params = viewSnackbar.layoutParams as FrameLayout.LayoutParams
		viewSnackbar.layoutParams = params
		snackbar.setAction(getString(R.string.setting)){
			startActivity(Intent(
				Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
				Uri.parse("package:" + BuildConfig.APPLICATION_ID)))
		}
		snackbar.show()
	}

	override fun finish() {
		super.finish()
		overridePendingTransition(0,0)
	}

	override fun onBackPressed(){
		super.onBackPressed()
		openCameraFragment()
	}

	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)
		when (requestCode) {
			PERMISSION_REQUEST -> {
				for(i in 0 until permissions.size) {
					if (permissions[i] == Manifest.permission.CAMERA && grantResults[i] == PackageManager.PERMISSION_DENIED) {
						val intent = Intent()
						setResult(Activity.RESULT_OK, intent)
						finish()
						return
					}
					if (permissions[i] == Manifest.permission.READ_EXTERNAL_STORAGE && grantResults[i] == PackageManager.PERMISSION_DENIED) {
						showSnackbarAlbum()
					}
				}
				openStartFragment()
			}
		}
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		if (requestCode == ACTION_GET_CONTENT && resultCode == RESULT_OK) {
		}
	}

	@android.support.annotation.RequiresApi(Build.VERSION_CODES.M)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		sharedPreferences=getSharedPreferences("DEFAULT", AppCompatActivity.MODE_PRIVATE)
		window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
		window.decorView.apply { systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN }

		setContentView(R.layout.activity_camera)
		windowManager.defaultDisplay.getRealSize(pointDisplay)

		val permissionCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
			val permissionReadMediaImages=ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
			if(permissionCamera == PackageManager.PERMISSION_DENIED || permissionReadMediaImages == PackageManager.PERMISSION_DENIED) {
				requestPermissions(arrayOf(Manifest.permission.CAMERA,Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_REQUEST)
			} else {
				openStartFragment()
			}
		} else{
			val permissionReadStorage=ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
			if(permissionCamera == PackageManager.PERMISSION_DENIED || permissionReadStorage == PackageManager.PERMISSION_DENIED) {
				requestPermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_REQUEST)
			} else {
				openStartFragment()
			}
		}


		layout=findViewById(R.id.layout) as ConstraintLayout
		imageView=findViewById<ImageView>(R.id.imageView)
		layoutMenu=findViewById<ConstraintLayout>(R.id.layoutMenu)
		btnFullpage=findViewById<Button>(R.id.btnFullpage)
		btnCamera=findViewById<Button>(R.id.btnCamera)
		btnDraw=findViewById<Button>(R.id.btnDraw)
		btnFullpage.setOnClickListener(View.OnClickListener {
		})
		btnCamera.setOnClickListener(View.OnClickListener {
			openCameraFragment()
		})

		btnDraw.setOnClickListener(View.OnClickListener {
			openDrawFragment()
		})

	}

	fun openStartFragment(){
		Handler(Looper.getMainLooper()).postDelayed({
			layout.visibility=View.VISIBLE
			if(intent.getSerializableExtra("FRAGMENT")!=null && intent.getSerializableExtra("FRAGMENT") as String=="DRAW"){
				if(isEmulator) openDrawFragment()
				else openDrawFragment()
			}else{
				if(sharedPreferences.getString("MENU","CAMERA")=="DRAW") openDrawFragment()
				else openCameraFragment()
			}
		},300)
	}

	fun callMathpix(uri: Uri){
		val intent = Intent()
		intent.putExtra("URI",uri.toString())
		setResult(Activity.RESULT_OK, intent)
		finish()
	}

	fun openCameraFragment(){
		sharedPreferences.edit { putString("MENU","CAMERA") }
		fragmentTransaction=supportFragmentManager.beginTransaction()
		if(drawFragment.isAdded) fragmentTransaction.hide(drawFragment)
		if(!cameraFragment.isAdded) fragmentTransaction.add(R.id.frameLayout,cameraFragment)
		fragmentTransaction.show(cameraFragment)
		fragmentTransaction.commitAllowingStateLoss()
	}

	fun openDrawFragment(){
		sharedPreferences.edit { putString("MENU","DRAW") }
		fragmentTransaction=supportFragmentManager.beginTransaction()
		if(cameraFragment.isAdded) fragmentTransaction.hide(cameraFragment)
		if(!drawFragment.isAdded) fragmentTransaction.add(R.id.frameLayout,drawFragment)
		fragmentTransaction.show(drawFragment)
		fragmentTransaction.commitAllowingStateLoss()
	}

	fun getAlbumImage(){
		if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
			showSnackbarAlbum()
			return
		}
		val intent=Intent(Intent.ACTION_GET_CONTENT)
		intent.setType("image/*")
		intent.setAction(Intent.ACTION_GET_CONTENT)
		startActivityForResult(intent,ACTION_GET_CONTENT)
	}

}
