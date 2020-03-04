package me.dslztx.assist.text;

import me.dslztx.assist.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum ResourceType {
    URL, QQ, WEIXIN, PHONE, ILLEGAL;

    public static ResourceType parse(String value) {
        if (URL.name().equalsIgnoreCase(value)) {
            return URL;
        } else if (QQ.name().equalsIgnoreCase(value)) {
            return QQ;
        } else if (WEIXIN.name().equalsIgnoreCase(value)) {
            return WEIXIN;
        } else if (PHONE.name().equalsIgnoreCase(value)) {
            return PHONE;
        } else {
            return ILLEGAL;
        }
    }
}

class TypeRegex {
    private ResourceType resourceType;

    private String regex;

    public TypeRegex(ResourceType resourceType, String regex) {
        this.resourceType = resourceType;
        this.regex = regex;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }
}

/**
 * @author dslztx
 */
public class TextProcessAssist {
    public static final Logger logger = LoggerFactory.getLogger(TextProcessAssist.class);

    private static Map<ResourceType, List<Pattern>> map = new HashMap<ResourceType, List<Pattern>>();

    static {

        BufferedReader reader = null;

        try {
            InputStream in = ClassPathResourceAssist.locateInputStream("rule.reg");

            reader = IOAssist.bufferedReader(in, Charset.forName("UTF-8"));

            String line = null;

            while ((line = reader.readLine()) != null) {
                TypeRegex typeRegex = parse(line);


                if (ObjectAssist.isNull(typeRegex)) {
                    logger.error("line {} illegal", line);
                }


                try {
                    Pattern pattern = Pattern.compile(typeRegex.getRegex());

                    if (ObjectAssist.isNull(map.get(typeRegex.getResourceType()))) {
                        map.put(typeRegex.getResourceType(), new ArrayList<Pattern>());
                    }

                    map.get(typeRegex.getResourceType()).add(pattern);
                } catch (Exception e) {
                    logger.error("", e);
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            CloseableAssist.closeQuietly(reader);
        }
    }

    public static String extractURLBasedOnSemantic(String text) {
        if (StringAssist.isBlank(text)) {
            return null;
        }

        List<Pattern> patternList = map.get(ResourceType.URL);
        if (CollectionAssist.isEmpty(patternList)) {
            return null;
        }

        for (Pattern pattern : patternList) {
            Matcher matcher = pattern.matcher(text);

            if (matcher.find()) {
                return matcher.group(1);
            }
        }

        return null;
    }

    private static TypeRegex parse(String line) {
        int pos = line.indexOf(":");

        if (pos == -1) {
            return null;
        }

        ResourceType resourceType = ResourceType.parse(line.substring(0, pos));
        String regex = line.substring(pos + 1);

        if (resourceType == ResourceType.ILLEGAL || StringAssist.isBlank(regex)) {
            return null;
        }

        return new TypeRegex(resourceType, regex);
    }
}