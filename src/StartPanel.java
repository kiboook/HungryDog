import javax.swing.*;
import java.awt.*;

public class StartPanel extends JPanel {
	private JButton btnStart, btnRank, btnBGM; // 게임스타트, 랭킹페이지, bgm 관리를 위한 버튼들
	private JLabel labTitle, labdog1, labdog2, labdog3, labdog4; // 제목과 중간에 조작법 설명을 위한 강아지들
	private ImageIcon title; // 제목에 씌우기 위한 이미지아이콘
	private ImageIcon start1, start2, ranking1, ranking2, dog1, dog2, dog3, dog4, bgmOn, bgmOff; // 각 버튼들과 라벨들에 씌워주기 위한
																									// 이미지 아이콘

	public StartPanel() { // 시작하면 뜨는 게임의 시작 패널

		setPreferredSize(new Dimension(600, 700)); // 전체 창 크기 조절
		Color backcolor = new Color(195, 224, 166); // 컬러값 생성
		setBackground(backcolor); // 백그라운드 컬러
		setLayout(null); // setbounds 로 지정해주기 위해

		// ------------------- bgm 버튼을 위한 이미지 아이콘 불러오며, 크기 조정하여 셋팅해줍니다.
		// ------------------------
		ImageIcon originIcon = new ImageIcon("images/bgmOn.png");
		Image originImg = originIcon.getImage();
		Image changedImg = originImg.getScaledInstance(60, 50, Image.SCALE_SMOOTH);
		bgmOn = new ImageIcon(changedImg);
		btnBGM = new JButton("ON", bgmOn);

		originIcon = new ImageIcon("images/bgmOff.png");
		originImg = originIcon.getImage();
		changedImg = originImg.getScaledInstance(60, 50, Image.SCALE_SMOOTH);
		bgmOff = new ImageIcon(changedImg);
		btnBGM.setSelectedIcon(bgmOff);

		btnBGM.setOpaque(false);
		btnBGM.setFocusPainted(false);
		btnBGM.setBorderPainted(false);

		btnBGM.setBackground(backcolor);
		btnBGM.setForeground(backcolor);
		btnBGM.setBounds(500, 20, 75, 50);
		btnBGM.setContentAreaFilled(false);
		add(btnBGM);

		// --------------------------- 게임스타트, 랭킹페이지로 가기 위한 버튼 이미지 아이콘 셋팅
		// ------------------
		originIcon = new ImageIcon("images/start1.png");
		originImg = originIcon.getImage();
		changedImg = originImg.getScaledInstance(300, 150, Image.SCALE_SMOOTH);
		start1 = new ImageIcon(changedImg);

		originIcon = new ImageIcon("images/start2.png");
		originImg = originIcon.getImage();
		changedImg = originImg.getScaledInstance(300, 150, Image.SCALE_SMOOTH);
		start2 = new ImageIcon(changedImg);

		originIcon = new ImageIcon("images/ranking1.png");
		originImg = originIcon.getImage();
		changedImg = originImg.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
		ranking1 = new ImageIcon(changedImg);

		originIcon = new ImageIcon("images/ranking2.png");
		originImg = originIcon.getImage();
		changedImg = originImg.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
		ranking2 = new ImageIcon(changedImg);

		// ------------------------ 중간에 조작법을 위한 강아지들 이미지 셋팅 -------------------
		originIcon = new ImageIcon("images/cute_front.png");
		originImg = originIcon.getImage();
		changedImg = originImg.getScaledInstance(47, 45, Image.SCALE_SMOOTH);
		dog1 = new ImageIcon(changedImg);

		originIcon = new ImageIcon("images/cute_left.png");
		originImg = originIcon.getImage();
		changedImg = originImg.getScaledInstance(45, 43, Image.SCALE_SMOOTH);
		dog2 = new ImageIcon(changedImg);

		originIcon = new ImageIcon("images/cute_right.png");
		originImg = originIcon.getImage();
		changedImg = originImg.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
		dog3 = new ImageIcon(changedImg);

		originIcon = new ImageIcon("images/cute_back.png");
		originImg = originIcon.getImage();
		changedImg = originImg.getScaledInstance(45, 45, Image.SCALE_SMOOTH);
		dog4 = new ImageIcon(changedImg);

		// ---------------------- 제목 이미지 셋팅 ------------------------
		originIcon = new ImageIcon("images/Title.png");
		originImg = originIcon.getImage();
		changedImg = originImg.getScaledInstance(500, 200, Image.SCALE_SMOOTH);
		title = new ImageIcon(changedImg);

		labTitle = new JLabel(title); // 제목 라벨 생성
		labTitle.setBounds(50, 75, 500, 200);
		labTitle.setVisible(true);
		labTitle.setHorizontalAlignment(SwingConstants.CENTER); // 중앙 정렬
		add(labTitle);

		// ------------ 중간에 상하좌우 조작법 설명하는 강아지들 라벨 생성 및 이미지 씌우기 -------------------
		Color curcolor = new Color(226, 115, 111); // 배경 컬러값 저장

		labdog1 = new JLabel("↓", dog1, SwingUtilities.RIGHT);
		labdog1.setFont(new Font("a타임머신", Font.BOLD, 30));
		labdog1.setForeground(curcolor);
		labdog1.setBounds(90, 350, 100, 50);
		add(labdog1);
		labdog2 = new JLabel(" ←", dog2, SwingUtilities.RIGHT);
		labdog2.setFont(new Font("a타임머신", Font.BOLD, 30));
		labdog2.setForeground(curcolor);
		labdog2.setBounds(205, 350, 100, 50);
		add(labdog2);
		labdog3 = new JLabel(" →", dog3, SwingUtilities.RIGHT);
		labdog3.setFont(new Font("a타임머신", Font.BOLD, 30));
		labdog3.setForeground(curcolor);
		labdog3.setBounds(320, 350, 100, 50);
		add(labdog3);
		labdog4 = new JLabel("↑", dog4, SwingUtilities.RIGHT);
		labdog4.setFont(new Font("a타임머신", Font.BOLD, 30));
		labdog4.setForeground(curcolor);
		labdog4.setBounds(420, 350, 100, 50);
		add(labdog4);

		// ----------------------------- 게임 시작 버튼과 랭킹페이지로 가는 버튼 생성 및 셋팅 ---------------
		btnStart = new JButton("GO!", start1);
		btnStart.setBounds(50, 500, 310, 150);
		btnStart.setBorderPainted(false);
		btnStart.setContentAreaFilled(false);
		btnStart.setForeground(backcolor);
		btnStart.setRolloverIcon(start2); // 호버링을 위한 버튼이미지 셋
		btnStart.setPressedIcon(start2); // 버튼 누를때를 위한 이미지 셋
		add(btnStart);

		btnRank = new JButton("RANKING", ranking1);
		btnRank.setBounds(400, 500, 150, 150);
		btnRank.setBorderPainted(false);
		btnRank.setContentAreaFilled(false);
		btnRank.setForeground(backcolor);
		btnRank.setRolloverIcon(ranking2);
		btnRank.setPressedIcon(ranking2);
		add(btnRank);
	}

	// ---------------- 버튼 리스너를 위한 겟 메소드들 -----------------
	public JButton getStartButton() {
		return btnStart;
	}

	public JButton getRankButton() {
		return btnRank;
	}

	public JButton getBGMButton() {
		return btnBGM;
	}

	// ---------- bgm 이미지 아이콘 변경을 위한 메소드 -----------------
	public void changeBgmIcon() {
		if (btnBGM.getText() == "ON") {
			btnBGM.setIcon(bgmOff);
		} else {
			btnBGM.setIcon(bgmOn);
		}
	}
}