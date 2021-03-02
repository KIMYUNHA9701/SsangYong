package sist.com.hotel;

import java.util.Scanner;

public class Main {
	Scanner sc = new Scanner(System.in);
	Reservation[] rvs = new Reservation[10];
	static int cnt;
	int dateIn, dateOut;
	int roomNum;
	int p;
	int caseA;
	int index;

	public void reserve() {
		Reservation rv = new Reservation();
		System.out.println("예약을 시작합니다.");
		System.out.println("이름");
		rv.setName(sc.next());
		System.out.println("휴대폰 번호");
		rv.setPhoneNum(sc.next());

		while (rv.getCheckIn() == 0) {
			System.out.println("체크인 날짜");
			dateIn = sc.nextInt();
			rv.setCheckIn(dateIn);
		}

		while (rv.getCheckOut() == 0) {
			System.out.println("체크아웃 날짜");
			dateOut = sc.nextInt();
			rv.setCheckOut(dateOut);
		}

		while (rv.getReservedRoomNum() == 0) {
			caseA = 0;
			already();
			rv.setReservedRoomNum(roomNum);
		}

		while (rv.getPersonnel() == 0) {
			System.out.println("인원수");
			p = sc.nextInt();
			rv.setPersonnel(roomNum, p);
		}

		System.out.println("조식포함여부(o/x)");
		String b = sc.next();
		if (b.equals("o")) {
			rv.setBreakfast(true);
		}

		rvs[cnt] = rv;
		cnt++;
		System.out.println("예약이 완료되었습니다");

	}

	public void already() { // 예약할 수 없는 호수
		if (cnt >= 1) {
			if (caseA == 0) {
				System.out.println("방호수");
				roomNum = sc.nextInt();
			} else if (caseA == 1) {
				System.out.println("체크인날짜를 입력해주세요");
				dateIn = sc.nextInt();
				System.out.println("체크아웃날짜를 입력해주세요");
				dateOut = sc.nextInt();
			} else {
				System.out.println("체크아웃날짜를 입력해주세요");
				dateOut = sc.nextInt();
				dateIn = rvs[index].getCheckOut();
			}
			for (int i = 0; i < cnt; i++) {
				if (rvs[i].getReservedRoomNum() == roomNum && i != index) {
					if (rvs[i].getCheckIn() <= dateIn && dateIn < rvs[i].getCheckOut()) {
						System.out.println("해당 날짜에는 예약할 수 없는 호실입니다.");
						already();
					} else if (rvs[i].getCheckIn() < dateOut && dateOut < rvs[i].getCheckOut()) {
						System.out.println("해당 날짜에는 예약할 수 없는 호실입니다.");
						already();
					} else if (rvs[i].getCheckIn() >= dateIn && dateOut >= rvs[i].getCheckOut()) {
						System.out.println("해당 날짜에는 예약할 수 없는 호실입니다.");
						already();
					}
				}
			}
		} else {
			if (caseA == 0) {
				System.out.println("방호수");
				roomNum = sc.nextInt();
			} else if (caseA == 1) {
				System.out.println("체크인날짜를 입력해주세요");
				dateIn = sc.nextInt();
				System.out.println("체크아웃날짜를 입력해주세요");
				dateOut = sc.nextInt();
			} else {
				System.out.println("체크아웃날짜를 입력해주세요");
				dateOut = sc.nextInt();
				dateIn = rvs[index].getCheckOut();
			}
		}
	}

	public void extend() {
		index = search();
		System.out.println("연장을 시작합니다!");
		caseA = 2;
		already();
		rvs[index].setCheckOut(dateOut);
		rvs[index].setExtend(true);
		System.out.println("예약이 완료되었습니다");
		listOne();
	}

	public void list() {
		System.out.println("--------------------------------------------------------");
		System.out.println("예약자성함     휴대폰번호             호실     체크인      체크아웃   인원  연장여부  조식포함여부");
		for (int i = 0; i < cnt; i++) {
			System.out.println(rvs[i].toString());
		}
		System.out.println("--------------------------------------------------------");
		System.out.println();
	}

	public void listDetail() {
		index = search();
		if (index == -1) {
			System.out.println("예약자목록에 존재하지 않는 이름입니다.");
		} else {
			listOne();
		}
		System.out.println();
	}

	public void listOne() {
		System.out.println("--------------------------------------------------------");
		System.out.println("예약자성함      휴대폰번호            호실    체크인  체크아웃  인원  연장여부  조식포함여부");
		System.out.println(rvs[index].toString());
		System.out.println("--------------------------------------------------------");
		System.out.println();
	}

	public void cancle() {
		index = search();
		if (index == -1) {
			System.out.println("예약자목록에 존재하지 않는 이름입니다.");
		} else {
			// delete
			for (int i = 0; i < cnt - 1; i++) {
				rvs[i] = rvs[i + 1];
			}
			cnt--;
		}
	}

	public int search() {
		System.out.println("이름을 입력해주세요");
		String name = sc.next();
		for (int i = 0; i < cnt; i++) {
			if (rvs[i].getName().equals(name)) {
				return i;
			}
		}
		return -1;
	}

	public void change() {
		index = search();
		if (index == -1) {
			System.out.println("예약자목록에 존재하지 않는 이름입니다.");
		} else {
			System.out.println("날짜변경");
			caseA = 1;
			already();
			rvs[index].setCheckIn(dateIn);
			rvs[index].setCheckOut(dateOut);
			rvs[index].setReservedRoomNum(roomNum);
			System.out.println("변경되었습니다");
			listOne();
		}
	}

	public void menu() {
		while (true) {
			System.out.println("1월 예약");
			System.out.println("1.예약  2.조회  3.상세조회  4.예약변경  5.연장  6.취소");
			switch (sc.nextInt()) {
			case 1:
				reserve();
				break;
			case 2:
				list();
				break;
			case 3:
				listDetail();
				break;
			case 4:
				change();
				break;
			case 5:
				extend();
				break;
			case 6:
				cancle();
				break;

			default:
				break;
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("==========  Welcome to ArrayHotel  ==========");
		Main m = new Main();
		Room r = new Room();
		r.roomSet();
		r.roomInfo();
		m.menu();
	}
}
