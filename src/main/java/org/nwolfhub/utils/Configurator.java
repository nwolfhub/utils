package org.nwolfhub.utils;

import org.nwolfhub.easycli.EasyCLI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class Configurator {
    private HashMap<String, String> parsed;
    private String raw;
    private EasyCLI cli;

    public File configFile = new File("config.cfg");

    public Configurator() {

    }

    public Configurator(boolean useEasyCLI) {
        if (useEasyCLI) cli = new EasyCLI();
        readConfig();
    }
    public Configurator(EasyCLI cli) {
        this.cli = new EasyCLI();
        readConfig();
    }

    public Configurator(File configFile) {
        this.configFile = configFile;
        readConfig();
    }
    public Configurator(EasyCLI cli, File configFile) {
        this.cli = cli; this.configFile = configFile;
        readConfig();
    }
    public Configurator(boolean useEasyCLI, File configFile) {
        if (useEasyCLI) this.cli = new EasyCLI();
        this.configFile = configFile;
        readConfig();
    }

    public String getValue(String key) {
        return parsed.get(key);
    }

    public void reloadConfig() {
        readConfig();
    }

    public void createConfigIfNotExists() throws IOException {
        if(!this.configFile.exists()) {
            configFile.createNewFile();
        }
    }
    public void createConfigIfNotExists(String content) throws IOException {
        if(!this.configFile.exists()) {
            configFile.createNewFile();
            try (FileOutputStream outputStream = new FileOutputStream(configFile)) {
                outputStream.write(content.getBytes(StandardCharsets.UTF_8));
            }
        }
    }

    public boolean createConfig() throws IOException {
        if(!this.configFile.exists()) {
            createConfigIfNotExists();
            return false;
        }
        return true;
    }
    public boolean createConfig(String content) throws IOException {
        if(!this.configFile.exists()) {
            createConfigIfNotExists(content);
            return false;
        }
        return true;
    }
    private void readConfig() {
        try(FileInputStream in = new FileInputStream(configFile)) {
            raw = new String(in.readAllBytes());
            parsed = Utils.parseValues(raw, "\n");
            print("Configuration updated");
        } catch (IOException e) {
            print("Failed to read configuration file " + configFile);
            throw new RuntimeException(e);
        }
    }
    public int getEntriesAmount() {
        return parsed.size();
    }

    private void print(String text) {
        if(cli!=null) {
            cli.print(text);
        }
    }
}
