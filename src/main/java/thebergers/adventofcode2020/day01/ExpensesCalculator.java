package thebergers.adventofcode2020.day01;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import thebergers.adventofcode2020.utils.Utils;

@Slf4j
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

	public static void main(String[] args) throws IOException {
		String fileName = "./src/main/resources/input/day01/input.txt";
		List<String> dataAsStr = Utils.getDataFromFile(fileName);
		List<Integer> dataAsInteger = dataAsStr.stream().map(Integer::parseInt).collect(Collectors.toList());
		ExpensesCalculator expensesCalculator = new ExpensesCalculator(dataAsInteger);
		ExpensesCalculator.Result result = expensesCalculator.calculate();
		LOG.info("value1={}, value2={}, product={}", result.getValue1(), result.getValue2(), result.getProduct());
	}
}
