package org.folderTests.enums;

import java.util.Arrays;

public enum FolderSize {

    //testowe założenia
    SMALL(0, 5),
    MEDIUM(6, 10),
    LARGE(11, Integer.MAX_VALUE);

    private final double minMB;
    private final double maxMB;
    private final long convertToMB = 1048576;

    FolderSize(double minMB, double maxMB) {
        this.minMB = minMB;
        this.maxMB = maxMB;
    }

    public boolean compare(long value) {
        return value >= minMB && value <= maxMB;
    }

    public static String getSizeToString(long value) {
        return Arrays.stream(values())
                .filter(v -> v.compare(value / v.convertToMB))
                .findFirst()
                .map(Enum::name)
                .orElseThrow(() ->
                        new IllegalArgumentException("Bad Value")
                );
    }
}
