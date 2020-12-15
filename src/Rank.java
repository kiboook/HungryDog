import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Rank extends JPanel {
	
	// DB 연결에 필요한 변수 정의
	static Connection con;
	PreparedStatement stmt;
	ResultSet rs;

	String Driver = "";
	String url = "jdbc:mysql://localhost:3306/HungryDog?&serverTimezone=Asia/Seoul&useSSL=false";
	String userid = "HungryDog";
	String pwd = "HungryDog";
	
	public void connectDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

		try { // 데이터베이스를 연결하는 과정
			con = DriverManager.getConnection(url, userid, pwd);
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
	
	private int[] lastScore = new int[2];
	private JButton btnRestart, btnExit; // 재시작을 위해 스타트패널로 가는 restart 버튼과 게임 종료를 위한 exit 버튼
	private JLabel lblTitle, lblSub, lblName[] = new JLabel[5], lblScore[] = new JLabel[5]; // RANKING, NAME SCORE를 띄워줄
																							// 라벨들과 랭킹에 띄워줄 name,
																							// score들을 저장하는 lblName,
																							// lblScore
	
	int idx; // 랭킹 Label을 DB로 부터 받아오고 랭킹 재조회 시 기존 랭킹을 지우는 index
	private ImageIcon restart1, restart2, exit1, exit2; // 재시작, 종료 버튼에 이미지를 씌워주기 위한 이미지아이콘 각각 2개씩
	
	
	public Rank() {
		connectDB();
		Color backcolor = new Color(246, 223, 170);
		setPreferredSize(new Dimension(600, 700));
		setBackground(backcolor);
		setLayout(null);

		// 상단 제목 라벨
		lblTitle = new JLabel("RANKING"); // RANKING 제목 라벨 생성
		lblTitle.setBounds(50, 50, 500, 90); // 좌표 최상단, 크기 지정
		lblTitle.setFont(new Font("Verdana", Font.BOLD, 50)); // 폰트지정
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER); // 중앙 정렬
		add(lblTitle); // Rank에 추가해준다.

		// 부제목 라벨
		lblSub = new JLabel("     NAME          SCORE"); // NAME, SCORE를 띄워줄 서브 타이틀 라벨 생성
		lblSub.setBounds(50, 165, 500, 60); // title 아래에 좌표 설정
		lblSub.setOpaque(true); // 라벨 자체의 background를 나타내기 위한 true
		lblSub.setFont(new Font("Verdana", Font.BOLD, 35));
		lblSub.setForeground(Color.red); // 글자색 변경
		add(lblSub);

		// -------------------------- 이미지 아이콘 셋팅 -----------------------------------
		// 스타트패널로 가는 메인버튼 2가지
		ImageIcon originIcon = new ImageIcon("images/main1.png");
		Image originImg = originIcon.getImage();
		Image changedImg = originImg.getScaledInstance(225, 100, Image.SCALE_SMOOTH);
		restart1 = new ImageIcon(changedImg);
		originIcon = new ImageIcon("images/main2.png");
		originImg = originIcon.getImage();
		changedImg = originImg.getScaledInstance(225, 100, Image.SCALE_SMOOTH);
		restart2 = new ImageIcon(changedImg);

		// 종료를 위한 exit 버튼 2가지
		originIcon = new ImageIcon("images/exit1.png");
		originImg = originIcon.getImage();
		changedImg = originImg.getScaledInstance(225, 100, Image.SCALE_SMOOTH);
		exit1 = new ImageIcon(changedImg);
		originIcon = new ImageIcon("images/exit2.png");
		originImg = originIcon.getImage();
		changedImg = originImg.getScaledInstance(225, 100, Image.SCALE_SMOOTH);
		exit2 = new ImageIcon(changedImg);

		// 재시작 버튼
		btnRestart = new JButton("□ MAIN", restart1);
		btnRestart.setBounds(50, 550, 235, 100);
		btnRestart.setBorderPainted(false);
		btnRestart.setContentAreaFilled(false); // 테두리를 없애주고, 기본 버튼 틀을 투명하게 바꿔준다.
		btnRestart.setForeground(backcolor);
		btnRestart.setRolloverIcon(restart2); // 마우스 올리면 흰색 이미지 변화
		btnRestart.setPressedIcon(restart2); // 버튼 누르면 흰색 이미지로 변화
		add(btnRestart);

		// 종료 버튼
		btnExit = new JButton("Exit", exit1);
		btnExit.setBounds(325, 550, 235, 100);
		btnExit.setBorderPainted(false);
		btnExit.setContentAreaFilled(false);
		btnExit.setForeground(backcolor);
		btnExit.setRolloverIcon(exit2);
		btnExit.setPressedIcon(exit2);
		add(btnExit);
	}

	// 랭킹 진입 여부를 위한 라스트 스코어 get 메서드
	public int[] getLastScore() {
		// 2020.09.16 기존엔 점수만 받아왔지만 기록이 4개 이하인 경우 무조건 랭킹 진입이기 때문에 기록 갯수도 저장
		return lastScore;
	}

	// DB에 기록 저장 메서드, 메서드명 변경 해야 함
	public void setNewRank(int score, String name) {
		
		String query = "INSERT INTO Score_Board VALUES(";
		
		try {
			Statement stmt = con.createStatement();

			String input = query + "'" + name + "', " + score + ");";
//			System.out.println(input);
			stmt.executeUpdate(input);
//			System.out.println("상단의 쿼리문을 통해 DATA 추가 완료 !!!");
		} catch (SQLException e1) {
			// 팝업창으로 띄워야 함
			System.out.println("존재하는 닉네임 입니다.");
		}
	}

	// 랭킹을 최대 5등까지 출력하는 메서드, 메서드명 변경 해야 함
	public void setList() {
		
		idx = 0;
		Statement stmt = null;
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			rs = stmt.executeQuery("SELECT * FROM Score_Board ORDER BY score DESC");
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
		try {
			while (rs.next() && idx < 5) {
				String name = rs.getString(1);
				int score = rs.getInt(2);
				
				// NAME 랭킹 라벨
				lblName[idx] = new JLabel();
				lblName[idx].setBounds(50, 225 + 60 * (idx), 500, 60);
				lblName[idx].setFont(new Font("Verdana", Font.BOLD, 35));
				lblName[idx].setText(idx + 1 + ".  " + name + "\r\n");
				add(lblName[idx]);
				
				// SCORE 랭킹 라벨
				lblScore[idx] = new JLabel();
				lblScore[idx].setBounds(340, 225 + 60 * (idx), 500, 60);
				lblScore[idx].setFont(new Font("Verdana", Font.BOLD, 35));
				lblScore[idx].setText(score + "\r\n");
				add(lblScore[idx]);
				
				lastScore[0] = idx;
				lastScore[1] = score;
				
				idx += 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void hideLabel() { // 랭킹 조회마다 이전 랭킹은 숨김, 메서드명 변경해야함 
		for (int i = 0; i < idx; i++) {
			lblName[i].setVisible(false);
			lblScore[i].setVisible(false);
		}
	}
	
	// 리스너를 위해 버튼들 넘겨주기 위한 get 메소드들
	public JButton getRestart() {
		return btnRestart;
	}

	public JButton getExit() {
		return btnExit;
	}

}