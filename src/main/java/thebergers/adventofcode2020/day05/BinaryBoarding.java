package thebergers.adventofcode2020.day05;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import thebergers.adventofcode2020.utils.Utils;

@Slf4j
@Value
public class BinaryBoarding {

	public static final Integer NUM_ROWS = 128;

	public static final Integer NUM_SEATS = 8;

	Integer numRows;

	Integer numSeats;

	public static int findHighestSeatId(Integer numRows, Integer numSeats, List<String> boardingPasses) {
		BinaryBoarding binaryBoarding = new BinaryBoarding(numRows, numSeats);
		return boardingPasses.stream()
			.map(binaryBoarding::verifyPass)
			.map(BoardingDetails::getSeatId)
			.max(Comparator.naturalOrder()).orElse(0);
	}

	public int findMySeatId(List<String> boardingPasses) {
		List<Integer> allSeatIds = boardingPasses.stream()
			.map(this::verifyPass)
			.map(BoardingDetails::getSeatId)
			.sorted(Comparator.reverseOrder())
			.collect(Collectors.toList());

		/*allSeatIds.stream()
			.sorted(Comparator.reverseOrder())
			.re*/
		for (int index = 0; index < allSeatIds.size() - 1; index++) {
			int id1 = allSeatIds.get(index);
			int id2 = allSeatIds.get(index + 1);
			if (id1 - id2 > 1) {
				return id1 - 1;
			}
		}
		throw new IllegalStateException("Unable to derive your seat id!");
	}

	public BoardingDetails verifyPass(String boardingPass) {
		String rowRef = boardingPass.substring(0, 7);
		Integer row = deriveRow(rowRef);
		String seatRef = boardingPass.substring(7, 10);
		Integer seat = deriveSeat(seatRef);
		Integer seatId = (row * numSeats) + seat;
		return new BoardingDetails(boardingPass, row, seat, seatId);
	}

	public static void main(String[] args) throws IOException {
		String fileName = "./src/main/resources/input/day05/input.txt";
		List<String> boardingPasses = Utils.getDataFromFile(fileName);
		int highestSeatId = BinaryBoarding.findHighestSeatId(NUM_ROWS, NUM_SEATS, boardingPasses);
		LOG.info("Highest SeatId = {}", highestSeatId);
		BinaryBoarding binaryBoarding = new BinaryBoarding(NUM_ROWS, NUM_SEATS);
		int mySeatId = binaryBoarding.findMySeatId(boardingPasses);
		LOG.info("Your seat id = {}", mySeatId);
	}

	private Integer deriveRow(String rowRef) {
		int rangeStart = 0;
		int rangeEnd = numRows - 1;
		int index = 0;
		while (index < rowRef.length()) {
			String rowDirectionStr = String.valueOf(rowRef.charAt(index));
			RowDirection rowDirection = RowDirection.fromValue(rowDirectionStr);
			switch(rowDirection) {
				case BACK:
					rangeStart = rangeEnd - (((rangeEnd + 1) - rangeStart) / 2) + 1;
					break;
				case FRONT:
					rangeEnd = rangeStart + (((rangeEnd + 1) - rangeStart) / 2) - 1;
					break;
			}
			index++;
		}
		return rangeStart;
	}

	private Integer deriveSeat(String seatRef) {
		int rangeStart = 0;
		int rangeEnd = numSeats - 1;
		int index = 0;
		while (index < seatRef.length()) {
			String seatDirectionStr = String.valueOf(seatRef.charAt(index));
			SeatDirection seatDirection = SeatDirection.fromValue(seatDirectionStr);
			switch(seatDirection) {
				case LEFT:
					rangeEnd = rangeStart + (((rangeEnd + 1) - rangeStart) / 2) - 1;
					break;
				case RIGHT:
					rangeStart = rangeEnd - (((rangeEnd + 1) - rangeStart) / 2) + 1;
					break;
			}
			index++;
		}
		return rangeStart;
	}

	enum RowDirection {
		BACK("B"),
		FRONT("F");

		String value;

		RowDirection(String value) {
			this.value = value;
		}

		static RowDirection fromValue(String value) {
			for (RowDirection rd : values()) {
				if (rd.value.equals(value)) {
					return rd;
				}
			}
			throw new IllegalArgumentException(value);
		}
	}

	enum SeatDirection {
		LEFT("L"),
		RIGHT("R");

		String value;

		SeatDirection(String value) {
			this.value = value;
		}

		static SeatDirection fromValue(String value) {
			for (SeatDirection sd : values()) {
				if (sd.value.equals(value)) {
					return sd;
				}
			}
			throw new IllegalArgumentException(value);
		}
	}
}
