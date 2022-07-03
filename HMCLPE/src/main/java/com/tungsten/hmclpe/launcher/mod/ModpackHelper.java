package com.tungsten.hmclpe.launcher.mod;

import com.tungsten.hmclpe.launcher.mod.curse.CurseModpackProvider;
import com.tungsten.hmclpe.launcher.mod.hmcl.HMCLModpackProvider;
import com.tungsten.hmclpe.launcher.mod.mcbbs.McbbsModpackProvider;
import com.tungsten.hmclpe.launcher.mod.modrinth.ModrinthModpackProvider;
import com.tungsten.hmclpe.launcher.mod.multimc.MultiMCModpackProvider;
import com.tungsten.hmclpe.launcher.mod.server.ServerModpackProvider;
import com.tungsten.hmclpe.utils.Lang;
import com.tungsten.hmclpe.utils.Pair;
import com.tungsten.hmclpe.utils.io.FileUtils;
import com.tungsten.hmclpe.utils.io.ZipTools;

import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ModpackHelper {
    private static final Map<String, ModpackProvider> providers = Lang.mapOf(Pair.pair(CurseModpackProvider.INSTANCE.getName(), CurseModpackProvider.INSTANCE), Pair.pair(McbbsModpackProvider.INSTANCE.getName(), McbbsModpackProvider.INSTANCE), Pair.pair(ModrinthModpackProvider.INSTANCE.getName(), ModrinthModpackProvider.INSTANCE), Pair.pair(MultiMCModpackProvider.INSTANCE.getName(), MultiMCModpackProvider.INSTANCE), Pair.pair(ServerModpackProvider.INSTANCE.getName(), ServerModpackProvider.INSTANCE), Pair.pair(HMCLModpackProvider.INSTANCE.getName(), HMCLModpackProvider.INSTANCE));

    private ModpackHelper() {
    }

    public static ModpackProvider getProviderByType(String str) {
        return providers.get(str);
    }

    public static boolean isFileModpackByExtension(File file) {
        String extension = FileUtils.getExtension(file);
        return ArchiveStreamFactory.ZIP.equals(extension) || "mrpack".equals(extension);
    }

    public static Modpack readModpackManifest(Path path, Charset charset) throws UnsupportedModpackException, ManuallyCreatedModpackException {
        try {
            ZipFile openZipFile = ZipTools.openZipFile(path, charset);
            ModpackProvider[] modpackProviderArr = {McbbsModpackProvider.INSTANCE, CurseModpackProvider.INSTANCE, ModrinthModpackProvider.INSTANCE, HMCLModpackProvider.INSTANCE, MultiMCModpackProvider.INSTANCE, ServerModpackProvider.INSTANCE};
            for (int i = 0; i < 6; i++) {
                try {
                    Modpack readManifest = modpackProviderArr[i].readManifest(openZipFile, path, charset);
                    openZipFile.close();
                    return readManifest;
                } catch (Exception ignored) {
                }
            }
            openZipFile.close();
        } catch (IOException ignored) {
        }
        try {
            findMinecraftDirectoryInManuallyCreatedModpack(path.toString());
            throw new ManuallyCreatedModpackException(path);
        } catch (IOException unused3) {
            throw new UnsupportedModpackException(path.toString());
        }
    }

    public static boolean findMinecraftDirectoryInManuallyCreatedModpack(String str) throws IOException, UnsupportedModpackException {
        ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(str)));
        while (true) {
            ZipEntry nextEntry = zipInputStream.getNextEntry();
            if (nextEntry == null) {
                throw new UnsupportedModpackException(str);
            } else if (nextEntry.isDirectory() && (nextEntry.getName().endsWith(".minecraft/versions/") || nextEntry.getName().equals("versions/"))) {
                return true;
            }
        }
    }
}
