package cde.pg;

import org.junit.jupiter.api.Test;

import java.io.*;

import static java.io.InputStream.nullInputStream;
import static java.io.OutputStream.nullOutputStream;
import static java.io.Reader.nullReader;
import static java.io.Writer.nullWriter;

public class IONullShould {

    @Test
    void J11_use_null_version_for_IO() {
        BufferedInputStream inputStream = new BufferedInputStream(nullInputStream());
        BufferedReader bufferedReader = new BufferedReader(nullReader());
        BufferedWriter bufferedWriter = new BufferedWriter(nullWriter());
        BufferedOutputStream outputStream = new BufferedOutputStream(nullOutputStream());
    }
}
