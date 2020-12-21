package thebergers.adventofcode2020.day01;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class ExpensesCalculator {

	List<Integer> values;

	public Result calculate() {
		for (Integer val : values) {
			Deque<Integer> workingValues = new ArrayDeque<>(values);
			workingValues.remove(val);
			for (Integer workingValue : workingValues) {
				if (val + workingValue == 2020) {
					return new Result(val, workingValue);
				}
			}
		}
		throw new IllegalStateException("No matching values found!");
	}

	@Value
	public static class Result {
		Integer value1;

		Integer value2;

		public Integer getProduct() {
			return value1 * value2;
		}
	}
}
