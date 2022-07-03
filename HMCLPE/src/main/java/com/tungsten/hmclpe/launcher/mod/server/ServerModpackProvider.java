package com.tungsten.hmclpe.launcher.mod.server;

import com.google.gson.JsonParseException;
import com.tungsten.hmclpe.launcher.mod.Modpack;
import com.tungsten.hmclpe.launcher.mod.ModpackProvider;
import com.tungsten.hmclpe.utils.gson.JsonUtils;
import com.tungsten.hmclpe.utils.io.ZipTools;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import org.apache.commons.compress.archivers.zip.ZipFile;

public final class ServerModpackProvider implements ModpackProvider {
    public static final ServerModpackProvider INSTANCE = new ServerModpackProvider();

    @Override
    public String getName() {
        return "Server";
    }

    @Override
    public Modpack readManifest(ZipFile zipFile, Path path, Charset charset) throws IOException, JsonParseException {
        return JsonUtils.fromNonNullJson(ZipTools.readTextZipEntry(zipFile, "server-manifest.json"), ServerModpackManifest.class).toModpack(charset);
    }
}
