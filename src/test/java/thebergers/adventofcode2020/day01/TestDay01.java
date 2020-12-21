package thebergers.adventofcode2020.day01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TestDay01 {

	private List<Integer> values;

	private ExpensesCalculator expensesCalculator;

	@BeforeEach
	void setup() {
		values = List.of(
			1721,
			979,
			366,
			299,
			675,
			1456
		);
		expensesCalculator = new ExpensesCalculator(values);
	}

	@Test
	void part1() {
		Integer expected = 514579;
		ExpensesCalculator.Result result = expensesCalculator.calculatePart1();
		List<Integer> resultVals = result.getValues();
		assertEquals(2, resultVals.size());
		assertEquals(1721, resultVals.get(0));
		assertEquals(299, resultVals.get(1));
		assertEquals(expected, result.getProduct());
	}

	@Test
	void part2() {
		Integer expected = 241861950;
		ExpensesCalculator.Result result = expensesCalculator.calculatePart2();
		List<Integer> resultVals = result.getValues();
		assertEquals(3, resultVals.size());
		assertEquals(979, resultVals.get(0));
		assertEquals(366, resultVals.get(1));
		assertEquals(675, resultVals.get(2));
		assertEquals(expected, result.getProduct());
	}
}
