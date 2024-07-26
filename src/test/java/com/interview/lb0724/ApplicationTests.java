package com.interview.lb0724;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

	@Autowired
	ToolController toolController;

	@Test
	void contextLoads() {
		Assertions.assertThat(toolController).isNot(null);
	}

}
