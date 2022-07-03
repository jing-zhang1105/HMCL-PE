package com.tungsten.hmclpe.launcher.mod;

import com.google.gson.JsonParseException;
import com.tungsten.hmclpe.utils.gson.tools.Validation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ModpackConfiguration<T> implements Validation {
    private final T manifest;
    private final String name;
    private final List<FileInformation> overrides;
    private final String type;
    private final String version;

    public ModpackConfiguration() {
        this(null, null, "", null, Collections.emptyList());
    }

    public ModpackConfiguration(T t, String str, String str2, String str3, List<FileInformation> list) {
        manifest = t;
        type = str;
        name = str2;
        version = str3;
        overrides = new ArrayList<>(list);
    }

    public T getManifest() {
        return manifest;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public ModpackConfiguration<T> setManifest(T t) {
        return new ModpackConfiguration<>(t, type, name, version, overrides);
    }

    public ModpackConfiguration<T> setOverrides(List<FileInformation> list) {
        return new ModpackConfiguration<>(manifest, type, name, version, list);
    }

    public ModpackConfiguration<T> setVersion(String str) {
        return new ModpackConfiguration<>(manifest, type, name, str, overrides);
    }

    public List<FileInformation> getOverrides() {
        return Collections.unmodifiableList(overrides);
    }

    @Override
    public void validate() throws JsonParseException {
        if (manifest == null) {
            throw new JsonParseException("MinecraftInstanceConfiguration missing `manifest`");
        } else if (type == null) {
            throw new JsonParseException("MinecraftInstanceConfiguration missing `type`");
        }
    }

    public static class FileInformation implements Validation {
        private final String downloadURL;
        private final String hash;
        private final String path;

        public FileInformation() {
            this(null, null);
        }

        public FileInformation(String str, String str2) {
            this(str, str2, null);
        }

        public FileInformation(String str, String str2, String str3) {
            path = str;
            hash = str2;
            downloadURL = str3;
        }

        public String getPath() {
            return path;
        }

        public String getDownloadURL() {
            return downloadURL;
        }

        public String getHash() {
            return hash;
        }

        @Override
        public void validate() throws JsonParseException {
            if (path == null) {
                throw new JsonParseException("FileInformation missing `path`.");
            } else if (hash == null) {
                throw new JsonParseException("FileInformation missing file hash code.");
            }
        }
    }
}
