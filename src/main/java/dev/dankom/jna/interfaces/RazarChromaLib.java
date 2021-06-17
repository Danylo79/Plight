package dev.dankom.jna.interfaces;

import com.sun.jna.Library;
import com.sun.jna.Pointer;
import dev.dankom.jna.LibraryLoader;

/**
 * Wrapper used by JNA to load Razer Chroma SDK libraries.
 */
public interface RazarChromaLib extends Library {
    int Init();

    int UnInit();

    int CreateKeyboardEffect(int type, Pointer param, Pointer effectID);

    static LibraryLoader load() {
        String libName = "RzChromaSDK";
        if(System.getProperty("os.arch").contains("64")) {
            libName += "64";
        }
        return LibraryLoader.create(libName, RazarChromaLib.class);
    }
}
