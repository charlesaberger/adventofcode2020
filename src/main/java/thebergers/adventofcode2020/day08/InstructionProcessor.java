package thebergers.adventofcode2020.day08;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import thebergers.adventofcode2020.utils.Utils;

@Slf4j
public class InstructionProcessor {

	List<Instruction> instructions = new LinkedList<>();

	Integer accumulator = 0;

	public int getAccumulator() {
		return accumulator;
	}

	void process() {
		int index = 0;
		Instruction instruction;
		while (true) {
			instruction = instructions.get(index);
			if (instruction.isProcessed()) {
				break;
			}
			switch (instruction.instructionType) {
				case ACCUMULATOR:
					accumulator += instruction.value;
					index++;
					break;
				case JUMP:
					index += instruction.value;
					break;
				case NOOP:
					index++;
					break;
			}
			instruction.processed();
		}
	}

	public static void main(String[] args) throws IOException {
		String fileName = "./src/main/resources/input/day08/input.txt";
		List<String> instructions = Utils.getDataFromFile(fileName);
		InstructionProcessor instructionProcessor = InstructionProcessor.Builder
			.instance().instructions(instructions).build();
		instructionProcessor.process();
		LOG.info("Pt1: Accumulator={}", instructionProcessor.getAccumulator());
	}

	@Data
	public static class Instruction {

		private final InstructionType instructionType;

		private final Integer value;

		private boolean processed = false;

		public void processed() { this.processed = true; }

		public static Instruction of(String instructionStr) {
			String[] parts = instructionStr.split(" ");
			InstructionType instructionType = InstructionType.fromValue(parts[0]);
			Integer value = Integer.parseInt(parts[1]);
			return new Instruction(instructionType, value);
		}
	}

	enum InstructionType {
		ACCUMULATOR("acc"),
		JUMP("jmp"),
		NOOP("nop");

		String name;

		InstructionType(String name) {
			this.name = name;
		}

		public static InstructionType fromValue(String name) {
			for (InstructionType it : values()) {
				if (it.name.equals(name)) {
					return it;
				}
			}
			throw new IllegalArgumentException(name);
		}
	}

	public static class Builder {
		List<String> instructions;

		public static Builder instance() {
			return new InstructionProcessor.Builder();
		}

		public Builder instructions(List<String> instructions) {
			this.instructions = instructions;
			return this;
		}

		public InstructionProcessor build() {
			InstructionProcessor instructionProcessor = new InstructionProcessor();
			instructions.stream()
				.map(Instruction::of)
				.forEach(instr -> instructionProcessor.instructions.add(instr));
			return instructionProcessor;
		}
	}
}
