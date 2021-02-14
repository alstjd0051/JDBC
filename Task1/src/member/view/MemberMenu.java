package member.view;

import java.util.List;
import java.util.Scanner;

import javax.swing.text.html.HTMLDocument.Iterator;

import member.controller.MemberController;
import member.model.vo.Member;

public class MemberMenu {
	
	private MemberController memberController = new MemberController();
	//�ʵ�� ��������

	//��ĳ�� �ʵ�� �����ؼ� �ٸ� �޼ҵ��ϰ� ����
	private Scanner sc= new Scanner(System.in);
	
	public void mainMenu() {
		String menu ="======= ȸ�� ���� ���α׷� =======\n"
				+ "1. ȸ�� ��ü ��ȸ \n"
				+ "2. ȸ�� ���̵� ��ȸ\n"
				+ "3. ȸ�� �̸� ��ȸ\n"
				+ "4. ȸ�� ����\n"
				+ "5. ȸ�� ��������\n"
				+ "6. ȸ��Ż��\n"
				+ "0. ���α׷� ������\n"
				+ "--------------------------\n"
				+ "���� : ";
		
		
		while(true) {
			System.out.print(menu);
			int choice = sc.nextInt();
			Member member = null;
			int result = 0;
			String msg =null;
			List<Member> list = null;
			String memberId = null;
			String memberName = null;
			String memberUpdateId = null;
			String memberUpdatePassword = null;
			String memberDeleteId = null;
			String memberDeletePassword = null;
			
			switch(choice) {
				case 1:
					list = memberController.selectAll();
					displayMemberList(list);
					break;
				case 2: 
					//���̵� �� �Է� -> 1�� �Է�X ->0��
					memberId = inputMemberId();
					member = memberController.selectOne(memberId);
					displayMember(member);
					break;
				case 3: 
					memberName =  inputMemberName();
					member = memberController.selectName(memberName);
					displayMemberName(member);
					break;
				case 4: 
					//1.�ű�ȸ������ �Է� ->Member��ü
					member = inputMember();
					System.out.println("�ű�ȸ�� Ȯ�� : "+member);
					//2.controller�� ȸ������ ��û(�޼ҵ�ȣ��) �̶� Member��ü�� ����. 
					// ->int����(ó���� ���� ����)
					result = memberController.insertMember(member);
					//3.int�� ���� �б�ó��
					msg = result > 0 ? "ȸ�� ���� ����!" :"ȸ�� ���� ����!";
					displayMsg(msg);
					break;
				case 5: 
					memberUpdateId = inputId("����");
					memberUpdatePassword = inputPassword("����");
					list = memberController.selectAll();
					member = searchMem(memberUpdateId,memberUpdatePassword,list);
					//�Է��� id�� �ش��ϴ� ��ü�� ����
					if(member != null) {
						member = inputUpdate(member);						
						result = memberController.selectUpdate(memberUpdateId,memberUpdatePassword,member);
						msg = result > 0 ? "ȸ�� ���� ���� ����!" :"ȸ�� ���� ���� ����!";
						displayMsg(msg);
						displayMember(member);
					}
					break;
				case 6: 
					memberDeleteId = inputId("����");
					memberDeletePassword = inputPassword("����");
					result = memberController.selectDelete(memberDeleteId,memberDeletePassword);
					msg = result > 0 ? "ȸ�� Ż�� ����!" :"ȸ�� Ż�� ����!";
					displayMsg(msg);
					break;
				case 0:
					System.out.print("������ �����ðڽ��ϱ�?(y/n) : ");
					if(sc.next().toLowerCase().charAt(0)== 'y')
						return; //����޼ҵ�(mainMenu)�� ȣ���� ������ ���ư�
					break;
				default:
					System.out.println("---�߸��Է��ϼ̽��ϴ�---");
			}
		}
		
	}

	
	//5,6 �������� �� password �Է�
	private String inputPassword(String select) {
		System.out.print(select+"�� id�� ��й�ȣ �Է� : ");
		return sc.next();
		
	}



	//5,6 �������� �� id �Է�
	private String inputId(String select) {
		System.out.print(select+"�� id �Է� : ");
		return sc.next();
	}

	//�Է��� id,��й�ȣ�� ��ġ�ϴ� �ش��ϴ� ��ü�� ����
	private Member searchMem(String memberUpdateId, String memberUpdatePassword, List<Member> list) {
		for(Member member :list) {
			if(memberUpdateId.equals(member.getMemberId())&&memberUpdatePassword.equals(member.getPassword())) {
				return member;
			}	
		}
		return null;
	}
	

	/**
	 * ������ ���� �Է�
	 * @param memberUpdate 
	 * @return
	 */
	private Member inputUpdate(Member member) {
		System.out.println("������ ȸ�������� �Է��ϼ���.");
			System.out.print("��й�ȣ: ");
			member.setPassword(sc.next());
			System.out.print("�̸��� : ");
			member.setEmail(sc.next());
			System.out.print("��ȭ��ȣ(-���� �Է�) : ");
			member.setPhone(sc.next());
			sc.nextLine(); //���ۿ� ���� ���๮�� ������ ��(next�迭 - nextLine) next�� nextLine ���� �ѹ� 
			System.out.print("�ּ�: ");
			member.setAddress(sc.nextLine());
			System.out.print("��� : ");
			member.setHobby(sc.next());
		return member;
		
	}




	/**
	 * DB���� ������ ������ id�� �Է�
	 * @return
	 */
	private String inputUpdateId() {
		System.out.print("������ id �Է� : ");
		return sc.next();
	}

	/**
	 * DB���� ��ȸ�� �̸� ���
	 * @param member
	 */
	private void displayMemberName(Member member) {
		if(member == null)
			System.out.println(">>>> ��ȸ�� ȸ���� �����ϴ�.");
		else {
			System.out.println("*******************************************************");
			System.out.println(member);
			System.out.println("*******************************************************");
		}
	}

	/**
	 * DB���� �̸� ��ȸ
	 * 
	 */
	private String inputMemberName() {
		System.out.print("��ȸ�� �̸� �Է� : ");
		return sc.next();
	}
 
	/**
	 * DB���� ��ȸ�� 1���� ȸ�� ���
	 * @param member
	 */
	private void displayMember(Member member) {
		if(member == null)
			System.out.println(">>>> ��ȸ�� ȸ���� �����ϴ�.");
		else {
			System.out.println("*******************************************************");
			System.out.println(member);
			System.out.println("*******************************************************");
	
		}
	}
	
	/**
	 * ��ȸ�� ȸ�����̵� �Է�
	 * @return
	 */
	private String inputMemberId() {
		System.out.print("��ȸ�� ���̵� �Է� : ");
		return sc.next();
	}

	/**
	 * DB���� ��ȸ�� ȸ����ü n���� ���
	 * @param list
	 */
	private void displayMemberList(List<Member> list) {
		if(list == null || list.isEmpty()) {
			System.out.println(">>>> ��ȸ�� ���� �����ϴ�.");
		}
		else {
			System.out.println("************************************************************************************");
			for(Member m :list) {
				System.out.println(m);
			}
			System.out.println("************************************************************************************");
		}
	}

	/**
	 * DMLó����� �뺸��
	 */
	private void displayMsg(String msg) {
		System.out.println(">>> ó����� : " + msg);
	}

	/**
	 * �ű�ȸ�� ���� �Է�
	 * 
	 */
	private Member inputMember() {
		System.out.println("���ο� ȸ�������� �Է��ϼ���.");
		Member member = new Member();
		System.out.print("���̵� : ");
		member.setMemberId(sc.next());
		System.out.print("�̸� : ");
		member.setMemberName(sc.next());
		System.out.print("��й�ȣ: ");
		member.setPassword(sc.next());
		System.out.print("����: ");
		member.setAge(sc.nextInt());
		System.out.print("����(M/F) : "); //m, f �ȉ�
		member.setGender(String.valueOf(sc.next().toUpperCase().charAt(0)));//String.valueOf ->String���� ��ȭ
		System.out.print("�̸��� : ");
		member.setEmail(sc.next());
		System.out.print("��ȭ��ȣ(-���� �Է�) : ");
		member.setPhone(sc.next());
		sc.nextLine(); //���ۿ� ���� ���๮�� ������ ��(next�迭 - nextLine) next�� nextLine ���� �ѹ� 
		System.out.print("�ּ�: ");
		member.setAddress(sc.nextLine());
		System.out.print("��� : ");
		member.setHobby(sc.next());
		return member;
	}

}