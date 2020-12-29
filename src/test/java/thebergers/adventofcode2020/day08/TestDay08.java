package thebergers.adventofcode2020.day08;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import thebergers.adventofcode2020.utils.Utils;

public class TestDay08 {

	@Test
	void part1() throws IOException {
		String fileName = "./src/test/resources/day08/test_input_pt1.txt";
		List<String> instructions = Utils.getDataFromFile(fileName);
		InstructionProcessor instructionProcessor = InstructionProcessor.Builder.instance().instructions(instructions).build();
		instructionProcessor.process();
		assertEquals(5, instructionProcessor.getAccumulator());
		assertEquals(1, instructionProcessor.getIndex());
		assertFalse(instructionProcessor.isTerminatedCleanly());
	}

	@Test
	void part2() throws IOException {
		String fileName = "./src/test/resources/day08/test_input_pt1.txt";
		List<String> instructions = Utils.getDataFromFile(fileName);
		InstructionProcessorDebugger ipd = new InstructionProcessorDebugger(instructions);
		ipd.doProcessing();
		assertTrue(ipd.isTerminatedNormally());
		assertEquals(8, ipd.getAccumulator());
	}
}
