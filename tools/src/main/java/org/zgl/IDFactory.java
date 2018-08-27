package org.zgl;

public class IDFactory {

    public static long getId(int id) {
        String idInitStr = "201";
        if (id < 10) {
            idInitStr += "00" + id;
        } else if (id >= 10 && id < 100) {
            idInitStr += "0" + id;
        } else if (id >= 100 && id < 1000) {
            idInitStr += +id;
        } else if (id >= 1000 && id < 100000) {
            idInitStr += id;
        }
        return Long.parseLong(idInitStr);
    }
}
