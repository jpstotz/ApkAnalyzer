package sk.styk.martin.apkanalyzer.util

import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * @author Martin Styk
 * @version 10.12.2017.
 */
object DigestHelper {

    fun sha1Digest(input: ByteArray): String {
        val digest = getDigest("SHA-1")
        digest.update(input)
        return getHexString(digest.digest())
    }

    fun sha1Digest(input: String): String {
        val digest = getDigest("SHA-1")
        digest.update(input.toByteArray())
        return getHexString(digest.digest())
    }

    fun md5Digest(input: ByteArray): String {
        val digest = getDigest("Md5")
        digest.update(input)
        return getHexString(digest.digest())
    }

    fun md5Digest(input: String): String {
        val digest = getDigest("Md5")
        digest.update(input.toByteArray())
        return getHexString(digest.digest())
    }

    fun byteToHexString(bArray: ByteArray): String {
        val sb = StringBuilder(bArray.size)
        val var5 = bArray.size

        for (var6 in 0 until var5) {
            val aBArray = bArray[var6]
            val sTemp = Integer.toHexString(255 and aBArray.toChar().toInt())
            if (sTemp.length < 2) {
                sb.append(0)
            }

            sb.append(sTemp.toUpperCase())
        }

        return sb.toString()
    }

    private fun getHexString(digest: ByteArray): String {
        val bi = BigInteger(1, digest)
        return String.format("%032x", bi)
    }

    private fun getDigest(algorithm: String): MessageDigest {
        try {
            return MessageDigest.getInstance(algorithm)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e.message)
        }

    }

}
