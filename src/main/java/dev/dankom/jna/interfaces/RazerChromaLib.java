package dev.dankom.jna.interfaces;

import com.sun.jna.Library;
import com.sun.jna.Pointer;
import dev.dankom.jna.LibraryLoader;
import dev.dankom.razer.DeviceInfos;
import dev.dankom.type.GUID;

/**
 * Wrapper used by JNA to load Razer Chroma SDK libraries.
 */
public interface RazerChromaLib extends Library {
    int Init();

    int UnInit();

    int CreateKeyboardEffect(int type, Pointer param, Pointer effectID);

    int QueryDevice(GUID struct, DeviceInfos.DeviceInfosStruct infos);

    static LibraryLoader load() {
        String lib = "RzChromaSDK";
        if (System.getProperty("os.arch").contains("64")) {
            lib += "64";
        }
        return LibraryLoader.create(lib, RazerChromaLib.class);
    }
}
