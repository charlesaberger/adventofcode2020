package thebergers.adventofcode2020.day04;

import java.util.ArrayList;
import java.util.List;

import lombok.Value;

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

	public static class Builder {

		Integer birthYear;

		Integer countryId;

		Integer expirationYear;

		String eyeColour;

		String hairColour;

		String height;

		Integer issueYear;

		String passportId;

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

		private boolean validate() {
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
}
