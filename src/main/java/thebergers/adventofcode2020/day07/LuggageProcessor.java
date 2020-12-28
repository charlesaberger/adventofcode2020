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

@Value
public class LuggageProcessor {

	ValueGraph<String, Integer> bags;

	public static LuggageProcessor getInstance(String fileName) throws IOException {
		ImmutableValueGraph.Builder<String, Integer> builder = ValueGraphBuilder.directed().<String, Integer>immutable();
		populateGraph(builder, fileName);
		return new LuggageProcessor(builder.build());
	}

	private static void populateGraph(ImmutableValueGraph.Builder<String, Integer> builder, String fileName) throws IOException {
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

	private static void parseLine(String rule, ImmutableValueGraph.Builder<String, Integer> builder) {
		String[] bagSpecs = rule.split(" contain ");
		String parent = bagSpecs[0].replace(" bags", "");
		builder.addNode(parent);
		String childrenStr = bagSpecs[1];
		if (!childrenStr.equals("no other bags."))  {
			childrenStr = childrenStr.replace(" bags", "").replace(" bag", "").replace(".", "");
			String[] children = childrenStr.split(", ");
			for (String child : children) {
				Integer qty = Integer.parseInt(String.valueOf(child.charAt(0)));
				String childColour = child.substring(2);
				builder.addNode(childColour);
				builder.putEdgeValue(parent, childColour, qty);
			}
		}
	}

	public long getApplicableBags(String colour) {
		/*Set<String> allNodes = bags.nodes();
		Predicate<String> isOuterNode = name -> bags.predecessors(name).isEmpty();
		Set<String> outerNodes = allNodes.stream()
			.filter(isOuterNode)
			.collect(Collectors.toSet());
		return outerNodes.stream()
			.map(bags::successors)
			.filter(nodes -> nodes.contains(colour))
			.count();*/
		Set<String> colours = new HashSet<>();
		processPredecessors(colour, colours);
		return colours.size();
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
}
