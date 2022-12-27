package com.fyrm.fyrm_service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FyrmServiceApplicationTests {

  @Test
  void contextLoads() {
  }

  @Test
  void shouldPass() {
    int a = 3;
    assertEquals(a, 4);
  }
}
