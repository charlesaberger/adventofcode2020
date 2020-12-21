package thebergers.adventofcode2020.day01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class TestDay01 {

	@Test
	void validate_expenses() {
		List<Integer> values = List.of(
			1721,
			979,
			366,
			299,
			675,
			1456
		);
		Integer expected = 514579;
		ExpensesCalculator expensesCalculator = new ExpensesCalculator(values);
		ExpensesCalculator.Result result = expensesCalculator.calculate();
		assertEquals(1721, result.getValue1());
		assertEquals(299, result.getValue2());
		assertEquals(expected, result.getProduct());
	}
}
