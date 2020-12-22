package thebergers.adventofcode2020.day02;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import thebergers.adventofcode2020.utils.Utils;

@Slf4j
@Value
public class PasswordPhilosophy {

	PasswordPolicy passwordPolicy;

	public PasswordPhilosophy(String policyStr) {
		this.passwordPolicy = PasswordPolicy.getInstance(policyStr);
	}

	public boolean validate() {
		Map<String, Integer> characterCounts = new HashMap<>();
		List<String> characters = IntStream.range(0, passwordPolicy.password.length())
			.mapToObj(passwordPolicy.password::charAt)
			.map(String::valueOf)
			.collect(Collectors.toList());
		characters.forEach(character -> {
			int cnt;
			cnt = characterCounts.getOrDefault(character, 0);
			cnt++;
			characterCounts.put(character, cnt);
		});
		int characterCount = characterCounts.getOrDefault(passwordPolicy.character, 0);
		boolean valid = characterCount >= passwordPolicy.minNumber && characterCount <= passwordPolicy.maxNumber;
		LOG.info("{}, valid = {}", passwordPolicy, valid);
		return valid;
	}

	public boolean validate2() {
		String character1 = String.valueOf(passwordPolicy.password.charAt(passwordPolicy.minNumber - 1));
		String character2 = String.valueOf(passwordPolicy.password.charAt(passwordPolicy.maxNumber - 1));
		boolean result = character1.equals(passwordPolicy.character) ^ character2.equals(passwordPolicy.character);
		LOG.info("password={},position1={},character1={},position2={},character2={},result={}", passwordPolicy.password,
			passwordPolicy.minNumber, character1, passwordPolicy.maxNumber, character2, result);
		return result;
	}

	static long validateList(List<String> passwords) {
		return passwords.stream()
			.map(PasswordPhilosophy::new)
			.filter(PasswordPhilosophy::validate)
			.count();
	}

	static long validateList2(List<String> passwords) {
		return passwords.stream()
			.map(PasswordPhilosophy::new)
			.filter(PasswordPhilosophy::validate2)
			.count();
	}

	@Value
	public static class PasswordPolicy {

		Integer minNumber;

		Integer maxNumber;

		String character;

		String password;

		private PasswordPolicy(String minValue, String maxValue, String character, String password) {
			this.minNumber = Integer.parseInt(minValue);
			this.maxNumber = Integer.parseInt(maxValue);
			this.character = character;
			this.password = password;
		}

		//public String toString

		static PasswordPolicy getInstance(String policyStr) {
			String policyRegex = "([0-9]{1,2})-([0-9]{1,2}) ([a-z]{1}): ([a-z]{1,})";
			Pattern pattern = Pattern.compile(policyRegex);
			Matcher matcher = pattern.matcher(policyStr);
			if (matcher.matches()) {
				return new PasswordPolicy(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4));
			}
			throw new IllegalArgumentException(String.format("Invalid input: %s", policyStr));
		}
	}

	public static void main(String[] args) throws IOException {
		String fileName = "./src/main/resources/input/day02/input.txt";
		List<String> passwords = Utils.getDataFromFile(fileName);
		long validCnt = PasswordPhilosophy.validateList(passwords);
		LOG.info("Part 1: {} passwords are valid", validCnt);
		long validCnt2 = PasswordPhilosophy.validateList2(passwords);
		LOG.info("Part 2: {} passwords are valid", validCnt2);
	}
}
