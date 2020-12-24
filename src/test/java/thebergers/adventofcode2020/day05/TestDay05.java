package thebergers.adventofcode2020.day05;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TestDay05 {

	private static final Integer NUM_ROWS = 128;

	private static final Integer NUM_SEATS = 8;

	@ParameterizedTest
	@CsvSource({
		"FBFBBFFRLR,44,5,357",
		"BFFFBBFRRR,70,7,567",
		"FFFBBBFRRR,14,7,119",
		"BBFFBBFRLL,102,4,820"
	})
	void part1(String boardingPass, Integer row, Integer seat, Integer seatId) {
		BinaryBoarding binaryBoarding = new BinaryBoarding(NUM_ROWS, NUM_SEATS);
		BoardingDetails boardingDetails = binaryBoarding.verifyPass(boardingPass);
		assertEquals(row, boardingDetails.getRow(), "row");
		assertEquals(seat, boardingDetails.getSeat(), "seat");
		assertEquals(seatId, boardingDetails.getSeatId(), "seatId");
	}

	@Test
	void part1CalcHighest() {
		List<String> boardingPasses = List.of(
			"FBFBBFFRLR",
			"BFFFBBFRRR",
			"FFFBBBFRRR",
			"BBFFBBFRLL"
		);
		assertEquals(820, BinaryBoarding.findHighestSeatId(NUM_ROWS, NUM_SEATS, boardingPasses));
	}

}
