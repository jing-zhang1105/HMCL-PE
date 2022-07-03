package com.tungsten.hmclpe.launcher.mod.curse;

import android.os.AsyncTask;

import com.google.gson.JsonParseException;
import com.tungsten.hmclpe.launcher.mod.Modpack;
import com.tungsten.hmclpe.launcher.mod.ModpackProvider;
import com.tungsten.hmclpe.utils.gson.JsonUtils;
import com.tungsten.hmclpe.utils.io.IOUtils;
import com.tungsten.hmclpe.utils.io.ZipTools;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;

public final class CurseModpackProvider implements ModpackProvider {
    public static final CurseModpackProvider INSTANCE = new CurseModpackProvider();

    @Override
    public String getName() {
        return "Curse";
    }

    @Override
    public Modpack readManifest(ZipFile zipFile, Path path, Charset charset) throws IOException, JsonParseException {
        final CurseManifest curseManifest = JsonUtils.fromNonNullJson(ZipTools.readTextZipEntry(zipFile, "manifest.json"), CurseManifest.class);
        String str = "No description";
        try {
            ZipArchiveEntry entry = zipFile.getEntry("modlist.html");
            if (entry != null) {
                str = IOUtils.readFullyAsString(zipFile.getInputStream(entry));
            }
        } catch (Throwable unused) {
        }
        return new Modpack(curseManifest.getName(), curseManifest.getAuthor(), curseManifest.getVersion(), curseManifest.getMinecraft().getGameVersion(), str, charset, curseManifest) { // from class: com.tungsten.hmclpe.launcher.mod.curse.CurseModpackProvider.1
            @Override
            public AsyncTask getInstallTask(File file, String str2) {
                return new CurseInstallTask(file, this, curseManifest, str2);
            }
        };
    }
}
