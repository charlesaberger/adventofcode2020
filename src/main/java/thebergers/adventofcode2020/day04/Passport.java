package thebergers.adventofcode2020.day04;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Value
public class Passport {

	Integer birthYear;

	Integer countryId;

	Integer expirationYear;

	String eyeColour;

	String hairColour;

	String height;

	Integer issueYear;

	String passportId;

	private Passport(Builder builder) {
		this.birthYear = builder.birthYear;
		this.countryId = builder.countryId;
		this.expirationYear = builder.expirationYear;
		this.eyeColour = builder.eyeColour;
		this.hairColour = builder.hairColour;
		this.height = builder.height;
		this.issueYear = builder.issueYear;
		this.passportId = builder.passportId;
	}

	public static abstract class Builder {

		final ValidationMode validationMode;

		Integer birthYear;

		Integer countryId;

		Integer expirationYear;

		String eyeColour;

		String hairColour;

		String height;

		Integer issueYear;

		String passportId;

		private Builder(ValidationMode validationMode) {
			this.validationMode = validationMode;
		}

		public static Builder getInstance(ValidationMode validationMode) {
			switch (validationMode) {
				case LOOSE:
					return new LooseValidationBuilder();
				case STRICT:
					return new StrictValidationBuilder();
			}
			throw new IllegalArgumentException();
		}

		public Builder property(String propertyStr) {
			String[] props = propertyStr.split(":");
			Property property = Property.fromName(props[0]);
			if (property == null) {
				return this;
			}
			switch (property) {
				case BIRTH_YEAR:
					birthYear(props[1]);
					break;
				case COUNTRY_ID:
					countryId(props[1]);
					break;
				case EXPIRATION_YEAR:
					expirationYear(props[1]);
					break;
				case EYE_COLOUR:
					eyeColour(props[1]);
					break;
				case HAIR_COLOUR:
					hairColour(props[1]);
					break;
				case HEIGHT:
					height(props[1]);
					break;
				case ISSUE_YEAR:
					issueYear(props[1]);
					break;
				case PASSPORT_ID:
					passportId(props[1]);
					break;
			}
			return this;
		}

		public Passport build() {
			if (validate()) {
				return new Passport(this);
			}
			return null;
		}

		@Override
		public String toString() {
			return "Builder{" +
				"birthYear=" + birthYear +
				", countryId=" + countryId +
				", expirationYear=" + expirationYear +
				", eyeColour='" + eyeColour + '\'' +
				", hairColour='" + hairColour + '\'' +
				", height='" + height + '\'' +
				", issueYear=" + issueYear +
				", passportId='" + passportId + '\'' +
				"}, valid=" + validate();
		}

		protected abstract boolean validate();

		private void birthYear(String value) {
			this.birthYear = Integer.parseInt(value);
		}

		private void countryId(String value) {
			this.countryId = Integer.parseInt(value);
		}

		private void expirationYear(String value) {
			this.expirationYear = Integer.parseInt(value);
		}

		private void eyeColour(String value) {
			this.eyeColour = value;
		}

		private void hairColour(String value) {
			this.hairColour = value;
		}

		private void height(String value) {
			this.height = value;
		}

		private void issueYear(String value) {
			this.issueYear = Integer.parseInt(value);
		}

		private void passportId(String value) {
			this.passportId = value;
		}
	}

	public static class LooseValidationBuilder extends Builder {

		private LooseValidationBuilder() {
			super(ValidationMode.LOOSE);
		}

		@Override
		protected boolean validate() {
			List<Boolean> checks = new ArrayList<>();
			checks.add(birthYear != null);
			checks.add(expirationYear != null);
			checks.add(eyeColour != null);
			checks.add(hairColour != null);
			checks.add(height != null);
			checks.add(issueYear != null);
			checks.add(passportId != null);
			return checks.stream().reduce(Boolean::logicalAnd).orElse(false);
		}
	}

	public static class StrictValidationBuilder extends Builder {

		private StrictValidationBuilder() {
			super(ValidationMode.STRICT);
		}

		@Override
		protected boolean validate() {
			List<Boolean> checks = new ArrayList<>();
			checks.add(validateBirthYear());
			checks.add(validateCountryId());
			checks.add(validateExpirationYear());
			checks.add(validateEyeColour());
			checks.add(validateHairColour());
			checks.add(validateHeight());
			checks.add(validateIssueYear());
			checks.add(validatePassportId());
			//LOG.info("{}", checks);
			return checks.stream().reduce(Boolean::logicalAnd).orElse(false);
		}

		private boolean matchRegex(String value, String regex) {
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(value);
			return matcher.matches();
		}

		private boolean validateBirthYear() {
			return birthYear != null && (birthYear >= 1920 && birthYear <= 2002);
		}

		private boolean validateCountryId() {
			return true;
		}

		private boolean validateExpirationYear() {
			return expirationYear != null && (expirationYear >= 2020 && expirationYear <= 2030);
		}

		private boolean validateEyeColour() {
			return EyeColour.fromValue(eyeColour) != null;
		}

		private boolean validateHairColour() {
			return hairColour != null && matchRegex(hairColour, "^#{1}[0-9a-z]{6}$");
		}

		private boolean validateHeight() {
			if (height == null) {
				return false;
			}
			if (!matchRegex(height, "^[0-9]{1,}(cm|in)$")) {
				return false;
			}
			if (height.contains("cm")) {
				int heightAmt = Integer.parseInt(height.replace("cm", ""));
				return heightAmt >= 150 && heightAmt <= 193;
			}
			int heightAmt = Integer.parseInt(height.replace("in", ""));
			return heightAmt >= 59 && heightAmt <= 76;
		}

		private boolean validateIssueYear() {
			return issueYear != null && (issueYear >= 2010 && issueYear <= 2020);
		}

		private boolean validatePassportId() {
			return passportId!= null && matchRegex(passportId, "^[0-9]{9}$");
		}
	}

	enum Property {
		BIRTH_YEAR("byr"),
		COUNTRY_ID("cid"),
		EXPIRATION_YEAR("eyr"),
		EYE_COLOUR("ecl"),
		HAIR_COLOUR("hcl"),
		HEIGHT("hgt"),
		ISSUE_YEAR("iyr"),
		PASSPORT_ID("pid");

		String name;

		Property(String name) {
			this.name = name;
		}

		static Property fromName(String name) {
			for (Property property : values()) {
				if (property.name.equals(name)) {
					return property;
				}
			}
			return null;
		}
	}
	enum EyeColour {
		AMB("amb"),
		BLU("blu"),
		BRN("brn"),
		GRY("gry"),
		GRN("grn"),
		HZL("hzl"),
		OTH("oth");

		String value;

		EyeColour(String value) {
			this.value = value;
		}

		static EyeColour fromValue(String value) {
			for (EyeColour eyeColour : values()) {
				if (eyeColour.value.equals(value)) {
					return eyeColour;
				}
			}
			return null;
		}
	}
}
