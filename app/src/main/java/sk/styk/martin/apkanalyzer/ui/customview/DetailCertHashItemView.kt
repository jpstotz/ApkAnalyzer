package sk.styk.martin.apkanalyzer.ui.customview

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import sk.styk.martin.apkanalyzer.R
import sk.styk.martin.apkanalyzer.ui.activity.dialog.SimpleTextDialog

const val URL_APKMIRROR = "https://www.apkmirror.com/?s=%s&post_type=app_release&searchtype=app"
const val URL_ANDROIDOBSERVATORY = "https://androidobservatory.org/cert/%s"

class DetailCertHashItemView(context: Context, attrs: AttributeSet?) : DetailItemView(context, attrs) {

    override fun onClick(v: View) {
        if (valueText == null) {
            return
        }
        val valueString = valueText.toString()

        val certHashView = LayoutInflater.from(context).inflate(R.layout.view_certhash, null)
        (certHashView.findViewById(R.id.button_apkmirror) as Button).setOnClickListener {
            openExternalBrowser(context, URL_APKMIRROR, valueString)
        }
        (certHashView.findViewById(R.id.button_androidobservatory) as Button).setOnClickListener {
            openExternalBrowser(context, URL_ANDROIDOBSERVATORY, valueString.toUpperCase())
        }

        val dlg = AlertDialog.Builder(context)
                .setTitle(titleText + ": " + valueString)
                .setMessage(descriptionText)
                .setView(certHashView)
                .setNegativeButton(R.string.dismiss) { x, _ -> x.dismiss() }
                .create()
        dlg.show()
    }

    fun openExternalBrowser(context: Context, urlFormat: String, value: String) {
        val url = String.format(urlFormat, value);
        val openURL = Intent(Intent.ACTION_VIEW)
        // Log.d("URL", url);
        openURL.data = Uri.parse(url)
        context.startActivity(openURL)
    }
}