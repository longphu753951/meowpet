package com.phutl.meowpet.shared.common;

import java.util.NavigableMap;
import java.util.TreeMap;

public enum Rank {
    BRONZE(0),
    SILVER(1000),
    GOLD(3000),
    DIAMOND(5000);

    private final int pointsThreshold;

    private static final NavigableMap<Integer, Rank> rankMap = new TreeMap<>();

    static {
        for (Rank rank : Rank.values()) {
            rankMap.put(rank.pointsThreshold(), rank);
        }
    }

    Rank(int pointsThreshold) {
        this.pointsThreshold = pointsThreshold;
    }

    public int pointsThreshold() {
        return pointsThreshold;
    }

    public static Rank getRankByPoints(int points) {
        return rankMap.floorEntry(points).getValue();
    }
}
