package com.jaspercloud.shipkeeper.support.geohash;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public final class GeoHashAround {

    private static final String BASE_32 = "0123456789bcdefghjkmnpqrstuvwxyz";
    public static final double MinLat = -90;
    public static final double MaxLat = 90;
    public static final double MinLng = -180;
    public static final double MaxLng = 180;

    private static int divideRangeByValue(double value, double[] range) {
        double mid = middle(range);
        if (value >= mid) {
            range[0] = mid;
            return 1;
        } else {
            range[1] = mid;
            return 0;
        }
    }

    private static void divideRangeByBit(int bit, double[] range) {
        double mid = middle(range);
        if (bit > 0) {
            range[0] = mid;
        } else {
            range[1] = mid;
        }
    }

    private static double middle(double[] range) {
        return (range[0] + range[1]) / 2;
    }

    private static String encodeGeohash(double latitude, double longitude, int length) {
        double[] latRange = new double[]{-90.0, 90.0};
        double[] lonRange = new double[]{-180.0, 180.0};
        boolean isEven = true;
        int bit = 0;
        int base32CharIndex = 0;
        StringBuilder geohash = new StringBuilder();
        while (geohash.length() < length) {
            if (isEven) {
                base32CharIndex = (base32CharIndex << 1) | divideRangeByValue(longitude, lonRange);
            } else {
                base32CharIndex = (base32CharIndex << 1) | divideRangeByValue(latitude, latRange);
            }

            isEven = !isEven;

            if (bit < 4) {
                bit++;
            } else {
                geohash.append(BASE_32.charAt(base32CharIndex));
                bit = 0;
                base32CharIndex = 0;
            }
        }

        return geohash.toString();
    }

    private static WGS84Point decodeGeohash(String geohash) {
        double[] latRange = new double[]{MinLat, MaxLat};
        double[] lonRange = new double[]{MinLng, MaxLng};
        boolean isEvenBit = true;

        for (int i = 0; i < geohash.length(); i++) {
            int base32CharIndex = BASE_32.indexOf(geohash.charAt(i));
            for (int j = 4; j >= 0; j--) {
                if (isEvenBit) {
                    divideRangeByBit((base32CharIndex >> j) & 1, lonRange);
                } else {
                    divideRangeByBit((base32CharIndex >> j) & 1, latRange);
                }
                isEvenBit = !isEvenBit;
            }
        }

        return new WGS84Point(middle(latRange), middle(lonRange));
    }

    public static List<String> around(double lat, double lng, int length, int layer) {
        double latUnit = (MaxLat - MinLat) / (1 << 20);
        double lngUnit = (MaxLng - MinLng) / (1 << 20);

        List<String> list = new ArrayList<>();

        //left
        list.add(encodeGeohash(lat + latUnit * layer, lng - lngUnit * layer, length));
        for (int i = 0; i < (layer - 1); i++) {
            list.add(encodeGeohash(lat + latUnit * (layer - 1 - i), lng - lngUnit * layer, length));
        }
        list.add(encodeGeohash(lat, lng - lngUnit * layer, length));
        for (int i = 0; i < (layer - 1); i++) {
            list.add(encodeGeohash(lat - latUnit * (layer - 1 - i), lng - lngUnit * layer, length));
        }
        list.add(encodeGeohash(lat - latUnit * layer, lng - lngUnit * layer, length));

        //center
        for (int i = 0; i < layer; i++) {
            list.add(encodeGeohash(lat + latUnit * layer, lng - lngUnit * (layer - i), length));
        }
        list.add(encodeGeohash(lat + latUnit * layer, lng, length));
        for (int i = 0; i < layer; i++) {
            list.add(encodeGeohash(lat + latUnit * layer, lng + lngUnit * (layer - i), length));
        }
        if (1 == layer) {
            list.add(encodeGeohash(lat, lng, length));
        }
        for (int i = 0; i < layer; i++) {
            list.add(encodeGeohash(lat - latUnit * layer, lng - lngUnit * (layer - i), length));
        }
        list.add(encodeGeohash(lat - latUnit * layer, lng, length));
        for (int i = 0; i < layer; i++) {
            list.add(encodeGeohash(lat - latUnit * layer, lng + lngUnit * (layer - i), length));
        }

        //right
        list.add(encodeGeohash(lat + latUnit * layer, lng + lngUnit * layer, length));
        for (int i = 0; i < (layer - 1); i++) {
            list.add(encodeGeohash(lat + latUnit * (layer - 1 - i), lng + lngUnit * layer, length));
        }
        list.add(encodeGeohash(lat, lng + lngUnit * layer, length));
        for (int i = 0; i < (layer - 1); i++) {
            list.add(encodeGeohash(lat - latUnit * (layer - 1 - i), lng + lngUnit * layer, length));
        }
        list.add(encodeGeohash(lat - latUnit * layer, lng + lngUnit * layer, length));

        return list;
    }
}
