package com.tungsten.hmclpe.launcher.mod.modrinth;

import android.os.AsyncTask;
import com.google.gson.JsonParseException;
import com.tungsten.hmclpe.launcher.mod.Modpack;
import com.tungsten.hmclpe.launcher.mod.ModpackProvider;
import com.tungsten.hmclpe.utils.gson.JsonUtils;
import com.tungsten.hmclpe.utils.io.ZipTools;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import org.apache.commons.compress.archivers.zip.ZipFile;

public final class ModrinthModpackProvider implements ModpackProvider {
    public static final ModrinthModpackProvider INSTANCE = new ModrinthModpackProvider();

    @Override
    public String getName() {
        return "Modrinth";
    }

    @Override
    public Modpack readManifest(ZipFile zipFile, Path path, Charset charset) throws IOException, JsonParseException {
        final ModrinthManifest modrinthManifest = JsonUtils.fromNonNullJson(ZipTools.readTextZipEntry(zipFile, "modrinth.index.json"), ModrinthManifest.class);
        return new Modpack(modrinthManifest.getName(), "", modrinthManifest.getVersionId(), modrinthManifest.getGameVersion(), modrinthManifest.getSummary(), charset, modrinthManifest) {
            @Override
            public AsyncTask getInstallTask(File file, String str) {
                return new ModrinthInstallTask(file, this, modrinthManifest, str);
            }
        };
    }
}
