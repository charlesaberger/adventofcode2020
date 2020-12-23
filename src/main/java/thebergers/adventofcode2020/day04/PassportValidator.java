package thebergers.adventofcode2020.day04;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class PassportValidator {

	static long validatePassportFile(String fileName) throws IOException {
		List<Passport> validPassports = new LinkedList<>();
		BufferedReader passportData = new BufferedReader(new FileReader(fileName));
		Passport.Builder builder = new Passport.Builder();
		while (true) {
			String data = passportData.readLine();
			if (StringUtils.isEmpty(data)) {
				LOG.info("{}", builder);
				Passport passport = builder.build();
				if (passport != null) {
					validPassports.add(passport);
				}
				if (data == null) {
					break;
				}
				builder = new Passport.Builder();
			} else {
				parseData(data, builder);
			}
		}
		return validPassports.size();
	}

	private static void parseData(String data, Passport.Builder builder) {
		String[] fields = data.split(" ");
		for (String field : fields) {
			builder = builder.property(field);
		}
	}

	public static void main(String[] args) throws IOException {
		String fileName = "./src/main/resources/input/day04/input.txt";
		long validPassports = PassportValidator.validatePassportFile(fileName);
		LOG.info("Found {} valid passports", validPassports);
	}
}
