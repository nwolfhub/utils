package org.nwolfhub;

import org.nwolfhub.easycli.EasyCLI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    private void print(String text) {
        if(cli!=null) {
            cli.print(text);
        }
    }
}
