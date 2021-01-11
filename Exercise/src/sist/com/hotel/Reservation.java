package sist.com.hotel;

import java.util.Calendar;

public class Reservation extends Room {
	Room r = new Room();
	private String name;
	private String phoneNum;
	private int reservedRoomNum;
	private int checkIn;
	private int checkOut;
	private int personnel;
	private boolean breakfast = false;
	private boolean extend = false;

	public int getReservedRoomNum() {
		return reservedRoomNum;
	}

	public void setReservedRoomNum(int reservedRoomNum) {
		switch (reservedRoomNum) {
		case 101:
		case 102:
		case 103:
		case 104:
		case 105:
		case 201:
		case 202:
		case 203:
		case 204:
		case 205:
		case 301:
		case 302:
		case 303:
		case 304:
		case 305:
		case 401:
		case 402:
			this.reservedRoomNum = reservedRoomNum;
			break;

		default:
			System.out.println("존재하지 않는 호수입니다.");
			break;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public int getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(int checkIn) {
		Calendar cal = Calendar.getInstance();
		int date = cal.get(cal.DATE);
		if (date > checkIn) {
			System.out.println("예약할 수 없는 날짜입니다.");
			return;
		}
		this.checkIn = checkIn;
	}

	public int getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(int checkOut) {
		if (checkOut < checkIn) {
			System.out.println("잘못된 체크아웃 날짜입니다.");
			return;
		}
		this.checkOut = checkOut;
	}

	public int getPersonnel() {
		return personnel;
	}

	public void setPersonnel(int roomNum, int personnel) {
		if (personnel < 0) {
			this.personnel = 0;
			System.out.println("잘못된 입력입니다.");
			return;
		} else {
			for (int i = 0; i < 17; i++) {
				if ((int) r.getRooms(i)[0] == roomNum) {
					if ((int) r.getRooms(i)[4] < personnel) {
						this.personnel = 0;
						System.out.println("인원초과입니다");
						return;
					}
				}
			}
		}
		this.personnel = personnel;
	}

	public boolean isExtend() {
		return extend;
	}

	public void setExtend(boolean extend) {
		this.extend = extend;
	}

	public boolean isBreakfast() {
		return breakfast;
	}

	public void setBreakfast(boolean breakfast) {
		this.breakfast = breakfast;
	}

	@Override
	public String toString() {
		return name + " " + phoneNum + "  " + reservedRoomNum + "  01/" + checkIn + "  01/" + checkOut + "\t " + personnel
				+ "  " + breakfast + "  " + extend;
	}

}
