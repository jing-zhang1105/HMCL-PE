package com.tungsten.hmclpe.launcher.mod.curse;

import com.google.gson.annotations.SerializedName;
import com.tungsten.hmclpe.launcher.mod.ModpackManifest;
import com.tungsten.hmclpe.launcher.mod.ModpackProvider;

import java.util.Collections;
import java.util.List;

public final class CurseManifest implements ModpackManifest {
    public static final String MINECRAFT_MODPACK = "minecraftModpack";
    @SerializedName("author")
    private final String author;
    @SerializedName("files")
    private final List<CurseManifestFile> files;
    @SerializedName("manifestType")
    private final String manifestType;
    @SerializedName("manifestVersion")
    private final int manifestVersion;
    @SerializedName("minecraft")
    private final CurseManifestMinecraft minecraft;
    @SerializedName("name")
    private final String name;
    @SerializedName("overrides")
    private final String overrides;
    @SerializedName("version")
    private final String version;

    public CurseManifest() {
        this(MINECRAFT_MODPACK, 1, "", "1.0", "", "overrides", new CurseManifestMinecraft(), Collections.emptyList());
    }

    public CurseManifest(String str, int i, String str2, String str3, String str4, String str5, CurseManifestMinecraft curseManifestMinecraft, List<CurseManifestFile> list) {
        manifestType = str;
        manifestVersion = i;
        name = str2;
        version = str3;
        author = str4;
        overrides = str5;
        minecraft = curseManifestMinecraft;
        files = list;
    }

    public String getManifestType() {
        return manifestType;
    }

    public int getManifestVersion() {
        return manifestVersion;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getAuthor() {
        return author;
    }

    public String getOverrides() {
        return overrides;
    }

    public CurseManifestMinecraft getMinecraft() {
        return minecraft;
    }

    public List<CurseManifestFile> getFiles() {
        return files;
    }

    public com.tungsten.hmclpe.launcher.mod.curse.CurseManifest setFiles(List<CurseManifestFile> list) {
        return new com.tungsten.hmclpe.launcher.mod.curse.CurseManifest(manifestType, manifestVersion, name, version, author, overrides, minecraft, list);
    }

    @Override
    public ModpackProvider getProvider() {
        return CurseModpackProvider.INSTANCE;
    }
}
