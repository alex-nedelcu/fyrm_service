package com.fyrm.fyrm_service.integration_test_setup;

import com.fyrm.fyrm_service.FyrmServiceApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * should serve as base class for every integration test so that test-context is not restarted that often
 */
@Configuration
@Import(FyrmServiceApplication.class)
public class IntegrationTestConfig {

}
