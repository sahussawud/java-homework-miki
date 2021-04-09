package com.wongnai.interview.wamup;

import java.util.InputMismatchException;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class DigitalRootTest {
	private DigitalRoot digitalRoot;

	@Before
	public void setUp() {
		digitalRoot = new DigitalRoot();
	}

	@Test
	public void testDigitalRoot() {
		//You are able to add more unit test to make it coverage.
		// Edge Case #1 Valid Partitioning
		Assert.assertThat(digitalRoot.check(0), Matchers.equalTo(0L));
		// Edge Case #2 Valid Partitioning
		Assert.assertThat(digitalRoot.check(1), Matchers.equalTo(1L));
		Assert.assertThat(digitalRoot.check(9), Matchers.equalTo(9L));
		// Edge Case #3 Valid Partitioning, Edge between 9 to 10
		Assert.assertThat(digitalRoot.check(10), Matchers.equalTo(1L));
		Assert.assertThat(digitalRoot.check(934623324L), Matchers.equalTo(9L));
		Assert.assertThat(digitalRoot.check(1235889343324L), Matchers.equalTo(1L));
		Assert.assertThat(digitalRoot.check(493193L), Matchers.equalTo(2L));
		// Edge Case #4 Valid Partitioning, Largest Long that possible to be
		Assert.assertThat(digitalRoot.check(9223372036854775807L), Matchers.equalTo(7L));

	}


	@Test(expected = InputMismatchException.class)
	public void testInvalidInput() {
		digitalRoot.check(-87625L);
		// Edge Case #5 Invalid Partitioning
		digitalRoot.check(-1L);
	}
}
