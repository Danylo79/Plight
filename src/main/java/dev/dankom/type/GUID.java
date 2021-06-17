package dev.dankom.type;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class GUID extends Structure implements Structure.ByValue {
    public int data1;
    public short data2;
    public short data3;
    public byte[] data4 = new byte[8];

    @Override
    protected List getFieldOrder() {
        return Arrays.asList("data1", "data2", "data3", "data4");
    }
}
