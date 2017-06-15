package com.dslztx.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

import com.dslztx.struct.Process;

public class IOUtils {
    private static final Logger LOG = LoggerFactory.getLogger(IOUtils.class);

    public static void loadFile(File file, Process process) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line = null;
            while ((line = in.readLine()) != null) {
                process.parse(line);
            }
        } catch (Exception e) {
            LOG.error("", e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                LOG.error("", e);
            }
        }
    }

    public static Writer fetchWriter(File file) {
        try {
            return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        } catch (Exception e) {
            LOG.error("", e);
            return null;
        }
    }

    public static void returnWriter(Writer writer) {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (Exception e) {
            LOG.error("", e);
        }
    }
}
