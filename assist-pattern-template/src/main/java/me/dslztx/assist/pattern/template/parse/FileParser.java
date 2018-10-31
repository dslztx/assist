package me.dslztx.assist.pattern.template.parse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class FileParser {

    private static final Logger logger = LoggerFactory.getLogger(FileParser.class);

    protected List<String> buffer = new ArrayList<String>(16);

    private BufferedReader in = null;

    public FileParser(BufferedReader in) {
        this.in = in;
    }

    public List<String> parse() {
        List<String> result = new ArrayList<String>();

        if (in == null) {
            return result;
        }

        try {
            String line = null;
            while ((line = in.readLine()) != null) {
                buffer.add(line);

                if (match(line)) {
                    execute(result);
                }
            }

            lastExecute(result);
        } catch (IOException e) {
            logger.error("", e);
        }

        return result;
    }

    protected abstract void execute(List<String> result);

    protected abstract void lastExecute(List<String> result);

    protected abstract boolean match(String line);
}
