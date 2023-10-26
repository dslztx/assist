package me.dslztx.assist.util;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import lombok.extern.slf4j.Slf4j;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;

@Slf4j
@ExtendWith(SystemStubsExtension.class)
class VarAssistTest {

    @SystemStub
    private static EnvironmentVariables environmentVariables;

    @BeforeAll
    public static void setEnvironmentVariables() {
        environmentVariables.set("POD_NAME", "pod-name-test-online-5f47c45c5c-2fx2k");
    }

    @Test
    void parse() {
        try {
            Assertions.assertTrue(StringAssist.isBlank(VarAssist.parse("")));
            Assertions.assertTrue(StringAssist.isBlank(VarAssist.parse(null)));
            Assertions.assertTrue(VarAssist.parse("abc").equals("abc"));
            Assertions.assertTrue(VarAssist.parse("${}") == null);
            Assertions.assertTrue(VarAssist.parse("${POD_NAME}").equals("pod-name-test-online-5f47c45c5c-2fx2k"));
            Assertions.assertTrue(VarAssist.parse("${POD_NAME:bbb}").equals("pod-name-test-online-5f47c45c5c-2fx2k"));
            Assertions.assertTrue(VarAssist.parse("${POD2_NAME:helloworld}").equals("" + "helloworld"));
            Assertions.assertTrue(VarAssist.parse("${POD2_NAME:hello:world}").equals("" + "hello:world"));
        } catch (Exception e) {
            log.error("", e);
            fail();
        }
    }
}