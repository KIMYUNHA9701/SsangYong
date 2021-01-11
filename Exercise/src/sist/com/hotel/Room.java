package sist.com.hotel;

import java.util.Arrays;

public class Room {
	private static Object[][] rooms = new Object[17][6];
	private static int roomNum;
	private static String roomType;
	private static String bedSize;
	private static int numberOfBeds;
	private static int maxPersonnal;
	private static int price;

	public Object[] getRooms(int i) {
		return rooms[i];
	}

	public void roomSet() { // static 안되는 이유
		for (int i = 0; i < rooms.length; i++) {
			if (i < 5) {
				roomNum = 101 + i;
				roomType = "single";
				bedSize = "single";
				numberOfBeds = 1;
				maxPersonnal = 1;
				price = 100000;
			} else if (i < 10) {
				roomNum = 201 + i - 5;
				roomType = "double";
				bedSize = "double";
				numberOfBeds = 1;
				maxPersonnal = 2;
				price = 200000;
			} else if (i < 15) {
				roomNum = 301 + i - 10;
				roomType = "suite";
				bedSize = "king";
				numberOfBeds = 1;
				maxPersonnal = 2;
				price = 400000;
			} else {
				roomNum = 401 + i - 15;
				roomType = "family";
				bedSize = "king";
				numberOfBeds = 2;
				maxPersonnal = 4;
				price = 600000;
			}

			rooms[i][0] = roomNum;
			rooms[i][1] = roomType;
			rooms[i][2] = bedSize;
			rooms[i][3] = numberOfBeds;
			rooms[i][4] = maxPersonnal;
			rooms[i][5] = price;
		}
	}

	public void roomInfo() {
		System.out.println("roomInformation");
		for (int i = 0; i < 17; i++) {
			System.out.println(Arrays.toString(rooms[i]));
		}
		System.out.println();
	}
}
