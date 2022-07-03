package com.tungsten.hmclpe.launcher.mod;

import com.google.gson.JsonParseException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import org.apache.commons.compress.archivers.zip.ZipFile;

public interface ModpackProvider {
    String getName();

    Modpack readManifest(ZipFile zipFile, Path path, Charset charset) throws IOException, JsonParseException;
}
