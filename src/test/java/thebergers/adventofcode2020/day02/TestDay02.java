package thebergers.adventofcode2020.day02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;

public class TestDay02 {

	private PasswordPhilosophy passwordPhilosophy;

	private List<String> part1Data = List.of(
		"1-3 a: abcde",
		"1-3 b: cdefg",
		"2-9 c: ccccccccc"
	);

	@BeforeEach
	void setup() {

	}

	@ParameterizedTest
	@CsvSource({
		"'1-3 a: abcde',true",
		"'1-3 b: cdefg', false",
		"'2-9 c: ccccccccc', true",
		"'10-14 c: sccctgfcchghccccc', true"
	})
	void part1Validate(String parameters, String expectedResultStr) {
		boolean expectedResult = Boolean.parseBoolean(expectedResultStr);
		passwordPhilosophy = new PasswordPhilosophy(parameters);
		assertEquals(expectedResult, passwordPhilosophy.validate());
	}

	@Test
	void part1CountValid() {
		assertEquals(2L, PasswordPhilosophy.validateList(part1Data));
	}

	/*Stream<Arguments> part1ValidateData() {
		return Stream.of(
			part1Data().stream()
			.map(str -> arguments(str, ))
		)
	}*/

	/*private List<String> part1Data() {
		return List.of(
			"1-3 a: abcde",
			"1-3 b: cdefg",
			"2-9 c: ccccccccc"
		);
	}*/
}
