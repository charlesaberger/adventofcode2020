package thebergers.adventofcode2020.day03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestDay03 {

	private List<String> mapData;

	private Forest forest;

	@BeforeEach
	void setup() {
		mapData = List.of(
			"..##.......",
			"#...#...#..",
			".#....#..#.",
			"..#.#...#.#",
			".#...##..#.",
			"..#.##.....",
			".#.#.#....#",
			".#........#",
			"#.##...#...",
			"#...##....#",
			".#..#...#.#"
		);
		forest = new Forest(mapData);
	}

	@Test
	void part1() {
		long numTrees = forest.navigatePart1(3, 1);
		assertEquals(7, numTrees);
	}

	@Test
	void part2() {
		List<Step> steps = List.of(
			new Step(1, 1),
			new Step(3, 1),
			new Step(5, 1),
			new Step(7, 1),
			new Step(1, 2)
		);
		long totalTrees = forest.navigatePart2(steps);
		assertEquals(336L, totalTrees);
	}
}
