package com.tungsten.hmclpe.launcher.mod.multimc;

import com.tungsten.hmclpe.launcher.mod.ModpackManifest;
import com.tungsten.hmclpe.launcher.mod.ModpackProvider;
import com.tungsten.hmclpe.utils.Lang;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Properties;

public final class MultiMCInstanceConfiguration implements ModpackManifest {
    private final boolean autoCloseConsole;
    private final boolean fullscreen;
    private final String gameVersion;
    private final Integer height;
    private final String instanceType;
    private final String javaPath;
    private final String jvmArgs;
    private final Integer maxMemory;
    private final Integer minMemory;
    private final MultiMCManifest mmcPack;
    private final String name;
    private final String notes;
    private final boolean overrideCommands;
    private final boolean overrideConsole;
    private final boolean overrideJavaArgs;
    private final boolean overrideJavaLocation;
    private final boolean overrideMemory;
    private final boolean overrideWindow;
    private final Integer permGen;
    private final String postExitCommand;
    private final String preLaunchCommand;
    private final boolean showConsole;
    private final boolean showConsoleOnError;
    private final Integer width;
    private final String wrapperCommand;

    public MultiMCInstanceConfiguration(String str, InputStream inputStream, MultiMCManifest multiMCManifest) throws IOException {
        Properties properties = new Properties();
        properties.load(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        mmcPack = multiMCManifest;
        instanceType = properties.getProperty("InstanceType");
        autoCloseConsole = Boolean.parseBoolean(properties.getProperty("AutoCloseConsole"));
        gameVersion = multiMCManifest != null ? multiMCManifest
                .getComponents()
                .stream()
                .filter((MultiMCManifest.MultiMCManifestComponent component) ->
                        "net.minecraft".equals(component.getUid())
                )
                .findAny()
                .orElseThrow(() ->
                        new IOException("Malformed mmc-pack.json")
                )
                .getVersion() : properties.getProperty("IntendedVersion");
        javaPath = properties.getProperty("JavaPath");
        jvmArgs = properties.getProperty("JvmArgs");
        fullscreen = Boolean.parseBoolean(properties.getProperty("LaunchMaximized"));
        maxMemory = Lang.toIntOrNull(properties.getProperty("MaxMemAlloc"));
        minMemory = Lang.toIntOrNull(properties.getProperty("MinMemAlloc"));
        height = Lang.toIntOrNull(properties.getProperty("MinecraftWinHeight"));
        width = Lang.toIntOrNull(properties.getProperty("MinecraftWinWidth"));
        overrideCommands = Boolean.parseBoolean(properties.getProperty("OverrideCommands"));
        overrideConsole = Boolean.parseBoolean(properties.getProperty("OverrideConsole"));
        overrideJavaArgs = Boolean.parseBoolean(properties.getProperty("OverrideJavaArgs"));
        overrideJavaLocation = Boolean.parseBoolean(properties.getProperty("OverrideJavaLocation"));
        overrideMemory = Boolean.parseBoolean(properties.getProperty("OverrideMemory"));
        overrideWindow = Boolean.parseBoolean(properties.getProperty("OverrideWindow"));
        permGen = Lang.toIntOrNull(properties.getProperty("PermGen"));
        postExitCommand = properties.getProperty("PostExitCommand");
        preLaunchCommand = properties.getProperty("PreLaunchCommand");
        showConsole = Boolean.parseBoolean(properties.getProperty("ShowConsole"));
        showConsoleOnError = Boolean.parseBoolean(properties.getProperty("ShowConsoleOnError"));
        wrapperCommand = properties.getProperty("WrapperCommand");
        name = str;
        notes = Optional.ofNullable(properties.getProperty("notes")).orElse("");
    }

    public MultiMCInstanceConfiguration(String str, String str2, String str3, Integer num, String str4, String str5, String str6, String str7, String str8, String str9, boolean z, Integer num2, Integer num3, Integer num4, Integer num5, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10) {
        instanceType = str;
        name = str2;
        gameVersion = str3;
        permGen = num;
        wrapperCommand = str4;
        preLaunchCommand = str5;
        postExitCommand = str6;
        notes = str7;
        javaPath = str8;
        jvmArgs = str9;
        fullscreen = z;
        width = num2;
        height = num3;
        maxMemory = num4;
        minMemory = num5;
        showConsole = z2;
        showConsoleOnError = z3;
        autoCloseConsole = z4;
        overrideMemory = z5;
        overrideJavaLocation = z6;
        overrideJavaArgs = z7;
        overrideConsole = z8;
        overrideCommands = z9;
        overrideWindow = z10;
        mmcPack = null;
    }

    public String getInstanceType() {
        return instanceType;
    }

    public String getName() {
        return name;
    }

    public String getGameVersion() {
        return gameVersion;
    }

    public Integer getPermGen() {
        return permGen;
    }

    public String getWrapperCommand() {
        return wrapperCommand;
    }

    public String getPreLaunchCommand() {
        return preLaunchCommand;
    }

    public String getPostExitCommand() {
        return postExitCommand;
    }

    public String getNotes() {
        return notes;
    }

    public String getJavaPath() {
        return javaPath;
    }

    public String getJvmArgs() {
        return jvmArgs;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getMaxMemory() {
        return maxMemory;
    }

    public Integer getMinMemory() {
        return minMemory;
    }

    public boolean isShowConsole() {
        return showConsole;
    }

    public boolean isShowConsoleOnError() {
        return showConsoleOnError;
    }

    public boolean isAutoCloseConsole() {
        return autoCloseConsole;
    }

    public boolean isOverrideMemory() {
        return overrideMemory;
    }

    public boolean isOverrideJavaLocation() {
        return overrideJavaLocation;
    }

    public boolean isOverrideJavaArgs() {
        return overrideJavaArgs;
    }

    public boolean isOverrideConsole() {
        return overrideConsole;
    }

    public boolean isOverrideCommands() {
        return overrideCommands;
    }

    public boolean isOverrideWindow() {
        return overrideWindow;
    }

    public Properties toProperties() {
        Properties properties = new Properties();
        if (instanceType != null) {
            properties.setProperty("InstanceType", instanceType);
        }
        properties.setProperty("AutoCloseConsole", Boolean.toString(autoCloseConsole));
        if (gameVersion != null) {
            properties.setProperty("IntendedVersion", gameVersion);
        }
        if (javaPath != null) {
            properties.setProperty("JavaPath", javaPath);
        }
        if (jvmArgs != null) {
            properties.setProperty("JvmArgs", jvmArgs);
        }
        properties.setProperty("LaunchMaximized", Boolean.toString(fullscreen));
        if (maxMemory != null) {
            properties.setProperty("MaxMemAlloc", Integer.toString(maxMemory));
        }
        if (minMemory != null) {
            properties.setProperty("MinMemAlloc", Integer.toString(minMemory));
        }
        if (height != null) {
            properties.setProperty("MinecraftWinHeight", Integer.toString(height));
        }
        if (width != null) {
            properties.setProperty("MinecraftWinWidth", Integer.toString(width));
        }
        properties.setProperty("OverrideCommands", Boolean.toString(overrideCommands));
        properties.setProperty("OverrideConsole", Boolean.toString(overrideConsole));
        properties.setProperty("OverrideJavaArgs", Boolean.toString(overrideJavaArgs));
        properties.setProperty("OverrideJavaLocation", Boolean.toString(overrideJavaLocation));
        properties.setProperty("OverrideMemory", Boolean.toString(overrideMemory));
        properties.setProperty("OverrideWindow", Boolean.toString(overrideWindow));
        if (permGen != null) {
            properties.setProperty("PermGen", Integer.toString(permGen));
        }
        if (postExitCommand != null) {
            properties.setProperty("PostExitCommand", postExitCommand);
        }
        if (preLaunchCommand != null) {
            properties.setProperty("PreLaunchCommand", preLaunchCommand);
        }
        properties.setProperty("ShowConsole", Boolean.toString(showConsole));
        properties.setProperty("ShowConsoleOnError", Boolean.toString(showConsoleOnError));
        if (wrapperCommand != null) {
            properties.setProperty("WrapperCommand", wrapperCommand);
        }
        if (name != null) {
            properties.setProperty("name", name);
        }
        if (notes != null) {
            properties.setProperty("notes", notes);
        }
        return properties;
    }

    public MultiMCManifest getMmcPack() {
        return mmcPack;
    }

    @Override
    public ModpackProvider getProvider() {
        return MultiMCModpackProvider.INSTANCE;
    }
}
