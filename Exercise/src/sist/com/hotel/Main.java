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
		System.out.println("������ �����մϴ�.");
		System.out.println("�̸�");
		rv.setName(sc.next());
		System.out.println("�޴��� ��ȣ");
		rv.setPhoneNum(sc.next());

		while (rv.getCheckIn() == 0) {
			System.out.println("üũ�� ��¥");
			dateIn = sc.nextInt();
			rv.setCheckIn(dateIn);
		}

		while (rv.getCheckOut() == 0) {
			System.out.println("üũ�ƿ� ��¥");
			dateOut = sc.nextInt();
			rv.setCheckOut(dateOut);
		}

		while (rv.getReservedRoomNum() == 0) {
			caseA = 0;
			already();
			rv.setReservedRoomNum(roomNum);
		}

		while (rv.getPersonnel() == 0) {
			System.out.println("�ο���");
			p = sc.nextInt();
			rv.setPersonnel(roomNum, p);
		}

		System.out.println("�������Կ���(o/x)");
		String b = sc.next();
		if (b.equals("o")) {
			rv.setBreakfast(true);
		}

		rvs[cnt] = rv;
		cnt++;
		System.out.println("������ �Ϸ�Ǿ����ϴ�");

	}

	public void already() { // ������ �� ���� ȣ��
		if (cnt >= 1) {
			if (caseA == 0) {
				System.out.println("��ȣ��");
				roomNum = sc.nextInt();
			} else if (caseA == 1) {
				System.out.println("üũ�γ�¥�� �Է����ּ���");
				dateIn = sc.nextInt();
				System.out.println("üũ�ƿ���¥�� �Է����ּ���");
				dateOut = sc.nextInt();
			} else {
				System.out.println("üũ�ƿ���¥�� �Է����ּ���");
				dateOut = sc.nextInt();
				dateIn = rvs[index].getCheckOut();
			}
			for (int i = 0; i < cnt; i++) {
				if (rvs[i].getReservedRoomNum() == roomNum && i != index) {
					if (rvs[i].getCheckIn() <= dateIn && dateIn < rvs[i].getCheckOut()) {
						System.out.println("�ش� ��¥���� ������ �� ���� ȣ���Դϴ�.");
						already();
					} else if (rvs[i].getCheckIn() < dateOut && dateOut < rvs[i].getCheckOut()) {
						System.out.println("�ش� ��¥���� ������ �� ���� ȣ���Դϴ�.");
						already();
					} else if (rvs[i].getCheckIn() >= dateIn && dateOut >= rvs[i].getCheckOut()) {
						System.out.println("�ش� ��¥���� ������ �� ���� ȣ���Դϴ�.");
						already();
					}
				}
			}
		} else {
			if (caseA == 0) {
				System.out.println("��ȣ��");
				roomNum = sc.nextInt();
			} else if (caseA == 1) {
				System.out.println("üũ�γ�¥�� �Է����ּ���");
				dateIn = sc.nextInt();
				System.out.println("üũ�ƿ���¥�� �Է����ּ���");
				dateOut = sc.nextInt();
			} else {
				System.out.println("üũ�ƿ���¥�� �Է����ּ���");
				dateOut = sc.nextInt();
				dateIn = rvs[index].getCheckOut();
			}
		}
	}

	public void extend() {
		index = search();
		System.out.println("������ �����մϴ�!");
		caseA = 2;
		already();
		rvs[index].setCheckOut(dateOut);
		rvs[index].setExtend(true);
		System.out.println("������ �Ϸ�Ǿ����ϴ�");
		listOne();
	}

	public void list() {
		System.out.println("--------------------------------------------------------");
		System.out.println("�����ڼ���     �޴�����ȣ             ȣ��     üũ��      üũ�ƿ�   �ο�  ���忩��  �������Կ���");
		for (int i = 0; i < cnt; i++) {
			System.out.println(rvs[i].toString());
		}
		System.out.println("--------------------------------------------------------");
		System.out.println();
	}

	public void listDetail() {
		index = search();
		if (index == -1) {
			System.out.println("�����ڸ�Ͽ� �������� �ʴ� �̸��Դϴ�.");
		} else {
			listOne();
		}
		System.out.println();
	}

	public void listOne() {
		System.out.println("--------------------------------------------------------");
		System.out.println("�����ڼ���      �޴�����ȣ            ȣ��    üũ��  üũ�ƿ�  �ο�  ���忩��  �������Կ���");
		System.out.println(rvs[index].toString());
		System.out.println("--------------------------------------------------------");
		System.out.println();
	}

	public void cancle() {
		index = search();
		if (index == -1) {
			System.out.println("�����ڸ�Ͽ� �������� �ʴ� �̸��Դϴ�.");
		} else {
			// delete
			for (int i = 0; i < cnt - 1; i++) {
				rvs[i] = rvs[i + 1];
			}
			cnt--;
		}
	}

	public int search() {
		System.out.println("�̸��� �Է����ּ���");
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
			System.out.println("�����ڸ�Ͽ� �������� �ʴ� �̸��Դϴ�.");
		} else {
			System.out.println("��¥����");
			caseA = 1;
			already();
			rvs[index].setCheckIn(dateIn);
			rvs[index].setCheckOut(dateOut);
			rvs[index].setReservedRoomNum(roomNum);
			System.out.println("����Ǿ����ϴ�");
			listOne();
		}
	}

	public void menu() {
		while (true) {
			System.out.println("1�� ����");
			System.out.println("1.����  2.��ȸ  3.����ȸ  4.���ຯ��  5.����  6.���");
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
