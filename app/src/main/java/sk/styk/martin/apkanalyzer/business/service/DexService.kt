package sk.styk.martin.apkanalyzer.business.service

import android.content.pm.PackageInfo
import android.util.Log
import dalvik.system.DexFile
import sk.styk.martin.apkanalyzer.model.detail.ClassPathData
import java.io.IOException
import java.util.*

/**
 * @author Martin Styk
 * @version 21.10.2017.
 */
class DexService {

    fun get(packageInfo: PackageInfo): ClassPathData {

        val packageClasses = ArrayList<String>()
        val otherClasses = ArrayList<String>()
        var innerClasses = 0
        val classPathData = ClassPathData()

        if (packageInfo.applicationInfo != null) {

            var dexFile: DexFile? = null
            try {
                dexFile = DexFile(packageInfo.applicationInfo.sourceDir)
                val iterator = dexFile.entries()
                while (iterator.hasMoreElements()) {
                    val className = iterator.nextElement()
                    if (className != null && className.startsWith(packageInfo.applicationInfo.packageName))
                        packageClasses.add(className)
                    else
                        otherClasses.add(className)

                    if (className != null && className.contains("$")) {
                        innerClasses++
                    }
                }
            } catch (e: IOException) {
                Log.e(DexService::class.java.simpleName, e.localizedMessage)
            } finally {
                try {
                    dexFile?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        classPathData.packageClasses = packageClasses
        classPathData.otherClasses = otherClasses
        classPathData.numberOfInnerClasses = innerClasses

        return classPathData
    }
}
