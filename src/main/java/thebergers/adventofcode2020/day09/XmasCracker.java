package thebergers.adventofcode2020.day09;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import thebergers.adventofcode2020.utils.Utils;

@Slf4j
public class XmasCracker {

	private final int preambleSize;

	private final List<Long> data;

	public XmasCracker(int preambleSize, List<String> data) {
		this.preambleSize = preambleSize;
		this.data = data.stream().map(Long::parseLong).collect(Collectors.toList());
	}

	public long analyse() {
		int index = preambleSize;
		while (index < data.size()) {
			List<Long> values = data.subList(index - preambleSize, index);
			Long candidate = data.get(index);
			if (!isValidNumber(candidate, values)) {
				return candidate;
			}
			index++;
		}
		return -1L;
	}

	private boolean isValidNumber(Long candidate, List<Long> values) {
		for (int i = 0; i < values.size(); i++) {
			for (int j = 0; j < values.size(); j++) {
				if (j == i) {
					continue;
				}
				Long val1 = values.get(i);
				Long val2 = values.get(j);
				if (val1.equals(val2)) {
					continue;
				}
				if (candidate.equals(val1 + val2)) {
					return true;
				}
			}
		}
		return false;
	}

	public static void main(String[] args) throws IOException {
		String fileName = "./src/main/resources/input/day09/input.txt";
		List<String> data = Utils.getDataFromFile(fileName);
		XmasCracker xmasCracker = new XmasCracker(25, data);
		LOG.info("Value = {}", xmasCracker.analyse());
	}
}
