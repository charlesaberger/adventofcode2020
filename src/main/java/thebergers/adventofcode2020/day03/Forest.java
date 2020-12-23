package thebergers.adventofcode2020.day03;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Value
public class Forest {

	List<String> mapData;

	Table<Integer, Integer, Point> points = HashBasedTable.create();

	public Forest(List<String> mapData) {
		this.mapData = mapData;
		initializeMap(0, 0);
	}

	private void initializeMap(int xOffset, int yOffset) {
		IntStream.range(0, mapData.size())
			.forEach(y -> {
				String line = mapData.get(y);
				for (int x = 0; x < line.length(); x++) {
					String location = String.valueOf(line.charAt(x));
					int xVal = x + xOffset, yVal = y + yOffset;
					points.put(yVal, xVal, Point.getInstance(xVal, yVal, location));
				}
			});
		LOG.info("{}", this);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder(System.lineSeparator());
		for (int x = 0; x < points.rowKeySet().size(); x++) {
			for (int y = 0; y < points.columnKeySet().size(); y++) {
				sb.append(points.get(x, y).getLocation().value);
			}
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}

	public long navigatePart1(int xStep, int yStep) {
		int x = 0;
		int y = 0;
		long treeCnt = 0;
		int maxY = mapData.size() - 1;
		while (y <= maxY) {
			Location location = points.get(y, x).getLocation();
			LOG.info("x={}, y={}: {}", x, y, location.value);
			if (location.equals(Location.TREE)) {
				treeCnt++;
			}
			int maxX = points.columnKeySet().stream().max(Comparator.naturalOrder()).orElse(0);
			if (x + xStep > maxX) {
				initializeMap(maxX + 1, 0);
			}
			x += xStep;
			y += yStep;
		}
		return treeCnt;
	}

	@Value
	static class Point {
		Integer x;

		Integer y;

		Location location;

		static Point getInstance(Integer x, Integer y, String locationStr) {
			return new Point(x, y, Location.fromValue(locationStr));
		}
	}

	private enum Location {
		EMPTY("."),
		TREE("#");

		Location(String value) {
			this.value = value;
		}

		String value;

		static Location fromValue(String value) {
			if ("#".equals(value)) {
				return TREE;
			}
			return EMPTY;
		}
	}
}
