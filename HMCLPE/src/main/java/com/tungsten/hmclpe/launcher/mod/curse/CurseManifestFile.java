package com.tungsten.hmclpe.launcher.mod.curse;

import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;
import com.tungsten.hmclpe.utils.gson.tools.Validation;
import com.tungsten.hmclpe.utils.io.NetworkUtils;

import java.net.URL;
import java.util.Locale;
import java.util.Objects;

public class CurseManifestFile implements Validation {
    @SerializedName("fileID")
    private final int fileID;
    @SerializedName("fileName")
    private final String fileName;
    @SerializedName("projectID")
    private final int projectID;
    @SerializedName("required")
    private final boolean required;
    @SerializedName("url")
    private final String url;

    public CurseManifestFile() {
        this(0, 0, null, null, true);
    }

    public CurseManifestFile(int i, int i2, String str, String str2, boolean z) {
        projectID = i;
        fileID = i2;
        fileName = str;
        url = str2;
        required = z;
    }

    public int getProjectID() {
        return projectID;
    }

    public int getFileID() {
        return fileID;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isRequired() {
        return required;
    }

    @Override
    public void validate() throws JsonParseException {
        if (projectID == 0 || fileID == 0) {
            throw new JsonParseException("Missing Project ID or File ID.");
        }
    }

    public URL getUrl() {
        String str = url;
        if (str != null) {
            return NetworkUtils.toURL(NetworkUtils.encodeLocation(str));
        }
        if (fileName != null) {
            return NetworkUtils.toURL(NetworkUtils.encodeLocation(String.format(Locale.getDefault(), "https://edge.forgecdn.net/files/%d/%d/%s", fileID / 1000, fileID % 1000, fileName)));
        }
        return null;
    }

    public CurseManifestFile withFileName(String str) {
        return new CurseManifestFile(projectID, fileID, str, url, required);
    }

    public CurseManifestFile withURL(String str) {
        return new CurseManifestFile(projectID, fileID, fileName, str, required);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CurseManifestFile curseManifestFile = (CurseManifestFile) obj;
        return projectID == curseManifestFile.projectID && fileID == curseManifestFile.fileID;
    }

    public int hashCode() {
        return Objects.hash(projectID, fileID);
    }
}
