package thebergers.adventofcode2020.day03;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class TestDay03 {

	@Test
	void part1() {
		List<String> mapData = List.of(
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
		Forest forest = new Forest(mapData);
		long numTrees = forest.navigatePart1(3, 1);
		assertEquals(7, numTrees);
	}
}
