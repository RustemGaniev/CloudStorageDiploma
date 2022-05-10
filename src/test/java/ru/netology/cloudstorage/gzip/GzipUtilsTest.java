package ru.netology.cloudstorage.gzip;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GzipUtilsTest {

    private byte[] bytes;
    private byte[] gzipBytes;
    private byte[] unzipBytes;

    @BeforeEach
    void setUp() {
        String byteExample = "Example";
        bytes = byteExample.getBytes(StandardCharsets.UTF_8);
        gzipBytes = GzipUtils.gzipCompress(bytes);
        unzipBytes = GzipUtils.gzipUncompress(gzipBytes);
    }

    @Test
    void isGZIPStream() {
        boolean gzipStream = GzipUtils.isGZIPStream(gzipBytes);
        assertTrue(gzipStream);
    }

    @Test
    void gzipCompress() {
        assertFalse(Arrays.equals(bytes, gzipBytes));
    }

    @Test
    void gzipUncompress() {
        assertArrayEquals(bytes, unzipBytes);
    }
}