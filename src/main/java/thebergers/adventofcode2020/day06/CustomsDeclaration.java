package thebergers.adventofcode2020.day06;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
@Value
public class CustomsDeclaration {

	public static int countYesAnswers(String fileName) throws IOException {
		Map<String, Integer> groupTotals = new HashMap<>();
		BufferedReader answerData = new BufferedReader(new FileReader(fileName));
		Set<String> groupAnswers = new HashSet<>();
		int groupNum = 1;
		while (true) {
			String personAnswers = answerData.readLine();
			if (StringUtils.isEmpty(personAnswers)) {
				String groupName = String.format("Group %d", groupNum);
				groupTotals.put(groupName, groupAnswers.size());
				if (personAnswers == null) {
					break;
				}
				groupNum++;
				groupAnswers.clear();
			}
			for (int i = 0; i < personAnswers.length(); i++) {
				String answer = String.valueOf(personAnswers.charAt(i));
				groupAnswers.add(answer);
			}
		}

		return groupTotals.values()
			.stream()
			.reduce(0, Integer::sum);
	}

	public static void main(String[] args) throws IOException {
		String fileName = "./src/main/resources/input/day06/input.txt";
		LOG.info("Answer = {}", CustomsDeclaration.countYesAnswers(fileName));
	}
}
