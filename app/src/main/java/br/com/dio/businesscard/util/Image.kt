package br.com.dio.businesscard.util

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import br.com.dio.businesscard.R
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

//Classe para compartilhar imagem do cartão
class Image {
    companion object {
        fun share(context: Context, view: View) {
            val bitmap = getScreenShotFromView(view)
            bitmap?.let {
                saveMediaToStorage(context, bitmap)
            }
        }

        //Screenshot da tela ( View)
        private fun getScreenShotFromView(view: View): Bitmap? {
            var screenshot: Bitmap? = null
            try {
                screenshot =
                    Bitmap.createBitmap(
                        view.measuredWidth,
                        view.measuredHeight,
                        Bitmap.Config.ARGB_8888
                    )
                val canvas = Canvas(screenshot)
                view.draw(canvas)
            } catch (e: Exception) {
                Log.e("GFG", "Failed to capture screenshot because:" + e.message)
            }
            return screenshot
        }

        //Salva
        private fun saveMediaToStorage(context: Context, bitmap: Bitmap) {
            val filename = "${System.currentTimeMillis()}.jpg"
            var fos: OutputStream? = null

            //Verifica se a versão do android é Q
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                context.contentResolver?.also { resolver ->
                    val contentValues = ContentValues().apply {
                        put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                    }
                    val imageUri: Uri? =
                        resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                    fos = imageUri?.let {
                        shareIntent(context, imageUri)
                        resolver.openOutputStream(it)
                    }
                }
            } else {
                val imagesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                val image = File(imagesDir, filename)
                shareIntent(context, Uri.fromFile(image))
                fos = FileOutputStream(image)
            }
            fos?.use {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
                Toast.makeText(context, "Imagem capturada com sucesso", Toast.LENGTH_SHORT).show()
            }
        }

        //Compartilha
        private fun shareIntent(context: Context, image: Uri) {
            val shareIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, image)
                type = "image/jpeg"
            }
            context.startActivity(
                Intent.createChooser(
                    shareIntent,
                    context.resources.getText(R.string.label_share_to)
                )
            )
        }
    }
}