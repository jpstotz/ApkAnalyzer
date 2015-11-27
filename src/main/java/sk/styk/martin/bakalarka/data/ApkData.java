package sk.styk.martin.bakalarka.data;

import java.util.List;

/**
 * Created by Martin Styk on 23.11.2015.
 */
public class ApkData {


    //basic info
    private String fileName;
    private Long fileSize;
    private Long dexSize;
    private Long arscSize;

    //certificate info
    private List<CertificateData> certificateDatas;

    //manifest info
    private AndroidManifestData androidManifest;

    //hash of every file
    private List<String> fileDigest;


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String name) {
        this.fileName = name;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Long getDexSize() {
        return dexSize;
    }

    public void setDexSize(Long dexSize) {
        this.dexSize = dexSize;
    }

    public Long getArscSize() {
        return arscSize;
    }

    public void setArscSize(Long arscSize) {
        this.arscSize = arscSize;
    }

    public AndroidManifestData getAndroidManifest() {
        return androidManifest;
    }

    public void setAndroidManifest(AndroidManifestData androidManifest) {
        this.androidManifest = androidManifest;
    }

    public List<CertificateData> getCertificateDatas() {
        return certificateDatas;
    }

    public void setCertificateDatas(List<CertificateData> certificateDatas) {
        this.certificateDatas = certificateDatas;
    }

    public List<String> getFileDigest() {
        return fileDigest;
    }

    public void setFileDigest(List<String> fileDigest) {
        this.fileDigest = fileDigest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApkData apkData = (ApkData) o;

        if (fileName != null ? !fileName.equals(apkData.fileName) : apkData.fileName != null) return false;
        if (fileSize != null ? !fileSize.equals(apkData.fileSize) : apkData.fileSize != null) return false;
        if (dexSize != null ? !dexSize.equals(apkData.dexSize) : apkData.dexSize != null) return false;
        if (arscSize != null ? !arscSize.equals(apkData.arscSize) : apkData.arscSize != null) return false;
        if (certificateDatas != null ? !certificateDatas.equals(apkData.certificateDatas) : apkData.certificateDatas != null)
            return false;
        return !(androidManifest != null ? !androidManifest.equals(apkData.androidManifest) : apkData.androidManifest != null);

    }

    @Override
    public int hashCode() {
        int result = fileName != null ? fileName.hashCode() : 0;
        result = 31 * result + (fileSize != null ? fileSize.hashCode() : 0);
        result = 31 * result + (dexSize != null ? dexSize.hashCode() : 0);
        result = 31 * result + (arscSize != null ? arscSize.hashCode() : 0);
        result = 31 * result + (certificateDatas != null ? certificateDatas.hashCode() : 0);
        result = 31 * result + (androidManifest != null ? androidManifest.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ApkData{" +
                "fileName='" + fileName + '\'' +
                ", fileSize=" + fileSize +
                ", dexSize=" + dexSize +
                ", arscSize=" + arscSize +
                ", certificateDatas=" + certificateDatas +
                ", androidManifest=" + androidManifest +
                '}';
    }
}
