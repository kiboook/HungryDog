import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class TimeThread extends JLabel implements Runnable {

	private Thread myThread;
	private JPanel base;					// PlayBasePanel 정보를 받을 객체
	private JLabel lblGame;					// GameOver Label을 받을 객체
	private JButton btnInput, btnRetry;		// 버튼들을 받을 객체
	private JTextField txtInput;			// 텍스트필드를 받을 객체
	private int nSleep, m, s;				// 기본 정보(텀 시간, 분, 초)
	private ImageIcon originIcon, lblIcon;
	private Image originImg, changedImage;
	private Font font;

	public TimeThread() {
		nSleep = 1000;	// 시간이 바뀌는 텀 1초
		// 남은 시간 초기화
		m = 10;	
		s = 0;
		
		font = new Font("Verdana", Font.BOLD, 20);
		
		// 배경 이미지 아이콘 부착
		originIcon = new ImageIcon("images/TimeBoard.png");
		originImg = originIcon.getImage();
		changedImage = originImg.getScaledInstance(150, 100, Image.SCALE_SMOOTH);
		lblIcon = new ImageIcon(changedImage);
		setIcon(lblIcon);
		
		setText("" + m + " : 0" + s);	// 남은 시간 10 : 00 보이게 하기
		setFont(font);
		setForeground(Color.black);
		// label과 글씨 겹치게 하기
		setHorizontalAlignment(SwingConstants.CENTER);
		setHorizontalTextPosition(SwingConstants.CENTER);
		
		myThread = new Thread(this); // 쓰레드 생성
	}

	public void start() {
		myThread.start();
	}

	public void stop() {
		myThread.stop();
	}

	public void run() {

		setVisible(true);
		
		try {
			myThread.sleep(nSleep);
		} catch (Exception e) {
		} // 1초 나게 하기
		
		for (m = 9; m >= 0; m--) {
			for (s = 59; s >= 0; s--) {
				if (s < 10) {
					setText("0" + m + " : 0" + s);
				} else {
					setText("0" + m + " : " + s);
				} // 초가 1자리 수면 0s로 보이게 함.
				
				try { // 텀 1초
					myThread.sleep(nSleep);
				} catch (Exception e) {
				}
			}

		} // 남은 시간 경과 표시
		
		// 이후 시간이 모두 경과하면
		base.setFocusable(false); // 키보드 입력 못 받게 하기
		
		tryView();				// 게임 종료 화면 출력

		// 남은 시간을 빨간색으로 강조
		setFont(font);
		setText("00 : 00");
		setForeground(Color.red);
	}

	public void setBase(JPanel B) {
		base = B;
	}	// 현재 진행되는 레벨의 base 정보 얻기

	public void setLblGame(JLabel Go) {
		lblGame = Go;
	} // GameOver label 전달받기

	public void setEndView(JTextField name, JButton input, JButton re) {
		txtInput = name;
		btnInput = input;
		btnRetry = re;
	} // 게임이 종료됐을 때 보여줄 컨포넌트들 정보 저장

	public void tryView() {
		lblGame.setVisible(true);
		txtInput.setVisible(true);
		btnInput.setVisible(true);
		btnRetry.setVisible(true);
	} // 게임이 종료되면 보여질 컨포넌트들 보이게 하기

	public int getMinute() {
		return m;
	} // 분 정보

	public int getSecond() {
		return s;
	} // 초 정보 

	public void initTime() {
		m = 9;
		s = 60;
	} // 시간 초기화

} // 디지털 시계 끝