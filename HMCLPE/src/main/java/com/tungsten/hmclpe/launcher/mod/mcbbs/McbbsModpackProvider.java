package com.tungsten.hmclpe.launcher.mod.mcbbs;

import com.google.gson.JsonParseException;
import com.tungsten.hmclpe.launcher.mod.Modpack;
import com.tungsten.hmclpe.launcher.mod.ModpackProvider;
import com.tungsten.hmclpe.utils.gson.JsonUtils;
import com.tungsten.hmclpe.utils.io.IOUtils;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;

public final class McbbsModpackProvider implements ModpackProvider {
    public static final McbbsModpackProvider INSTANCE = new McbbsModpackProvider();

    @Override
    public String getName() {
        return "Mcbbs";
    }

    private static Modpack fromManifestFile(String str, Charset charset) throws IOException, JsonParseException {
        return JsonUtils.fromNonNullJson(str, McbbsModpackManifest.class).toModpack(charset);
    }

    @Override
    public Modpack readManifest(ZipFile zipFile, Path path, Charset charset) throws IOException, JsonParseException {
        ZipArchiveEntry entry = zipFile.getEntry("mcbbs.packmeta");
        if (entry != null) {
            return fromManifestFile(IOUtils.readFullyAsString(zipFile.getInputStream(entry)), charset);
        }
        ZipArchiveEntry entry2 = zipFile.getEntry("manifest.json");
        if (entry2 != null) {
            return fromManifestFile(IOUtils.readFullyAsString(zipFile.getInputStream(entry2)), charset);
        }
        throw new IOException("`mcbbs.packmeta` or `manifest.json` cannot be found");
    }
}
