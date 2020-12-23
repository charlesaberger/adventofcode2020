package thebergers.adventofcode2020.day03;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import thebergers.adventofcode2020.utils.Utils;

@Slf4j
@Value
public class Forest {

	List<String> mapData;

	int mapWidth;

	Map<Point, Location> points = new HashMap<>();

	public Forest(List<String> mapData) {
		this.mapData = mapData;
		initializeMap();
		this.mapWidth = mapData.get(0).length();
	}

	private void initializeMap() {
		IntStream.range(0, mapData.size())
			.forEach(y -> {
				String line = mapData.get(y);
				for (int x = 0; x < line.length(); x++) {
					String locationStr = String.valueOf(line.charAt(x));
					Location location = Location.fromValue(locationStr);
					if (location.equals(Location.TREE)) {
						Point point = Point.getInstance(x, y);
						points.put(point, location);
					}
				}
			});
	}

	public long navigatePart1(int xStep, int yStep) {
		int x = 0;
		int y = 0;
		long treeCnt = 0;
		int maxY = mapData.size() - 1;
		while (y <= maxY) {
			boolean isTree = isTree(x, y);
			LOG.info("x={}, y={}: isTree: {}", x, y, isTree);
			if (isTree) {
				treeCnt++;
			}
			x += xStep;
			y += yStep;
		}
		return treeCnt;
	}

	private boolean isTree(int x, int y) {
		int xPos = x % mapWidth;
		Point currentPosition = Point.getInstance(xPos, y);
		return points.containsKey(currentPosition);
	}

	@Value
	static class Point {
		Integer x;

		Integer y;

		static Point getInstance(Integer x, Integer y) {
			return new Point(x, y);
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

	public static void main(String[] args) throws IOException {
		String fileName = "./src/main/resources/input/day03/input.txt";
		List<String> mapData = Utils.getDataFromFile(fileName);
		Forest forest = new Forest(mapData);
		long treeCnt = forest.navigatePart1(3, 1);
		LOG.info("Found {} trees", treeCnt);
	}
}
