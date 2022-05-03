package ru.netology.cloudstorage.gzip;


import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        MatcherAssert.assertThat(bytes, IsNot.not(IsEqual.equalTo(gzipBytes)));
    }

    @Test
    void gzipUncompress() {
        assertArrayEquals(bytes, unzipBytes);
    }
}