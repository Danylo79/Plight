package dev.dankom.util.steam;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static java.awt.Desktop.getDesktop;
import static java.util.Arrays.asList;

public class SteamGameExecutor {
    public static final String STEAM_INSTALLATION_PATH = "C:\\Program Files (x86)\\Steam\\Steam.exe";
    private static final boolean USE_STEAM_PROTOCOL = true;

    public static void startGameById(String id) throws Exception
    {
        if (USE_STEAM_PROTOCOL)
        {
            Desktop desktop = getDesktop();
            URI steamProtocol = new URI("steam://run/" + id);
            desktop.browse(steamProtocol);
        } else
        {
            startProcess("-applaunch", id);
        }
    }

    private static void startProcess(String... arguments) throws IOException
    {
        ArrayList<String> allArguments = new ArrayList<String>();
        allArguments.add(STEAM_INSTALLATION_PATH);
        List<String> argumentsList = asList(arguments);
        allArguments.addAll(argumentsList);
        ProcessBuilder process = new ProcessBuilder(allArguments);
        process.start();
    }
}
