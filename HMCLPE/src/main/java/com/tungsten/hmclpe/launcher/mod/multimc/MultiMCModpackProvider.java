package com.tungsten.hmclpe.launcher.mod.multimc;

import android.os.AsyncTask;
import com.tungsten.hmclpe.launcher.mod.Modpack;
import com.tungsten.hmclpe.launcher.mod.ModpackProvider;
import com.tungsten.hmclpe.utils.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.stream.Stream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;

public final class MultiMCModpackProvider implements ModpackProvider {
    public static final MultiMCModpackProvider INSTANCE = new MultiMCModpackProvider();

    @Override
    public String getName() {
        return "MultiMC";
    }

    private static boolean testPath(Path path) {
        return Files.exists(path.resolve("instance.cfg"));
    }

    public static Path getRootPath(Path path) throws IOException {
        if (testPath(path)) {
            return path;
        }
        Stream<Path> list = Files.list(path);
        try {
            Path orElseThrow = list.filter(Files::isDirectory).findAny().orElseThrow(() -> new IOException("Not a valid MultiMC modpack"));
            if (testPath(orElseThrow)) {
                list.close();
                return orElseThrow;
            }
            throw new IOException("Not a valid MultiMC modpack");
        } catch (Throwable th) {
            if (list != null) {
                try {
                    list.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private static String getRootEntryName(ZipFile zipFile) throws IOException {
        if (zipFile.getEntry("instance.cfg") != null) {
            return "";
        }
        Enumeration<ZipArchiveEntry> entries = zipFile.getEntries();
        while (entries.hasMoreElements()) {
            String name = entries.nextElement().getName();
            int indexOf = name.indexOf(47);
            if (indexOf >= 0 && name.length() == indexOf + 12 + 1) {
                int i = indexOf + 1;
                if (name.startsWith("instance.cfg", i)) {
                    return name.substring(0, i);
                }
            }
        }
        throw new IOException("Not a valid MultiMC modpack");
    }

    @Override
    public Modpack readManifest(ZipFile zipFile, Path path, Charset charset) throws IOException {
        String rootEntryName = getRootEntryName(zipFile);
        MultiMCManifest readMultiMCModpackManifest = MultiMCManifest.readMultiMCModpackManifest(zipFile, rootEntryName);
        String nameWithoutExtension = rootEntryName.isEmpty() ? FileUtils.getNameWithoutExtension(path) : rootEntryName.substring(0, rootEntryName.length() - 1);
        ZipArchiveEntry entry = zipFile.getEntry(rootEntryName + "instance.cfg");
        if (entry != null) {
            InputStream inputStream = zipFile.getInputStream(entry);
            try {
                final MultiMCInstanceConfiguration multiMCInstanceConfiguration = new MultiMCInstanceConfiguration(nameWithoutExtension, inputStream, readMultiMCModpackManifest);
                Modpack modpack = new Modpack(multiMCInstanceConfiguration.getName(), "", "", multiMCInstanceConfiguration.getGameVersion(), multiMCInstanceConfiguration.getNotes(), charset, multiMCInstanceConfiguration) { // from class: com.tungsten.hmclpe.launcher.mod.multimc.MultiMCModpackProvider.1
                    @Override
                    public AsyncTask getInstallTask(File file, String str) {
                        return new MultiMCModpackInstallTask(file, this, multiMCInstanceConfiguration, str);
                    }
                };
                if (inputStream != null) {
                    inputStream.close();
                }
                return modpack;
            } catch (Throwable th) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } else {
            throw new IOException("`instance.cfg` not found, " + zipFile + " is not a valid MultiMC modpack.");
        }
    }
}
