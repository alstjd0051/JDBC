package member.controller;

import java.util.List;

import member.model.dao.MemberDao;
import member.model.vo.Member;

/**
 * 
 * MVC������ ���������� ��ü�帧�� ����.
 * 
 * view�����κ��� ��û�� �޾Ƽ� dao�� �ٽ� ��û.
 * �� ����� view�ܿ� �ٽ� ����.
 *
 */

public class MemberController {

	//dao�� ���������� dao��ü�� �ʿ��ϴ�
	private MemberDao memberDao = new MemberDao();

	public int insertMember(Member member) {
		return memberDao.insertMember(member);
	}

	public List<Member> selectAll() {
		return memberDao.selectAll();
	}

	public Member selectOne(String memberId) {
		return memberDao.selectOne(memberId);
	}

	public Member selectName(String memberName) {
		return memberDao.selectName(memberName);
	}

	public int selectUpdate(String memberUpdateId,String memberUpdatePassword, Member member) {
		return memberDao.selectUpdate(memberUpdateId,memberUpdatePassword,member);
	}

	public int selectDelete(String memberDeleteId, String memberDeletePassword) {
		return memberDao.selectDelete(memberDeleteId,memberDeletePassword);
	}


}