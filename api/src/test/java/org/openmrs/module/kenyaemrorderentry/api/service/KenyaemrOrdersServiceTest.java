package org.openmrs.module.kenyaemrorderentry.api.service;

import org.junit.Test;
import org.junit.Ignore;
import org.openmrs.api.context.Context;
import org.openmrs.test.BaseModuleContextSensitiveTest;

import static org.junit.Assert.*;

@Ignore
public class KenyaemrOrdersServiceTest extends BaseModuleContextSensitiveTest {

    @Test
    @Ignore("Unignore if you want to make the DAO persistable")
    public void shouldSetupContext() {
        assertNotNull(Context.getService(KenyaemrOrdersService.class));
    }
}