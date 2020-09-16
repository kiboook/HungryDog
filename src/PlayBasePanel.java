import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class PlayBasePanel extends JPanel {

	private JLabel lblGO, lblAC; // 게임오버, 올클리어 라벨
	private JTextField txtInput; // 닉네임 입력 받을 텍스트필드
	private JButton btnInput, btnRetry; // 입력, 다시하기 버튼
	private ImageIcon retry1, retry2, input1, input2, gameover, clear; // 아이콘 저장 멤버

	public PlayBasePanel() {
		// PlayBasePanel 기본설정
		setPreferredSize(new Dimension(600, 700));
		setLayout(null);

		ImageIcon originIcon = new ImageIcon("images/GameOver.png");
		Image originImg = originIcon.getImage();
		Image changedImg = originImg.getScaledInstance(400, 200, Image.SCALE_SMOOTH);
		gameover = new ImageIcon(changedImg);
		lblGO = new JLabel(gameover, lblGO.CENTER);
		lblGO.setVisible(false); // 안보이게 하기
		lblGO.setOpaque(false);
		lblGO.setBounds(55, 100, 500, 350);
		add(lblGO); // 게임오버 라벨 아이콘 붙여서 패널에 붙이기

		originIcon = new ImageIcon("images/clear.png");
		originImg = originIcon.getImage();
		changedImg = originImg.getScaledInstance(430, 200, Image.SCALE_SMOOTH);
		clear = new ImageIcon(changedImg);
		lblAC = new JLabel(clear, lblAC.CENTER);
		lblAC.setVisible(false); // 안보이게 하기
		lblAC.setOpaque(false);
		lblAC.setBounds(75, 100, 450, 400);
		add(lblAC); // 올클리어 라벨 아이콘 붙여서 패널에 붙이기

		txtInput = new JTextField(3); // 크기 3만큼
		txtInput.setBounds(75, 503, 150, 65);
		txtInput.setFont(new Font("Verdana", Font.BOLD, 30));
		txtInput.setAlignmentX(CENTER_ALIGNMENT);
		txtInput.setDocument(new JTextFieldLimit(3)); // 입력 가능 글자수 최대 3글자
		txtInput.setVisible(false); // 안보이게 하기
		add(txtInput); // 텍스트필드 붙이기

		originIcon = new ImageIcon("images/input1.png");
		originImg = originIcon.getImage();
		changedImg = originImg.getScaledInstance(150, 70, Image.SCALE_SMOOTH);
		input1 = new ImageIcon(changedImg); // 인풋 버튼 이미지
		originIcon = new ImageIcon("images/input2.png");
		originImg = originIcon.getImage();
		changedImg = originImg.getScaledInstance(150, 70, Image.SCALE_SMOOTH);
		input2 = new ImageIcon(changedImg); // 인풋 버튼 호버링 이미지

		btnInput = new JButton("입력", input1);
		btnInput.setBounds(240, 500, 160, 75);
		btnInput.setBorderPainted(false); // 외곽선 안보이게 하기
		btnInput.setContentAreaFilled(false); // 사각형 점선? 안보이게 하기
		btnInput.setRolloverIcon(input2);
		btnInput.setPressedIcon(input2);
		btnInput.setVisible(false);
		add(btnInput); // 인풋버튼 기본 설정 및 호버링 설정 후 붙이기

		originIcon = new ImageIcon("images/main1.png");
		originImg = originIcon.getImage();
		changedImg = originImg.getScaledInstance(150, 75, Image.SCALE_SMOOTH);
		retry1 = new ImageIcon(changedImg); // 다시시작 버튼 이미지
		originIcon = new ImageIcon("images/main2.png");
		originImg = originIcon.getImage();
		changedImg = originImg.getScaledInstance(150, 75, Image.SCALE_SMOOTH);
		retry2 = new ImageIcon(changedImg); // 다시시작 버튼 호버링 이미지

		btnRetry = new JButton("다시하기", retry1);
		btnRetry.setBounds(410, 500, 160, 75);
		btnRetry.setVisible(false);
		btnRetry.setBorderPainted(false);
		btnRetry.setContentAreaFilled(false);
		btnRetry.setRolloverIcon(retry2);
		btnRetry.setPressedIcon(retry2);
		add(btnRetry); // 다시하기 버튼 설정 및 호버링 설정 후 붙이기
	}

	// ----------------- 닉네임 입력받는 텍스트필드에 3글자 제한 두기 위한 클래스 ------------------
	public class JTextFieldLimit extends PlainDocument {
		private int limit;

		public JTextFieldLimit(int limit) { // 받아온 limit 저장 ( 위에서 3 넘겨준 )
			super();
			this.limit = limit;
		}

		public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
			if (str == null)
				return;
			if (getLength() + str.length() <= limit) // length값 3으로 제한 둠
				super.insertString(offset, str, attr);
		}
	}

	// 라벨, 버튼, 텍스트버튼 전달함수들--------------------------------
	public JLabel getLblGO() {
		return lblGO;
	}

	public JLabel getLblAC() {
		return lblAC;
	}

	public JButton getBtnInput() {
		return btnInput;
	}

	public JTextField getTxtInput() {
		return txtInput;
	}

	public JButton getBtnRetry() {
		return btnRetry;
	}
}