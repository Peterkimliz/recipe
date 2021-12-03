package com.example.favrecipe.views.activities

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.favrecipe.R
import com.example.favrecipe.adapters.CustomListAdapter
import com.example.favrecipe.application.FavDishApplication
import com.example.favrecipe.databinding.ActivityAddUpdateDishBinding
import com.example.favrecipe.databinding.CustomDialogBinding
import com.example.favrecipe.databinding.DialogCustomListBinding
import com.example.favrecipe.model.FavDish
import com.example.favrecipe.utils.Constants.Companion.CAMERA_CODE
import com.example.favrecipe.utils.Constants.Companion.DISH_CATEGORY
import com.example.favrecipe.utils.Constants.Companion.DISH_COOKING_TIME
import com.example.favrecipe.utils.Constants.Companion.DISH_TYPE
import com.example.favrecipe.utils.Constants.Companion.GALLERY_CODE
import com.example.favrecipe.utils.Constants.Companion.IMAGE_DIRECTORY
import com.example.favrecipe.utils.Constants.Companion.IMAGE_SOURCE_LOCAL
import com.example.favrecipe.utils.Constants.Companion.dishCategories
import com.example.favrecipe.utils.Constants.Companion.dishCookTime
import com.example.favrecipe.utils.Constants.Companion.dishTypes
import com.example.favrecipe.utils.FavDishViewModelFactory
import com.example.favrecipe.viewmodels.FavDishViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*

class AddUpdateDishActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mBinding: ActivityAddUpdateDishBinding
    private lateinit var customItemsDialog: Dialog
    private var imagePath: String = ""

    private val mFavDishViewModel: FavDishViewModel by viewModels {
        FavDishViewModelFactory((application as FavDishApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityAddUpdateDishBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setupActionBar()

        mBinding.ivAddDishImage.setOnClickListener(this)
        mBinding.btnAddDish.setOnClickListener(this)
        mBinding.etType.setOnClickListener(this)
        mBinding.etCategory.setOnClickListener(this)
        mBinding.etCookingTime.setOnClickListener(this)

    }

    /////////////////////////////////////setting up the  toolbar////////////////////////////////////////
    private fun setupActionBar() {
        val toolbar = mBinding.updateToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    //////////////////////////handling the click events of views////////////////////////////////////////////
    override fun onClick(views: View?) {
        if (views != null) {
/////////////////////////////selection of image dialog///////////////////////////////////
            when (views.id) {
                R.id.iv_add_dish_image -> {
                    showDialog()
                    return
                }
/////////////////////////////selection of dish type dialog///////////////////////////////////
                R.id.et_type -> {
                    showItemsOnDialogs(
                        resources.getString(R.string.title_select_dish_type),
                        dishTypes(),
                        DISH_TYPE
                    )

                    return
                }
/////////////////////////////selection of category type dialog/////////////////////////////////
                R.id.et_category -> {
                    showItemsOnDialogs(
                        resources.getString(R.string.title_select_dish_category),
                        dishCategories(), DISH_CATEGORY
                    )
                    return
                }
/////////////////////////////selection of dish cook time dialog///////////////////////////////
                R.id.et_cooking_time -> {
                    showItemsOnDialogs(
                        resources.getString(R.string.title_select_dish_cooking_time),
                        dishCookTime(), DISH_COOKING_TIME
                    )
                    return
                }
/////////////////////////////saving the items to the database //////////////////////////////////
                R.id.btn_add_dish -> {
                    val title = mBinding.etTitle.text.toString().trim { it <= ' ' }
                    val type = mBinding.etType.text.toString().trim { it <= ' ' }
                    val category = mBinding.etCategory.text.toString().trim { it <= ' ' }
                    val ingredients = mBinding.etIngredients.text.toString().trim { it <= ' ' }
                    val cookingTimeInMinutes =
                        mBinding.etCookingTime.text.toString().trim { it <= ' ' }
                    val cookingDirection =
                        mBinding.etDirectionToCook.text.toString().trim { it <= ' ' }
                    when {
                        TextUtils.isEmpty(imagePath) -> {
                            Toast.makeText(
                                this@AddUpdateDishActivity,
                                resources.getString(R.string.err_msg_select_dish_image),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        TextUtils.isEmpty(title) -> {
                            Toast.makeText(
                                this@AddUpdateDishActivity,
                                resources.getString(R.string.err_msg_enter_dish_title),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        TextUtils.isEmpty(type) -> {
                            Toast.makeText(
                                this@AddUpdateDishActivity,
                                resources.getString(R.string.err_msg_select_dish_type),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        TextUtils.isEmpty(category) -> {
                            Toast.makeText(
                                this@AddUpdateDishActivity,
                                resources.getString(R.string.err_msg_select_dish_category),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        TextUtils.isEmpty(ingredients) -> {
                            Toast.makeText(
                                this@AddUpdateDishActivity,
                                resources.getString(R.string.err_msg_enter_dish_ingredients),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        TextUtils.isEmpty(cookingTimeInMinutes) -> {
                            Toast.makeText(
                                this@AddUpdateDishActivity,
                                resources.getString(R.string.err_msg_select_dish_cooking_time),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        TextUtils.isEmpty(cookingDirection) -> {
                            Toast.makeText(
                                this@AddUpdateDishActivity,
                                resources.getString(R.string.err_msg_enter_dish_cooking_instructions),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        else -> {
                            val favDishDetails: FavDish = FavDish(
                                image = imagePath,
                                imageSource = IMAGE_SOURCE_LOCAL,
                                title = title,
                                type = type,
                                category = category,
                                ingredients = ingredients,
                                cookingTime = cookingTimeInMinutes,
                                directionToCook = cookingDirection,
                                favouriteDish = false
                            )
                            mFavDishViewModel.insertData(favDish = favDishDetails)
                            Toast.makeText(
                                this,
                                "data has been added successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        }
                    }
                }
            }
        }
    }

    /////////////////////////////////////////////function to show dialog to select image////////////////
    private fun showDialog() {
        val dialog = Dialog(this)
        val binder: CustomDialogBinding = CustomDialogBinding.inflate(layoutInflater)
        dialog.setContentView(binder.root)

        binder.tvCamera.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, CAMERA_CODE)
            dialog.dismiss()
        }

        binder.tvGallery.setOnClickListener {
            val galleryIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, GALLERY_CODE)
            dialog.dismiss()
        }
        dialog.show()

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_CODE) {
                data?.extras?.let {
                    val bitmap: Bitmap = data.extras!!.get("data") as Bitmap
                    Glide.with(this)
                        .load(bitmap)
                        .centerCrop()
                        .into(mBinding.ivDishImage)
                    imagePath = saveImageToInternalStorage(bitmap)
                    mBinding.ivAddDishImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            this, R.drawable.ic_edit
                        )
                    )
                }
            }
            if (requestCode == GALLERY_CODE) {
                data?.let {
                    val imageUrl = data.data
                    Glide.with(this)
                        .load(imageUrl)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: com.bumptech.glide.request.target.Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: com.bumptech.glide.request.target.Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                val bitmap: Bitmap = resource!!.toBitmap()
                                imagePath = saveImageToInternalStorage(bitmap)
                                return false
                            }
                        }).into(mBinding.ivDishImage)
                    mBinding.ivAddDishImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.ic_edit
                        )
                    )
                }

            }

        } else if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this, "failed to pick image", Toast.LENGTH_SHORT).show()
        }
    }

    /////////////////////////////////function to convert image Bitmap to string////////////////////
    private fun saveImageToInternalStorage(bitmap: Bitmap): String {
        val wrapper = ContextWrapper(applicationContext)
        var file = wrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)
        // Mention a file name to save the image
        file = File(file, "${UUID.randomUUID()}.jpg")
        try {
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream) // Compress bitmap
            stream.flush()// Flush the stream
            stream.close()
        } catch (e: IOException) { // Catch the exception
            e.printStackTrace()
        }
        return file.absolutePath
    }


    //////////////////////////////////////function to show selected items/////////////////////////
    private fun showItemsOnDialogs(title: String, item: ArrayList<String>, selection: String) {
        customItemsDialog = Dialog(this)
        val recycleBinder: DialogCustomListBinding = DialogCustomListBinding.inflate(layoutInflater)
        customItemsDialog.setContentView(recycleBinder.root)
        recycleBinder.tvTitle.text = title
        val rvAdapter: CustomListAdapter = CustomListAdapter(this, item, selection)
        recycleBinder.rvList.apply {
            layoutManager = LinearLayoutManager(this@AddUpdateDishActivity)
            adapter = rvAdapter
        }

        customItemsDialog.show()
    }

    ////////////////////////////function to select item from a dialog///////////////////////////////
    fun selectedListItem(item: String, selection: String) {
        when (selection) {
            DISH_TYPE -> {
                customItemsDialog.dismiss()
                mBinding.etType.setText(item)
            }
            DISH_CATEGORY -> {
                customItemsDialog.dismiss()
                mBinding.etCategory.setText(item)
            }
            DISH_COOKING_TIME -> {
                customItemsDialog.dismiss()
                mBinding.etCookingTime.setText(item)
            }

        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////


}