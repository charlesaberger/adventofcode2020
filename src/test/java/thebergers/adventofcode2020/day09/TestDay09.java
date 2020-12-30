package thebergers.adventofcode2020.day09;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import thebergers.adventofcode2020.utils.Utils;

public class TestDay09 {

	@Test
	void part1() throws IOException {
		String fileName = "./src/test/resources/day09/test_input_pt1.txt";
		List<String> data = Utils.getDataFromFile(fileName);
		XmasCracker xmasCracker = new XmasCracker(5, data);
		assertEquals(127L, xmasCracker.analyse());
	}
}
