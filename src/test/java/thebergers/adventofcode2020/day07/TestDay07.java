package thebergers.adventofcode2020.day07;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import com.google.common.graph.ValueGraph;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TestDay07 {

	@Test
	void part1() throws IOException {
		String fileName = "./src/test/resources/day07/test_input_pt1.txt";
		LuggageProcessor luggageProcessor = LuggageProcessor.getInstance(fileName);
		ValueGraph<String, Long> graph = luggageProcessor.getBags();
		List.of("light red", "dark orange", "bright white", "muted yellow",
			"shiny gold", "dark olive", "vibrant plum", "faded blue", "dotted black")
			.forEach(colour -> assertTrue(graph.nodes().contains(colour), String.format("Contains %s", colour)));
		assertFalse(graph.edges().isEmpty());
		assertEquals(4L, luggageProcessor.getApplicableBags("shiny gold"));
	}

	@Test
	void part2a() throws IOException {
		String fileName = "./src/test/resources/day07/test_input_pt1.txt";
		LuggageProcessor luggageProcessor = LuggageProcessor.getInstance(fileName);
		assertEquals(32L, luggageProcessor.countRequiredBags("shiny gold"));
	}

	@Test
	void part2b() throws IOException {
		String fileName = "./src/test/resources/day07/test_input_pt2.txt";
		LuggageProcessor luggageProcessor = LuggageProcessor.getInstance(fileName);
		assertEquals(126L, luggageProcessor.countRequiredBags("shiny gold"));
	}
}
