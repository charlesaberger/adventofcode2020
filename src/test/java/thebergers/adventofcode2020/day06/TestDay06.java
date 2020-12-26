package thebergers.adventofcode2020.day06;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class TestDay06 {

	@Test
	void part1() throws IOException {
		String fileName = "./src/test/resources/day06/test_input_pt1.txt";
		assertEquals(11, CustomsDeclaration.countYesAnswers(fileName));

	}
}
