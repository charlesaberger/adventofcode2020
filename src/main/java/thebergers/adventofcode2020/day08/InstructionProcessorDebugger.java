package thebergers.adventofcode2020.day08;

import java.io.IOException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import thebergers.adventofcode2020.utils.Utils;

@Slf4j
public class InstructionProcessorDebugger {

	private final List<String> originalInstructions;

	private boolean terminatedNormally = false;

	private Integer accumulator = 0;

	public InstructionProcessorDebugger(List<String> instructions) {
		this.originalInstructions = instructions;
	}

	public boolean isTerminatedNormally() {
		return terminatedNormally;
	}

	public Integer getAccumulator() {
		return accumulator;
	}

	void doProcessing() {
		int counter = 0;

		while (true) {
			counter++;

			// build an InstructionProcessor
			InstructionProcessor instructionProcessor = InstructionProcessor.Builder
				.instance().instructions(originalInstructions).build();

			// get its list of instructions
			List<InstructionProcessor.Instruction> instructions = instructionProcessor.getInstructions();

			// find next jmp / nop & toggle
			replaceInstruction(counter, instructions);

			// run InstructionProcessor
			instructionProcessor.process();

			// did it terminate normally?
			if (instructionProcessor.isTerminatedCleanly()) {
				// Yes - set accumulator & break;
				accumulator = instructionProcessor.getAccumulator();
				terminatedNormally = true;
				break;
			}

			// No - start over
		}
	}

	void replaceInstruction(Integer counter, List<InstructionProcessor.Instruction> instructions) {
		int jmpNopCnt = 0;
		for (InstructionProcessor.Instruction instruction : instructions) {
			InstructionProcessor.InstructionType instructionType = instruction.getInstructionType();
			if (instructionType.equals(InstructionProcessor.InstructionType.JUMP) ||
				instructionType.equals(InstructionProcessor.InstructionType.NOOP)) {
				jmpNopCnt++;
				if (jmpNopCnt == counter) {
					InstructionProcessor.InstructionType newInstructionType =
						instructionType.equals(InstructionProcessor.InstructionType.NOOP) ? InstructionProcessor.InstructionType.JUMP :
							InstructionProcessor.InstructionType.NOOP;
					instruction.setInstructionType(newInstructionType);
					break;
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		String fileName = "./src/main/resources/input/day08/input.txt";
		List<String> instructions = Utils.getDataFromFile(fileName);
		InstructionProcessorDebugger ipd = new InstructionProcessorDebugger(instructions);
		ipd.doProcessing();
		LOG.info("Pt2: accumulator = {}", ipd.getAccumulator());
	}
}
