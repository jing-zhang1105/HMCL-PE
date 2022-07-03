package com.tungsten.hmclpe.launcher.mod.modrinth;

import com.google.gson.JsonParseException;
import com.tungsten.hmclpe.launcher.mod.ModpackManifest;
import com.tungsten.hmclpe.launcher.mod.ModpackProvider;
import com.tungsten.hmclpe.utils.gson.tools.TolerableValidationException;
import com.tungsten.hmclpe.utils.gson.tools.Validation;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ModrinthManifest implements ModpackManifest, Validation {
    private final Map<String, String> dependencies;
    private final List<File> files;
    private final int formatVersion;
    private final String game;
    private final String name;
    private final String summary;
    private final String versionId;

    public ModrinthManifest(String str, int i, String str2, String str3, String str4, List<File> list, Map<String, String> map) {
        game = str;
        formatVersion = i;
        versionId = str2;
        name = str3;
        summary = str4;
        files = list;
        dependencies = map;
    }

    public String getGame() {
        return game;
    }

    public int getFormatVersion() {
        return formatVersion;
    }

    public String getVersionId() {
        return versionId;
    }

    public String getName() {
        return name;
    }

    public String getSummary() {
        String str = summary;
        return str == null ? "" : str;
    }

    public List<File> getFiles() {
        return files;
    }

    public Map<String, String> getDependencies() {
        return dependencies;
    }

    public String getGameVersion() {
        return dependencies.get("minecraft");
    }

    @Override
    public ModpackProvider getProvider() {
        return ModrinthModpackProvider.INSTANCE;
    }

    @Override
    public void validate() throws JsonParseException, TolerableValidationException {
        Map<String, String> map = dependencies;
        if (map == null || map.get("minecraft") == null) {
            throw new JsonParseException("missing Modrinth.dependencies.minecraft");
        }
    }

    public static class File {
        private final List<URL> downloads;
        private final Map<String, String> env;
        private final int fileSize;
        private final Map<String, String> hashes;
        private final String path;

        public File(String str, Map<String, String> map, Map<String, String> map2, List<URL> list, int i) {
            path = str;
            hashes = map;
            env = map2;
            downloads = list;
            fileSize = i;
        }

        public String getPath() {
            return path;
        }

        public Map<String, String> getHashes() {
            return hashes;
        }

        public Map<String, String> getEnv() {
            return env;
        }

        public List<URL> getDownloads() {
            return downloads;
        }

        public int getFileSize() {
            return fileSize;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            File file = (File) obj;
            return fileSize == file.fileSize && path.equals(file.path) && hashes.equals(file.hashes) && env.equals(file.env) && downloads.equals(file.downloads);
        }

        public int hashCode() {
            return Objects.hash(path, hashes, env, downloads, fileSize);
        }
    }
}
