package com.lihp.common.id;

import java.util.Properties;

/**
 * <b>uuid.hex </b> <br>
 * <br>
 * A <tt>UUIDGenerator</tt> that returns a string of length 32, This string will consist of only hex digits. Optionally, the string may be generated with seperators between each component of the UUID.
 * @see UUIDStringGenerator
 */

public class UuidHexGenerator
    extends UuidGenerator {

    protected String format(int intval) {
        String formatted = Integer.toHexString(intval);
        StringBuffer buf = new StringBuffer("00000000");
        buf.replace(8 - formatted.length(), 8, formatted);
        return buf.toString();
    }

    protected String format(short shortval) {
        String formatted = Integer.toHexString(shortval);
        StringBuffer buf = new StringBuffer("0000");
        buf.replace(4 - formatted.length(), 4, formatted);
        return buf.toString();
    }

    public String generate(String sep) {
        sep = sep == null ? "" : sep;
        return new StringBuffer(36).append(format(getIP())).append(sep).append(format(getJVM())).append(sep)
            .append(format(getHiTime())).append(sep).append(format(getLoTime())).append(sep).append(format(getCount()))
            .toString();
    }

    public String generate() {
        return generate("");
    }

    public static void main(String[] args)
        throws Exception {
        Properties props = new Properties();
        props.setProperty("seperator", "/");
        UuidHexGenerator gen = new UuidHexGenerator();
        UuidHexGenerator gen2 = new UuidHexGenerator();

        for (int i = 0; i < 10; i++) {
            String id = (String) gen.generate("");
            System.out.println(id + ": " + id.length());
            String id2 = (String) gen2.generate("-");
            System.out.println(id2 + ": " + id2.length());
        }
    }
}
