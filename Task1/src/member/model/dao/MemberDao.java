package member.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import member.model.vo.Member;

/**
 * DAO
 * Data Access Object
 * DB�� �����ϴ� Ŭ����
 * DB�� �����ϴ� ���� DAO �ȿ����� �Ͼ�� �ؾ��Ѵ�.
 *
 *1. ����̹�Ŭ���� ���(����1ȸ)
 *2. Connection��ü ����(�����ϰ����ϴ� DB������ url,user,password)
 *3. �ڵ�Ŀ�Կ��� ���� : true(�ڵ�Ŀ��, �⺻��)/false -> app���� ���� Ʈ����������
 *4. PreparedStatement��ü����(�̿ϼ�����(�������� ���� �ȵ� ����)) �� ������
 *5. Statement ��ü ����. DB�� ���� ��û
 *6. ����ó�� DML:int ����, DQL: ResultSet���� ->�ڹٰ�ü�� ��ȯ
 *7. Ʈ�����ó��(DML)
 *8. �ڿ��ݳ�(������ ����)
 *
 */
public class MemberDao {
	
	//������ �� �ֵ��� �ʵ�� ����
	String driverClass = "oracle.jdbc.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	//: ������ ����. => �� 5��   					ip:��Ʈ:db�̸�
	String user = "student"; //user ��ҹ��� ����X
	String password = "student"; //password ��ҹ��� ����

	public int insertMember(Member member) {
		//������ �� �ֵ��� ���������� ����ø�
		Connection conn = null;
		String sql = "insert into member values(?,?,?,?,?,?,?,?,?,default)";
		PreparedStatement pstmt = null;
		int result =0;
		
		try {
			//1. ����̹�Ŭ���� ���(����1ȸ) �̷� ��Ű���� �̷� Ŭ������ �־��.
			Class.forName(driverClass); //����ó�� ����ȭ
			//2. Connection��ü ����(�����ϰ����ϴ� DB������ url,user,password)
			conn = DriverManager.getConnection(url, user, password); //SQLException�ֻ��� ���� Ŭ���� �߻�
			
			//3. �ڵ�Ŀ�Կ��� ����(DML) : true(�ڵ�Ŀ��, �⺻��)/false -> app���� ���� Ʈ����������
			conn.setAutoCommit(false);
			//4. PreparedStatement��ü����(�̿ϼ�����(�������� ���� �ȵ� ����)) �� ������
			pstmt = conn.prepareStatement(sql); //������ �����ϴ� ��ü
			pstmt.setString(1, member.getMemberId()); //ù��° ?�� id�̴�
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getGender());
			pstmt.setInt(5, member.getAge());
			pstmt.setString(6, member.getEmail());
			pstmt.setString(7, member.getPhone());
			pstmt.setString(8, member.getAddress());
			pstmt.setString(9, member.getHobby());
			
			//5. Statement ��ü ����. DB�� ���� ��û
			//6. ����ó�� DML:int ����, DQL: ResultSet���� ->�ڹٰ�ü�� ��ȯ
			result = pstmt.executeUpdate(); //DML -> executeUpdate(Delete �̷��� ����.Update�� ����) |  DQL -> executeQuery
			
			//7. Ʈ�����ó��(DML)
			if(result > 0)
				conn.commit();
			else
				conn.rollback();
		} catch (ClassNotFoundException | SQLException e) {
			//ojdbc6.jar ������Ʈ ��������!
			e.printStackTrace();
		} finally {
			//8. �ڿ��ݳ�(������ ����)
			try {
				if(pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public List<Member> selectAll() {
		Connection conn = null;
		PreparedStatement pstmt = null; //finally������ �����ϱ� ���� ���� ��������
		ResultSet rset = null;
		String sql = "select * from member order by enroll_date desc";
		List <Member> list = null;
		
		try {
			//1. ����̹�Ŭ���� ���(����1ȸ)
			Class.forName(driverClass);
			//2. Connection��ü ����(�����ϰ����ϴ� DB������ url,user,password)
			//3. �ڵ�Ŀ�Կ��� ���� : true(�ڵ�Ŀ��, �⺻��)/false -> app���� ���� Ʈ����������
			conn = DriverManager.getConnection(url,user, password);
			//4. PreparedStatement��ü����(�̿ϼ�����(�������� ���� �ȵ� ����)) �� ������
			pstmt = conn.prepareStatement(sql);
			//5. Statement ��ü ����. DB�� ���� ��û
			rset = pstmt.executeQuery();
			//6. ����ó�� DML:int ����, DQL: ResultSet���� ->�ڹٰ�ü�� ��ȯ
			//������ ���翩�θ��� //���������� ������ ������ �� �ִ�(�������� ���� ������)
			list = new ArrayList<>();
			while(rset.next()) {
				//�÷����� ��ҹ��ڸ� �������� �ʴ´�.
				String memberId = rset.getString("member_id");
				String password = rset.getString("password");
				String memberName = rset.getString("member_name");
				String gender = rset.getString("gender");
				int age = rset.getInt("age");
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("address");
				String hobby= rset.getString("hobby");
				Date enrollDate = rset.getDate("enroll_date");
				
				//�����ü�� �����ؼ� ����Ʈ�� �������� �׾��� ���Ե� ��������,,
				Member member = new Member(memberId, password, memberName, gender, age, email, phone, address, hobby, enrollDate);
				list.add(member);
			}
			//7. Ʈ�����ó��(DML) DQL�϶��� Ʈ������ ó�� ���ص� �޴�.
			
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//8. �ڿ��ݳ�(������ ����)
			try {
				if(rset != null)
					rset.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return list;
	}
	
	public Member selectOne(String memberId) {
		Connection conn = null;
		PreparedStatement pstmt = null; //finally������ �����ϱ� ���� ���� ��������
		ResultSet rset = null;
		String sql = "select * from member where member_id = ?";
		Member member = null;
		
		try {
			//1. ����̹�Ŭ���� ���(����1ȸ)
			Class.forName(driverClass);
			//2. Connection��ü ����(�����ϰ����ϴ� DB������ url,user,password)
			//3. �ڵ�Ŀ�Կ��� ���� : true(�ڵ�Ŀ��, �⺻��)/false -> app���� ���� Ʈ����������
			conn = DriverManager.getConnection(url,user, password);
			//4. PreparedStatement��ü����(�̿ϼ�����(�������� ���� �ȵ� ����)) �� ������
			pstmt = conn.prepareStatement(sql); //select * from member where member_id = 'honggd'
			pstmt.setString(1, memberId);//
			//5. Statement ��ü ����. DB�� ���� ��û
			rset = pstmt.executeQuery(); //0���̾ rset�� ��ȯ�ȴ�.
			//6. ����ó�� DML:int ����, DQL: ResultSet���� ->�ڹٰ�ü�� ��ȯ
			//������ ���翩�θ��� //���������� ������ ������ �� �ִ�(�������� ���� ������)
			while(rset.next()) {
				//�÷����� ��ҹ��ڸ� �������� �ʴ´�.
				memberId = rset.getString("member_id");
				String password = rset.getString("password");
				String memberName = rset.getString("member_name");
				String gender = rset.getString("gender");
				int age = rset.getInt("age");
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("address");
				String hobby= rset.getString("hobby");
				Date enrollDate = rset.getDate("enroll_date");
				
				//�����ü�� �����ؼ� ����Ʈ�� �������� �׾��� ���Ե� ��������,,
				member = new Member(memberId, password, memberName, gender, age, email, phone, address, hobby, enrollDate);
			}
			//7. Ʈ�����ó��(DML) DQL�϶��� Ʈ������ ó�� ���ص� �޴�.
			
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//8. �ڿ��ݳ�(������ ����)
			try {
				if(rset != null)
					rset.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return member;
	}

	public Member selectName(String memberName) {
		Connection conn = null;
		PreparedStatement pstmt = null; //finally������ �����ϱ� ���� ���� ��������
		ResultSet rset = null;
		String sql = "select * from member where member_name like ?" ;
		Member member = null;
		
		try {
			//1. ����̹�Ŭ���� ���(����1ȸ)
			Class.forName(driverClass);
			//2. Connection��ü ����(�����ϰ����ϴ� DB������ url,user,password)
			//3. �ڵ�Ŀ�Կ��� ���� : true(�ڵ�Ŀ��, �⺻��)/false -> app���� ���� Ʈ����������
			conn = DriverManager.getConnection(url,user, password);
			//4. PreparedStatement��ü����(�̿ϼ�����(�������� ���� �ȵ� ����)) �� ������
			pstmt = conn.prepareStatement(sql); //select * from member where member_id = 'honggd'
			pstmt.setString(1, "%"+memberName+"%");//
			//5. Statement ��ü ����. DB�� ���� ��û
			rset = pstmt.executeQuery(); //0���̾ rset�� ��ȯ�ȴ�.
			//6. ����ó�� DML:int ����, DQL: ResultSet���� ->�ڹٰ�ü�� ��ȯ
			//������ ���翩�θ��� //���������� ������ ������ �� �ִ�(�������� ���� ������)
			while(rset.next()) {
				//�÷����� ��ҹ��ڸ� �������� �ʴ´�.
				String memberId = rset.getString("member_id");
				String password = rset.getString("password");
				memberName = rset.getString("member_name");
				String gender = rset.getString("gender");
				int age = rset.getInt("age");
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("address");
				String hobby= rset.getString("hobby");
				Date enrollDate = rset.getDate("enroll_date");
				
				//�����ü�� �����ؼ� ����Ʈ�� �������� �׾��� ���Ե� ��������,,
				member = new Member(memberId, password, memberName, gender, age, email, phone, address, hobby, enrollDate);
			}
			//7. Ʈ�����ó��(DML) DQL�϶��� Ʈ������ ó�� ���ص� �޴�.
			
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//8. �ڿ��ݳ�(������ ����)
			try {
				if(rset != null)
					rset.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return member;

	}

	public int selectUpdate(String memberUpdateId,String memberUpdatePassword, Member member) {
		//������ �� �ֵ��� ���������� ����ø�
		Connection conn = null;
		String sql = "update member set password =?,email=?,phone=?,address=?,hobby=? where member_id = ?and password=?";
		PreparedStatement pstmt = null;
		int result =0;
		
		try {
			//1. ����̹�Ŭ���� ���(����1ȸ) �̷� ��Ű���� �̷� Ŭ������ �־��.
			Class.forName(driverClass); //����ó�� ����ȭ
			//2. Connection��ü ����(�����ϰ����ϴ� DB������ url,user,password)
			conn = DriverManager.getConnection(url, user, password); //SQLException�ֻ��� ���� Ŭ���� �߻�
			
			//3. �ڵ�Ŀ�Կ��� ����(DML) : true(�ڵ�Ŀ��, �⺻��)/false -> app���� ���� Ʈ����������
			conn.setAutoCommit(false);
			//4. PreparedStatement��ü����(�̿ϼ�����(�������� ���� �ȵ� ����)) �� ������
			pstmt = conn.prepareStatement(sql); //������ �����ϴ� ��ü
			pstmt.setString(1, member.getPassword()); //ù��° ?�� id�̴�
			pstmt.setString(2, member.getEmail());
			pstmt.setString(3, member.getPhone());
			pstmt.setString(4, member.getAddress());
			pstmt.setString(5, member.getHobby());
			pstmt.setString(6, memberUpdateId);
			pstmt.setString(7, memberUpdatePassword);
			
		
			//5. Statement ��ü ����. DB�� ���� ��û
			//6. ����ó�� DML:int ����, DQL: ResultSet���� ->�ڹٰ�ü�� ��ȯ
			result = pstmt.executeUpdate(); //DML -> executeUpdate(Delete �̷��� ����.Update�� ����) |  DQL -> executeQuery
			
			//7. Ʈ�����ó��(DML)
			if(result > 0)
				conn.commit();
			else
				conn.rollback();
		} catch (ClassNotFoundException | SQLException e) {
			//ojdbc6.jar ������Ʈ ��������!
			e.printStackTrace();
		} finally {
			//8. �ڿ��ݳ�(������ ����)
			try {
				if(pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public int selectDelete(String memberDeleteId, String memberDeletePassword) {
		//������ �� �ֵ��� ���������� ����ø�
		Connection conn = null;
		String sql = "delete from member where member_id = ? and password = ?";
		PreparedStatement pstmt = null;
		int result =0;
		
		try {
			//1. ����̹�Ŭ���� ���(����1ȸ) �̷� ��Ű���� �̷� Ŭ������ �־��.
			Class.forName(driverClass); //����ó�� ����ȭ
			//2. Connection��ü ����(�����ϰ����ϴ� DB������ url,user,password)
			conn = DriverManager.getConnection(url, user, password); //SQLException�ֻ��� ���� Ŭ���� �߻�
			
			//3. �ڵ�Ŀ�Կ��� ����(DML) : true(�ڵ�Ŀ��, �⺻��)/false -> app���� ���� Ʈ����������
			conn.setAutoCommit(false);
			//4. PreparedStatement��ü����(�̿ϼ�����(�������� ���� �ȵ� ����)) �� ������
			pstmt = conn.prepareStatement(sql); //������ �����ϴ� ��ü
			pstmt.setString(1, memberDeleteId); 
			pstmt.setString(2, memberDeletePassword); 
			
		
			//5. Statement ��ü ����. DB�� ���� ��û
			//6. ����ó�� DML:int ����, DQL: ResultSet���� ->�ڹٰ�ü�� ��ȯ
			result = pstmt.executeUpdate(); //DML -> executeUpdate(Delete �̷��� ����.Update�� ����) |  DQL -> executeQuery
			
			//7. Ʈ�����ó��(DML)
			if(result > 0)
				conn.commit();
			else
				conn.rollback();
		} catch (ClassNotFoundException | SQLException e) {
			//ojdbc6.jar ������Ʈ ��������!
			e.printStackTrace();
		} finally {
			//8. �ڿ��ݳ�(������ ����)
			try {
				if(pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}