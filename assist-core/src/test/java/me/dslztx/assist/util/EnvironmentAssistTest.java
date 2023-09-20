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
class EnvironmentAssistTest {

    @SystemStub
    private static EnvironmentVariables environmentVariables;

    @BeforeAll
    public static void setEnvironmentVariables() {
        environmentVariables.set("POD_NAME", "pod-name-test-online-5f47c45c5c-2fx2k");
    }

    @Test
    public void test0() {
        try {
            Assertions.assertTrue(EnvironmentAssist.obtainShortMachineName().equals("2fx2k"));
            Assertions
                .assertTrue(EnvironmentAssist.obtainLongMachineName().equals("pod-name-test-online-5f47c45c5c-2fx2k"));
        } catch (Exception e) {
            log.error("", e);
            fail();
        }
    }

}