package thebergers.adventofcode2020.day01;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
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

	public Result calculatePart1() {
		for (Integer val : values) {
			Deque<Integer> workingValues = new ArrayDeque<>(values);
			workingValues.remove(val);
			for (Integer workingValue : workingValues) {
				if (val + workingValue == 2020) {
					return new Result(Arrays.asList(val, workingValue));
				}
			}
		}
		throw new IllegalStateException("No matching values found!");
	}

	public Result calculatePart2() {
		for (Integer val1 : values) {
			Deque<Integer> workingValues1 = new ArrayDeque<>(values);
			workingValues1.remove(val1);
			for (Integer val2 : workingValues1) {
				Deque<Integer> workingValues2 = new ArrayDeque<>(workingValues1);
				workingValues2.remove(val2);
				for (Integer val3 : workingValues2)
				if (val1 + val2 + val3 == 2020) {
					return new Result(Arrays.asList(val1, val2, val3));
				}
			}
		}
		throw new IllegalStateException("No matching values found!");
	}

	@Value
	public static class Result {
		List<Integer> values;

		public Integer getProduct() {
			return values.stream().reduce(1, Math::multiplyExact);
		}
	}

	public static void main(String[] args) throws IOException {
		String fileName = "./src/main/resources/input/day01/input.txt";
		List<String> dataAsStr = Utils.getDataFromFile(fileName);
		List<Integer> dataAsInteger = dataAsStr.stream().map(Integer::parseInt).collect(Collectors.toList());
		ExpensesCalculator expensesCalculator = new ExpensesCalculator(dataAsInteger);
		ExpensesCalculator.Result resultPart1 = expensesCalculator.calculatePart1();
		LOG.info("Part1: value1={}, value2={}, product={}", resultPart1.getValues().get(0), resultPart1.getValues().get(1), resultPart1.getProduct());
		ExpensesCalculator.Result resultPart2 = expensesCalculator.calculatePart2();
		LOG.info("Part1: value1={}, value2={}, value3={}, product={}", resultPart2.getValues().get(0), resultPart2.getValues().get(1), resultPart2.getValues().get(2), resultPart2.getProduct());
	}
}
