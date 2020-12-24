package thebergers.adventofcode2020.day04;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class TestDay04 {

	@Test
	void part1() throws IOException {
		String fileName = "./src/test/resources/day04/test_input.txt";
		assertEquals(2, PassportValidator.validatePassportFile(fileName, ValidationMode.LOOSE));
	}

	@Test
	void part2() throws IOException {
		String fileName = "./src/test/resources/day04/test_input_pt2.txt";
		assertEquals(4, PassportValidator.validatePassportFile(fileName, ValidationMode.STRICT));
	}
}
