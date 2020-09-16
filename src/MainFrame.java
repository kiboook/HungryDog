import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MainFrame extends JFrame {
	CardLayout card = new CardLayout(); // frame 컨포넌트 배치 구조
	StartPanel first; // 시작페이지
	Rank rank; // 랭크페이지
	PlayBasePanel base[] = new PlayBasePanel[9]; // 플레이 페이지
	PlayPanel play; // 플레이화면
	JButton b = new JButton(); // 버튼리스너를 받을 버튼의 정보를 받음
	JLabel lblStage, lblScore, lblMove; // 게임 진행 상황을 표시해주는 label들
	JPanel PlayBar = new JPanel(); // 게임 진행 상황 표시 Panel
	TimeThread trdTime; // 남은시간을 표시해주는 label
	String intxt; // textField에서 읽어올 텍스트
	int level, nMove, nScore, a[][]; // 게임 진행 정보 a : 맵 배열 전달 받는 배열
	MapArray mapArray; // 맵 정보
	ImageIcon originIcon, Icon;
	Image originImg, changedImage;
	PlayMusic BGM, Bark; // BGM과 캐릭터 이동시 효과음

	public MainFrame() {

		// play bar values init
		level = 1;
		nMove = 0;
		nScore = 0;

		Font font = new Font("Verdana", Font.BOLD, 20); // 중복으로 사용 될 폰트 설정

		// init frame
		setTitle("배고픈 댕댕이");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 700);
		setBackground(Color.black);
		setLayout(card);

		// ------------start화면 만들기-----------------
		first = new StartPanel();

		b = first.getStartButton(); // start버튼에 액션달기
		b.addActionListener(new ButtonListener());

		b = first.getRankButton(); // rank버튼에 액션달기
		b.addActionListener(new ButtonListener());

		b = first.getBGMButton(); // rank버튼에 액션달기
		b.addActionListener(new ButtonListener());
		getContentPane().add(first); // frame에 startPanel 추가
		// --------------------------------------------------

		for (int i = 0; i < 9; i++) {
			base[i] = new PlayBasePanel(); // stage 수만큼 만들고
			base[i].addKeyListener(new KeyboardListener()); // Panel마다 키리스너 부여
			b = base[i].getBtnRetry(); // 다시시작 버튼에 액션달기
			b.addActionListener(new ButtonListener());
			b = base[i].getBtnInput(); // 입력버튼에 액션달기
			b.addActionListener(new ButtonListener());
			getContentPane().add(base[i]); // 순서대로 frame에 추가
		} // playBasePanel 초기화

		// ------------rank화면 만들기--------------------------
		rank = new Rank();

		b = rank.getRestart(); // restart버튼에 액션달기
		b.addActionListener(new ButtonListener());

		b = rank.getExit(); // exit버튼에 액션달기
		b.addActionListener(new ButtonListener());
		getContentPane().add(rank);
		// --------------------------------------------------

		// --------PlayBar에 붙여질 Label들 구성--------------------
		setIconStage();
		lblStage = new JLabel(Icon); // stage 아이콘 붙이기
		setIconScore();
		lblScore = new JLabel("" + nScore, Icon, SwingConstants.CENTER); // score배경 아이콘 붙이기
		setIconMove();
		lblMove = new JLabel("" + nMove, Icon, SwingConstants.CENTER); // move배경 아이콘 붙이기

		lblStage.setBounds(0, 0, 100, 100); // lblStage 초기화
		lblStage.setOpaque(true);
		lblStage.setBackground(Color.white);

		lblScore.setBounds(100, 0, 200, 100); // lblScore 초기화
		lblScore.setFont(font);
		lblScore.setForeground(Color.black);
		lblScore.setOpaque(true);
		lblScore.setHorizontalTextPosition(SwingConstants.CENTER);
		lblScore.setBackground(Color.blue);

		lblMove.setBounds(300, 0, 150, 100); // lblMove 초기화
		lblMove.setFont(font);
		lblMove.setForeground(Color.black);
		lblMove.setOpaque(true);
		lblMove.setHorizontalTextPosition(SwingConstants.CENTER);
		lblMove.setBackground(Color.red);

		PlayBar.setLayout(null);
		PlayBar.setBounds(0, 0, 600, 100); // PlayBar 위치

		PlayBar.add(lblStage);
		PlayBar.add(lblScore);
		PlayBar.add(lblMove); // 게임 진행 표시 label들 붙이기
		// ------------------------------------------------------

		BGM = new PlayMusic();
		BGM.startMusic(); // 배경음악 받아서 재생
		Bark = new PlayMusic(); // 효과음 객체 생성

		pack();
		setVisible(true);
	}

	public class ButtonListener implements ActionListener { // 버튼리스너

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource(); // 버튼 객체 받기

			if (b.getText().equals("GO!")) { // 시작버튼 누르면

				BGM.stopMusic(); // 배경음악 멈춤

				// -------------1단계 맵배열 받아서 PlayPanel 만들기-------------
				mapArray = new MapArray(level);
				a = mapArray.getArray();
				play = new PlayPanel(a);
				// ----------TimeThread 생성해서 PlayBar에 붙이기-------------
				trdTime = new TimeThread();
				trdTime.setBounds(450, 0, 150, 100);
				trdTime.setOpaque(true);
				trdTime.setBackground(Color.white);
				trdTime.start(); // 시간 재기
				trdTime.setBase(base[level - 1]); // PlayBasePanel정보 넘겨주기
				trdTime.setLblGame(base[level - 1].getLblGO()); // GameOver Label 넘겨주기
				trdTime.setEndView(base[level - 1].getTxtInput(), base[level - 1].getBtnInput(),
						base[level - 1].getBtnRetry()); // 1스테이지 PlayBasePanel 종료 화면 구성
				PlayBar.add(trdTime);

				base[level - 1].add(PlayBar); // 1스테이지 base에 PlayBar 추가
				base[level - 1].add(play); // 1스테이지 base에 play(Panel) 추가

				card.next(getContentPane()); // 첫 스테이지 화면으로 넘어감

				base[level - 1].setFocusable(true); // 키보드 입력 받을 수 있게 해주기
				base[level - 1].requestFocus(); // 첫페이지에 포커스 맞추기

			} else if (b.getText().equals("□ MAIN")) {
				card.first(getContentPane()); // 시작 화면으로
			} else if (b.getText().equals("RANKING")) {
				rank.hideLabel();
				rank.setList(); // 랭킹리스트 정리 후
				card.last(getContentPane()); // 랭킹 화면으로 넘어가기
			} else if (b.getText().equals("Exit")) { // exit버튼 누르면 창닫기
				System.exit(0);
			} else if (b.getText().equals("다시하기")) {
				card.first(getContentPane()); // 시작 화면으로 가기
				initPlayBasePanels(); // PlayBasePanel들 다시 초기화 하기
			} else if (b.getText().equals("입력")) {
				JTextField txtfield = base[level - 1].getTxtInput(); // 텍스트필드 전달받아서 문자열 받기
				String intxt = txtfield.getText();

				if (intxt.length() != 3) { // 3글자가 아니면 빈 칸으로 만들고 경고창 띄우기
					txtfield.setText("");
					JOptionPane.showMessageDialog(txtfield, "닉네임을 3글자로 입력해주세요.");
				} else { // 3글자면
					txtfield.setText(intxt.toUpperCase());
					intxt = intxt.toUpperCase(); // 대문자로 만들어주기.
					rank.setNewRank(nScore, intxt); // 랭크 패널에 게임 정보 넘겨주고
					rank.hideLabel();
					rank.setList(); // 정보리스트 정리하기
					// 2020.09.16 조건 수정
					if (rank.getLastScore()[0] < 4 || rank.getLastScore()[1] < nScore)
						JOptionPane.showMessageDialog(txtfield, "랭킹에 진입했습니다.");
					else
						JOptionPane.showMessageDialog(txtfield, "랭킹에 진입하지 못했습니다.");
					
//					if (rank.getLastScore() == nScore) // 랭킹 진입 확인창 띄우기
//						JOptionPane.showMessageDialog(txtfield, "랭킹에 진입하지 못했습니다.");
//					else
//						JOptionPane.showMessageDialog(txtfield, "랭킹에 진입했습니다.");

					card.last(getContentPane()); // 랭크 화면으로 가기
					initPlayBasePanels(); // PlayBasePanel들 다시 초기화
				}
			} // if(b.getText().equals()) ~ else if()
			else if (b.getText().equals("ON")) {
				first.changeBgmIcon(); // 버튼 모양 Off로 바꾸기
				b.setText("OFF");
				BGM.stopMusic(); // 음악 끄기
			} else if (b.getText().equals("OFF")) {
				first.changeBgmIcon(); // 버튼 모양 On으로 바꾸기
				b.setText("ON");
				BGM.start(); // 음악 키기
			}

		} // actionPerformed

	} // ButtonListener class

	public class KeyboardListener implements KeyListener {

		@Override
		public void keyReleased(KeyEvent e) {
			int keyEvent = e.getKeyCode();

			if (keyEvent >= 37 && keyEvent <= 40) {

				play.move(keyEvent); // 움직이기
				play.view(keyEvent); // 화면 보여주기

				if (!(play.getIsMovable())) { // 움직이지 못한 상황이라면
					nMove--; // 움직임 횟수 증가되지 않게 하기
					play.setIsMovable(true);
				} // if
				Bark.moveMusic(); // 움직일 때 소리 출력

				lblMove.setText("" + ++nMove); // 움직임 횟수 갱신후 출력

				if (play.isGameOver()) { // 게임오버됐다면?
					base[level - 1].setFocusable(false); // 키보드 입력 못 받게 하고
					trdTime.stop(); // 쓰레드 정지 시킴.
					trdTime.tryView(); // 종료화면 출력
					trdTime = null;
				}

				else if (play.isGameClear()) { // 스테이지 클리어했다면

					level = level + 1; // 레벨 올려주고

					if (level == 10) { // 스테이지 레벨이 10이면
						level--;
						base[level - 1].setFocusable(false); // 키포커스 없애주고
						trdTime.setLblGame(base[level - 1].getLblAC()); // 종료화면 라벨을 ALLClear로 설정
						trdTime.setEndView(base[level - 1].getTxtInput(), base[level - 1].getBtnInput(),
								base[level - 1].getBtnRetry()); // 종료 화면 구성(버튼, 텍스트 필드)
						trdTime.stop(); // 시계 멈추고
						trdTime.tryView(); // 종료 화면 출력
						trdTime = null;
					} else {

						base[level - 2].remove(play); // 전 단계 스테이지 화면 없애기

						nScore = nScore + ((trdTime.getMinute() * 60 + trdTime.getSecond()) * level - nMove); // 점수계산
						nMove = 0; // 움직임 횟수 초기화

						// PlayBar 리셋
						setIconStage();
						lblStage.setIcon(Icon);
						lblScore.setText("" + nScore);
						lblMove.setText("" + nMove);

						// 맵 배열 다시 받고 PlayPanel 재구성---------
						mapArray = new MapArray(level);
						a = mapArray.getArray();
						play = new PlayPanel(a);
						// ------------------------------------

						// -------Time Thread Label 초기화
						trdTime.setBase(base[level - 1]);
						trdTime.setLblGame(base[level - 1].getLblGO());
						trdTime.setEndView(base[level - 1].getTxtInput(), base[level - 1].getBtnInput(),
								base[level - 1].getBtnRetry());
						trdTime.initTime();
						// -------------------------------------

						// 다음단계 base 구성
						base[level - 1].add(PlayBar);
						base[level - 1].add(play);

						card.next(getContentPane()); // 다음단계로 넘어가기
						base[level - 1].requestFocus(); // 다음단계로 포커스 맞추기
					}
				} // if isGameClear

			} // ↑, ↓, ←, → 키를 누르면
			else if (keyEvent == 90) {
				play.undo(); // 직전 움직임으로 돌아가기
				play.view(keyEvent); // 화면 출력
				Bark.moveMusic(); // 움직임 효과음 출력
			} // Z 키를 누르면
		} // keyReleased()

		@Override
		public void keyTyped(KeyEvent arg0) {
		}

		@Override
		public void keyPressed(KeyEvent e) {
		}

	} // KeyboradListener class

	void initPlayBasePanels() {

		for (int i = 0; i < level; i++) {
			base[i].getBtnInput().setVisible(false);
			base[i].getBtnRetry().setVisible(false);
			base[i].getTxtInput().setText("");
			base[i].getTxtInput().setVisible(false);
			base[i].getLblGO().setVisible(false);
			base[i].getLblAC().setVisible(false);
			base[i].remove(play);
			base[i].setFocusable(true);
		} // 현재 스테이지까지의 BasePanel들의 컨포넌트들 안보이게 하고
			// 게임 정보 초기화
		nMove = 0;
		nScore = 0;
		level = 1;

		setIconStage(); // 스테이지 단계를 표시할 아이콘을 초기화
		lblStage.setIcon(Icon);

		lblScore.setText("" + nScore);
		lblScore.setText("" + nMove);
	}

	void setIconStage() { // 아이콘을 현재 레벨을 표시해주는 아이콘으로 바꿈
		String strLevel = String.valueOf(level);
		originIcon = new ImageIcon("images/stage" + strLevel + ".png"); // 단계 표시!
		originImg = originIcon.getImage();
		changedImage = originImg.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		Icon = new ImageIcon(changedImage);
	}

	void setIconScore() { // 아이콘을 score 배경 아이콘으로 바꿈
		originIcon = new ImageIcon("images/ScoreBoard.png"); // 단계 표시!
		originImg = originIcon.getImage();
		changedImage = originImg.getScaledInstance(200, 100, Image.SCALE_SMOOTH);
		Icon = new ImageIcon(changedImage);
	}

	void setIconMove() { // 아이콘을 score 배경 아이콘으로 바꿈
		originIcon = new ImageIcon("images/MoveBoard.png"); // 단계 표시!
		originImg = originIcon.getImage();
		changedImage = originImg.getScaledInstance(150, 100, Image.SCALE_SMOOTH);
		Icon = new ImageIcon(changedImage);
	}

	public static void main(String[] args) {

		new MainFrame();
	}

}