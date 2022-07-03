package com.tungsten.hmclpe.launcher.mod.server;

import android.os.AsyncTask;
import com.google.gson.JsonParseException;
import com.tungsten.hmclpe.launcher.download.LibraryAnalyzer;
import com.tungsten.hmclpe.launcher.mod.Modpack;
import com.tungsten.hmclpe.launcher.mod.ModpackConfiguration;
import com.tungsten.hmclpe.launcher.mod.ModpackManifest;
import com.tungsten.hmclpe.launcher.mod.ModpackProvider;
import com.tungsten.hmclpe.utils.gson.tools.TolerableValidationException;
import com.tungsten.hmclpe.utils.gson.tools.Validation;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

public class ServerModpackManifest implements ModpackManifest, Validation {
    private final List<Addon> addons;
    private final String author;
    private final String description;
    private final String fileApi;
    private final List<ModpackConfiguration.FileInformation> files;
    private final String name;
    private final String version;

    public ServerModpackManifest() {
        this("", "", "", "", "", Collections.emptyList(), Collections.emptyList());
    }

    public ServerModpackManifest(String str, String str2, String str3, String str4, String str5, List<ModpackConfiguration.FileInformation> list, List<Addon> list2) {
        name = str;
        author = str2;
        version = str3;
        description = str4;
        fileApi = str5;
        files = list;
        addons = list2;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getVersion() {
        return version;
    }

    public String getDescription() {
        return description;
    }

    public String getFileApi() {
        return fileApi;
    }

    public List<ModpackConfiguration.FileInformation> getFiles() {
        return files;
    }

    public List<Addon> getAddons() {
        return addons;
    }

    @Override
    public ModpackProvider getProvider() {
        return ServerModpackProvider.INSTANCE;
    }

    @Override
    public void validate() throws JsonParseException, TolerableValidationException {
        if (fileApi == null) {
            throw new JsonParseException("ServerModpackManifest.fileApi cannot be blank");
        } else if (files == null) {
            throw new JsonParseException("ServerModpackManifest.files cannot be null");
        }
    }

    public static final class Addon {
        private final String id;
        private final String version;

        public Addon() {
            this("", "");
        }

        public Addon(String str, String str2) {
            id = str;
            version = str2;
        }

        public String getId() {
            return id;
        }

        public String getVersion() {
            return version;
        }
    }

    public Modpack toModpack(Charset arg11) throws IOException {
        String v6 = addons.stream().filter((Addon addon) -> LibraryAnalyzer.LibraryType.MINECRAFT.getPatchId().equals(addon.id)).findAny().orElseThrow(() -> new IOException("Cannot find game version")).getVersion();
        return new Modpack(name, author, version, v6, description, arg11, this) {
            @Override
            public AsyncTask getInstallTask(File arg3, String arg4) {
                return new ServerModpackLocalInstallTask(arg3, this, ServerModpackManifest.this, arg4);
            }
        };
    }
}
