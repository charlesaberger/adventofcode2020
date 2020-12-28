package thebergers.adventofcode2020.day07;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.google.common.graph.ImmutableValueGraph;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Value
public class LuggageProcessor {

	ValueGraph<String, Long> bags;

	public static LuggageProcessor getInstance(String fileName) throws IOException {
		ImmutableValueGraph.Builder<String, Long> builder = ValueGraphBuilder.directed().<String, Long>immutable();
		populateGraph(builder, fileName);
		return new LuggageProcessor(builder.build());
	}

	public static void main(String[] args) throws IOException {
		String fileName = "./src/main/resources/input/day07/input.txt";
		LuggageProcessor luggageProcessor = LuggageProcessor.getInstance(fileName);
		LOG.info("Applicable bags = {}", luggageProcessor.getApplicableBags("shiny gold"));
		LOG.info("Bags inside = {}", luggageProcessor.countRequiredBags("shiny gold"));
	}

	public long getApplicableBags(String colour) {
		Set<String> colours = new HashSet<>();
		processPredecessors(colour, colours);
		return colours.size();
	}

	public long countRequiredBags(String colour) {
		return countBags(colour, 0L) - 1L;
	}

	private static void populateGraph(ImmutableValueGraph.Builder<String, Long> builder, String fileName) throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			while (true) {
				String rule = reader.readLine();
				if (rule == null) {
					break;
				}
				parseLine(rule, builder);
			}
		};
	}

	private static void parseLine(String rule, ImmutableValueGraph.Builder<String, Long> builder) {
		String[] bagSpecs = rule.split(" contain ");
		String parent = bagSpecs[0].replace(" bags", "");
		builder.addNode(parent);
		String childrenStr = bagSpecs[1];
		if (!childrenStr.equals("no other bags."))  {
			childrenStr = childrenStr.replace(" bags", "").replace(" bag", "").replace(".", "");
			String[] children = childrenStr.split(", ");
			for (String child : children) {
				Long qty = Long.parseLong(String.valueOf(child.charAt(0)));
				String childColour = child.substring(2);
				builder.addNode(childColour);
				builder.putEdgeValue(parent, childColour, qty);
			}
		}
	}

	private void processPredecessors(String colour, Set<String> colours) {
		Set<String> predecessors = bags.predecessors(colour);
		if (predecessors.isEmpty()) {
			return;
		}
		for (String predecessor : predecessors) {
			if (!colours.contains(predecessor)) {
				processPredecessors(predecessor, colours);
				colours.add(predecessor);
			}
		}
	}

	private Long countBags(String colour, Long runningTotal) {
		Set<String> enclosed = bags.successors(colour);
		if (enclosed.isEmpty()) {
			return ++runningTotal;
		}
		for (String successor : enclosed) {
			Long qty = bags.edgeValue(colour, successor).orElse(0L);
			runningTotal += qty * countBags(successor, 0L);
		}
		return ++runningTotal;
	}
}
