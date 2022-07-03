package com.tungsten.hmclpe.launcher.mod.hmcl;

import android.os.AsyncTask;
import com.google.gson.JsonParseException;
import com.tungsten.hmclpe.launcher.game.Version;
import com.tungsten.hmclpe.launcher.mod.Modpack;
import com.tungsten.hmclpe.launcher.mod.ModpackProvider;
import com.tungsten.hmclpe.utils.gson.JsonUtils;
import com.tungsten.hmclpe.utils.io.ZipTools;
import com.tungsten.hmclpe.utils.string.StringUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import org.apache.commons.compress.archivers.zip.ZipFile;

public final class HMCLModpackProvider implements ModpackProvider {
    public static final HMCLModpackProvider INSTANCE = new HMCLModpackProvider();

    @Override
    public String getName() {
        return "HMCL";
    }

    @Override
    public Modpack readManifest(ZipFile zipFile, Path path, Charset charset) throws IOException, JsonParseException {
        Modpack encoding = JsonUtils.fromNonNullJson(ZipTools.readTextZipEntry(zipFile, "modpack.json"), HMCLModpack.class).setEncoding(charset);
        Version version = JsonUtils.fromNonNullJson(ZipTools.readTextZipEntry(zipFile, "minecraft/pack.json"), Version.class);
        if (version.getJar() != null) {
            encoding.setManifest(HMCLModpackManifest.INSTANCE).setGameVersion(version.getJar());
        } else if (!StringUtils.isBlank(encoding.getVersion())) {
            encoding.setManifest(HMCLModpackManifest.INSTANCE);
        } else {
            throw new JsonParseException("Cannot recognize the game version of modpack " + zipFile + ".");
        }
        return encoding;
    }

    public static class HMCLModpack extends Modpack {
        @Override
        public AsyncTask getInstallTask(File file, String str) {
            return new HMCLModpackInstallTask(file, this, str);
        }
    }
}
