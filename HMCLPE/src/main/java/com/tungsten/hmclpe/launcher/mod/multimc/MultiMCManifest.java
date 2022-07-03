package com.tungsten.hmclpe.launcher.mod.multimc;

import com.google.gson.annotations.SerializedName;
import com.tungsten.hmclpe.utils.gson.JsonUtils;
import com.tungsten.hmclpe.utils.io.IOUtils;
import java.io.IOException;
import java.util.List;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;

public final class MultiMCManifest {
    @SerializedName("components")
    private final List<MultiMCManifestComponent> components;
    @SerializedName("formatVersion")
    private final int formatVersion;

    public MultiMCManifest(int i, List<MultiMCManifestComponent> list) {
        formatVersion = i;
        components = list;
    }

    public int getFormatVersion() {
        return formatVersion;
    }

    public List<MultiMCManifestComponent> getComponents() {
        return components;
    }

    public static MultiMCManifest readMultiMCModpackManifest(ZipFile zipFile, String str) throws IOException {
        ZipArchiveEntry entry = zipFile.getEntry(str + "mmc-pack.json");
        if (entry == null) {
            return null;
        }
        MultiMCManifest multiMCManifest = JsonUtils.fromNonNullJson(IOUtils.readFullyAsString(zipFile.getInputStream(entry)), MultiMCManifest.class);
        if (multiMCManifest.getComponents() != null) {
            return multiMCManifest;
        }
        throw new IOException("mmc-pack.json malformed.");
    }

    public static final class MultiMCManifestCachedRequires {
        @SerializedName("equals")
        private final String equalsVersion;
        @SerializedName("suggests")
        private final String suggests;
        @SerializedName("uid")
        private final String uid;

        public MultiMCManifestCachedRequires(String str, String str2, String str3) {
            equalsVersion = str;
            uid = str2;
            suggests = str3;
        }

        public String getEqualsVersion() {
            return equalsVersion;
        }

        public String getUid() {
            return uid;
        }

        public String getSuggests() {
            return suggests;
        }
    }

    public static final class MultiMCManifestComponent {
        @SerializedName("cachedName")
        private final String cachedName;
        @SerializedName("cachedRequires")
        private final List<MultiMCManifestCachedRequires> cachedRequires;
        @SerializedName("cachedVersion")
        private final String cachedVersion;
        @SerializedName("dependencyOnly")
        private final boolean dependencyOnly;
        @SerializedName("important")
        private final boolean important;
        @SerializedName("uid")
        private final String uid;
        @SerializedName("version")
        private final String version;

        public MultiMCManifestComponent(boolean z, boolean z2, String str, String str2) {
            this(null, null, null, z, z2, str, str2);
        }

        public MultiMCManifestComponent(String str, List<MultiMCManifestCachedRequires> list, String str2, boolean z, boolean z2, String str3, String str4) {
            cachedName = str;
            cachedRequires = list;
            cachedVersion = str2;
            important = z;
            dependencyOnly = z2;
            uid = str3;
            version = str4;
        }

        public String getCachedName() {
            return cachedName;
        }

        public List<MultiMCManifestCachedRequires> getCachedRequires() {
            return cachedRequires;
        }

        public String getCachedVersion() {
            return cachedVersion;
        }

        public boolean isImportant() {
            return important;
        }

        public boolean isDependencyOnly() {
            return dependencyOnly;
        }

        public String getUid() {
            return uid;
        }

        public String getVersion() {
            return version;
        }
    }
}
