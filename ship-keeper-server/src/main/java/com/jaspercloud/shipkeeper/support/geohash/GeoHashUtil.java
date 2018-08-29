package com.jaspercloud.shipkeeper.support.geohash;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class GeoHashUtil {

    public static GeoHash encodeGeoHash(WGS84Point wgs84Point) {
        GeoHash geoHash = GeoHash.withCharacterPrecision(wgs84Point.getLatitude(), wgs84Point.getLongitude(), 5);
        return geoHash;
    }

    public static String encode(WGS84Point wgs84Point) {
        GeoHash geoHash = encodeGeoHash(wgs84Point);
        String base32 = geoHash.toBase32();
        return base32;
    }

    public static WGS84Point decode(String hash) {
        GeoHash geoHash = GeoHash.fromGeohashString(hash);
        WGS84Point wgs84Point = geoHash.getPoint();
        return wgs84Point;
    }

    public static double distance(WGS84Point wgs84Point1, WGS84Point wgs84Point2) {
        double distance = VincentyGeodesy.distanceInMeters(wgs84Point1, wgs84Point2);
        return distance;
    }

    public static Set<GeoHash> around(GeoHash geoHash, int layer) {
        if (1 == layer) {
            Set<GeoHash> layerList = getAroundList(geoHash, layer);
            return layerList;
        } else {
            Set<GeoHash> layerList = getAroundList(geoHash, layer);
            Set<GeoHash> lastLayerList = getAroundList(geoHash, layer - 1);
            for (GeoHash hash : lastLayerList) {
                layerList.remove(hash);
            }
            return layerList;
        }
    }

    public static List<Long> around(WGS84Point wgs84Point, int layer) {
        GeoHash geoHash = GeoHashUtil.encodeGeoHash(wgs84Point);
        Set<GeoHash> geoHashSet = GeoHashUtil.around(geoHash, layer);
        SortedMap<Double, GeoHash> sortedMap = new TreeMap<>(new Comparator<Double>() {
            @Override
            public int compare(Double left, Double right) {
                return left.compareTo(right);
            }
        });
        for (GeoHash hash : geoHashSet) {
            double distance = GeoHashUtil.distance(wgs84Point, hash.getPoint());
            sortedMap.put(distance, hash);
        }
        List<Long> geoLongList = new ArrayList<>(sortedMap.values())
                .stream()
                .map(new Function<GeoHash, Long>() {
                    @Override
                    public Long apply(GeoHash geoHash) {
                        long value = geoHash.longValue();
                        return value;
                    }
                }).collect(Collectors.toList());
        return geoLongList;
    }

    private static Set<GeoHash> getAroundList(GeoHash geoHash, int layer) {
        Set<GeoHash> resultList = new HashSet<>();
        resultList.add(geoHash);
        for (int i = 0; i < layer; i++) {
            Set<GeoHash> tmp = new HashSet<>();
            for (GeoHash hash : resultList) {
                List<GeoHash> list = hash.getAdjacentList();
                tmp.addAll(list);
            }
            resultList.addAll(tmp);
        }

        return resultList;
    }
}
