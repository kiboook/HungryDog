import javax.swing.*;
import java.awt.*;

public class PlayPanel extends JPanel {
	private int mapArray[][]; // 화면에 보여질 맵 정보
	private int player_x, player_y, BoxX[] = new int[6], BoxY[] = new int[6], GoalX[] = new int[6],
			GoalY[] = new int[6], goal_cnt = 0, cnt, undo_box_x = 0, undo_box_y = 0, nUndo = 0; // 캐릭터 좌표, 뼈다귀들 좌표,
																								// 밥그릇좌표, 밥그릇 갯수, 뼈다귀
																								// 갯수, 이전 박스 좌표, 언두플래그
	private JLabel Map[][] = new JLabel[12][12], lblCharacter, lblBowl[] = new JLabel[6], lblBox[] = new JLabel[6]; // 화면에
																													// 좌표마다
																													// 보여질
																													// 라벨들(플레이
																													// 화면)
	private boolean isMovable = true, isGameOver; // 움직였는지, 게임 오버됐는지 반환
	private ImageIcon dog_front, dog_back, dog_right, dog_left, wall, ground, bone, tree, bowl, fullbowl; // 아이콘 이름들

	public PlayPanel(int a[][]) {
		cnt = 0;
		mapArray = a; // 맵 배열 전달 받기

		// 패널 기본 설정
		setBounds(0, 100, 600, 600);
		setBackground(Color.red);
		setLayout(null);

		// ---------------------------아이콘 저장--------------------------
		ImageIcon originIcon = new ImageIcon("images/cute_front.png"); // 캐릭터 앞
		Image originImg = originIcon.getImage();
		Image changedImg = originImg.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		dog_front = new ImageIcon(changedImg);

		originIcon = new ImageIcon("images/cute_back.png"); // 캐릭터 뒤
		originImg = originIcon.getImage();
		changedImg = originImg.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		dog_back = new ImageIcon(changedImg);

		originIcon = new ImageIcon("images/cute_left.png"); // 캐릭터 왼쪽
		originImg = originIcon.getImage();
		changedImg = originImg.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		dog_left = new ImageIcon(changedImg);

		originIcon = new ImageIcon("images/cute_right.png"); // 캐릭터 오른쪽
		originImg = originIcon.getImage();
		changedImg = originImg.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		dog_right = new ImageIcon(changedImg);

		originIcon = new ImageIcon("images/wall.jpg"); // 벽
		originImg = originIcon.getImage();
		changedImg = originImg.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		wall = new ImageIcon(changedImg);

		ground = new ImageIcon("images/ground.jpg"); // 땅

		originIcon = new ImageIcon("images/bone.png"); // 뼈다귀
		originImg = originIcon.getImage();
		changedImg = originImg.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		bone = new ImageIcon(changedImg);

		originIcon = new ImageIcon("images/bowl.png"); // 빈 밥그릇
		originImg = originIcon.getImage();
		changedImg = originImg.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		bowl = new ImageIcon(changedImg);

		originIcon = new ImageIcon("images/fullbowl.png"); // 꽉찬 밥그릇
		originImg = originIcon.getImage();
		changedImg = originImg.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		fullbowl = new ImageIcon(changedImg);

		originIcon = new ImageIcon("images/tree.jpg"); // 맵 밖
		originImg = originIcon.getImage();
		changedImg = originImg.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		tree = new ImageIcon(changedImg);
		// ----------------------------------------------------------------------

		// -----------맵배열의 정보에 따라 캐릭터, 뼈다귀, 밥그릇을 먼저 라벨로 그려서 화면의 맨 위로 보이게 하기
		// ----------한 칸당 50x50의 크기
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 12; j++) {
				if (mapArray[i][j] == 4) { // 캐릭터 좌표 저장
					player_x = j;
					player_y = i;
					mapArray[i][j] = 0;
					lblCharacter = new JLabel(dog_front);
					lblCharacter.setBounds(player_x * 50, player_y * 50, 50, 50);
					lblCharacter.setVisible(true);
					add(lblCharacter);
				} else if (mapArray[i][j] == 2) { // 뼈다귀의 갯수만큼 좌표 저장, 그리기
					BoxX[cnt] = j;
					BoxY[cnt] = i;

					lblBox[cnt] = new JLabel(bone);
					lblBox[cnt].setBounds(BoxX[cnt] * 50, BoxY[cnt] * 50, 50, 50);
					lblBox[cnt].setVisible(true);
					add(lblBox[cnt]);

					cnt++;
				} else if (mapArray[i][j] == 3) { // 밥그릇의 갯수만큼 좌표저장, 그리기
					GoalX[goal_cnt] = j;
					GoalY[goal_cnt] = i;

					lblBowl[goal_cnt] = new JLabel(bowl);
					lblBowl[goal_cnt].setBounds(GoalX[goal_cnt] * 50, GoalY[goal_cnt] * 50, 50, 50);
					lblBowl[goal_cnt].setVisible(true);
					add(lblBowl[goal_cnt]);

					goal_cnt++;
				}
			}
		}

		// ---------------------------------맵 그리기------------------------------------
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 12; j++) { // 땅 그리기 - 캐릭터나 뼈다귀, 밥그릇은 땅 위에 올려지기 때문에 이 좌표들도 땅으로 그려준다.
				if (mapArray[i][j] == 4 || mapArray[i][j] == 2 || mapArray[i][j] == 0 || mapArray[i][j] == 3) {
					Map[i][j] = new JLabel(ground);
					Map[i][j].setOpaque(true);
					add(Map[i][j]);
				} else if (mapArray[i][j] == 1) { // 벽 그리기
					Map[i][j] = new JLabel(wall);
					Map[i][j].setOpaque(true);
					add(Map[i][j]);
				} else if (mapArray[i][j] == 5) { // 밖 그리기
					Map[i][j] = new JLabel(tree);
					Map[i][j].setOpaque(true);
					add(Map[i][j]);
				}
				Map[i][j].setBounds(j * 50, i * 50, 50, 50);
				Map[i][j].setVisible(true);

			}
		} // 캐릭터 좌표, 박스 좌표 저장

	}

	public void move(int key) { // 캐릭터와 뼈다귀, 밥그릇 좌표 옮기는 메소드

		switch (key) { // 방향키 값을 받아와서 그 값에 따라 움직임
		case 38: // UP-------------------------------------------------------------------------------------------
			player_y--; // 캐릭터 y좌표를 미리 -1 옮겨줌
			nUndo = 1; // 캐릭터만 움직임
			if (mapArray[player_y][player_x] == 2) // 플레이어 이동할 좌표가 Box라면
			{
				if (mapArray[player_y - 1][player_x] == 0 || mapArray[player_y - 1][player_x] == 3) // 박스가 이동해야할 부분이
																									// 길이나 골이라면
				{
					mapArray[player_y][player_x] = 0; // 캐릭터 자리 0으로 만들고
					mapArray[player_y - 1][player_x] = 2; // 위를 박스로 바꾼다
					undo_box_y = player_y - 1; // 박스 위치 기억해주고
					undo_box_x = player_x;
					for (int i = 0; i < goal_cnt; i++) {
						if (BoxX[i] == player_x && BoxY[i] == player_y) {
							BoxY[i] = undo_box_y; // 박스 좌표 저장
						}
					}
					nUndo = 11; // 캐릭터와 박스 모두 움직임
					isMovable = true;
				} else { // 벽이면
					player_y++; // 원위치
					isMovable = false; // 못움직임
					nUndo = 0; // 언두도 못함
				}

			} else if (mapArray[player_y][player_x] == 1) {
				player_y++; // 원위치
				isMovable = false; // 못움직임
				nUndo = 0; // 언두도 못함
			}
			break; // 아래, 왼쪽, 오른쪽도 같은 방법으로 바꿔준다.
		case 40: // DOWN-------------------------------------------------------------------------------
			player_y++;
			nUndo = 2;
			if (mapArray[player_y][player_x] == 2) {
				if (mapArray[player_y + 1][player_x] == 0 || mapArray[player_y + 1][player_x] == 3) {
					mapArray[player_y][player_x] = 0;
					mapArray[player_y + 1][player_x] = 2;
					undo_box_x = player_x;
					undo_box_y = player_y + 1;
					for (int i = 0; i < goal_cnt; i++) {
						if (BoxX[i] == player_x && BoxY[i] == player_y) {
							BoxY[i] = undo_box_y;
						}
					}
					nUndo = 21;
					isMovable = true;
				} else {
					player_y--;
					isMovable = false;
					nUndo = 0;
				}
			} else if (mapArray[player_y][player_x] == 1) {
				player_y--;
				isMovable = false;
				nUndo = 0;
			}
			break;
		case 37: // LEFT----------------------------------------------------------------------------------
			player_x--;
			nUndo = 3;
			if (mapArray[player_y][player_x] == 2) {
				if (mapArray[player_y][player_x - 1] == 0 || mapArray[player_y][player_x - 1] == 3) {
					mapArray[player_y][player_x] = 0;
					mapArray[player_y][player_x - 1] = 2;
					undo_box_x = player_x - 1;
					undo_box_y = player_y;
					for (int i = 0; i < goal_cnt; i++) {
						if (BoxX[i] == player_x && BoxY[i] == player_y) {
							BoxX[i] = undo_box_x;
						}
					}
					nUndo = 31;
					isMovable = true;
				} else {
					player_x++;
					isMovable = false;
					nUndo = 0;
				}
			} else if (mapArray[player_y][player_x] == 1) {
				player_x++;
				isMovable = false;
				nUndo = 0;
			}
			break;
		case 39: // RIGHT--------------------------------------------------------------------------------------
			player_x++; // 캐릭터 오른쪽으로 이동(x좌표 + 1)
			nUndo = 4;
			if (mapArray[player_y][player_x] == 2) {
				if (mapArray[player_y][player_x + 1] == 0 || mapArray[player_y][player_x + 1] == 3) {
					mapArray[player_y][player_x] = 0;
					mapArray[player_y][player_x + 1] = 2;
					undo_box_x = player_x + 1;
					undo_box_y = player_y;
					for (int i = 0; i < goal_cnt; i++) {
						if (BoxX[i] == player_x && BoxY[i] == player_y) {
							BoxX[i] = undo_box_x;
						}
					}
					nUndo = 41;
					isMovable = true;
				} else {
					player_x--;
					isMovable = false;
					nUndo = 0;
				}

			} else if (mapArray[player_y][player_x] == 1) {
				player_x--;
				isMovable = false;
				nUndo = 0;
			}
			break;
		}
	} // move

	public void undo() {	
		
		switch (nUndo) { // nUndo값에 따라 직전 상태로 바뀜
		case 1:
			move(40); // 캐릭터만 아래로 움직여줌
			break;
		case 11: // 캐릭터와 뼈다귀 모두 아래로 움직여줌
			mapArray[undo_box_y][undo_box_x] = 0;
			mapArray[undo_box_y + 1][undo_box_x] = 2; // 뼈다귀 먼저 맵에서 움직여주고
			for (int i = 0; i < goal_cnt; i++) { // 뼈다귀 좌표 바꿔주고
				if (BoxX[i] == undo_box_x && BoxY[i] == undo_box_y) {
					BoxY[i] = undo_box_y + 1;
				}
			}
			move(40); // 캐릭터 움직이기
			break;
		case 2:
			move(38); // 캐릭터만 위로 움직이기
			break;
		case 21: // 캐릭터와 뼈다귀 모두 위로
			mapArray[undo_box_y][undo_box_x] = 0;
			mapArray[undo_box_y - 1][undo_box_x] = 2; // 뼈다귀 먼저 움직이고
			for (int i = 0; i < goal_cnt; i++) { // 뼈다귀 좌표 바꿔주고
				if (BoxX[i] == undo_box_x && BoxY[i] == undo_box_y) {
					BoxY[i] = undo_box_y - 1;
				}
			}
			move(38); // 캐릭터 움직이기
			break;
		case 3: // 캐릭터만 오른쪽으로 움직이기
			move(39);
			break;
		case 31: // 캐릭터와 뼈다귀 모두 오른쪽으로
			mapArray[undo_box_y][undo_box_x] = 0;
			mapArray[undo_box_y][undo_box_x + 1] = 2; // 뼈다귀 먼저 움직여주고
			for (int i = 0; i < goal_cnt; i++) {
				if (BoxX[i] == undo_box_x && BoxY[i] == undo_box_y) {
					BoxX[i] = undo_box_x + 1;
				}
			}
			move(39); // 캐릭터 움직이기
			break;
		case 4:
			move(37); // 캐릭터만 왼쪽으로 움직이기
			break;
		case 41: // 캐릭터와 뼈다귀 움직이기
			mapArray[undo_box_y][undo_box_x] = 0;
			mapArray[undo_box_y][undo_box_x - 1] = 2; // 뼈다귀 먼저 움직여주고
			for (int i = 0; i < goal_cnt; i++) {
				if (BoxX[i] == undo_box_x && BoxY[i] == undo_box_y) {
					BoxX[i] = undo_box_x - 1;
				}
			}
			move(37); // 캐릭터 움직이기
			break;
		}
		nUndo = 0; // 다시 못 바꾸게 하기
	} // undo

	public void view(int key) { // 화면에 보이게 하기
		int flag = 0;
		switch (key) { // 누른 방향키에 따라 캐릭터 방향바꾸기
		case 38: // 위
			lblCharacter.setIcon(dog_back);
			break;
		case 40: // 아래
			lblCharacter.setIcon(dog_front);
			break;
		case 37: // 오른쪽
			lblCharacter.setIcon(dog_left);
			break;
		case 39: // 왼쪽
			lblCharacter.setIcon(dog_right);
			break;
		}

		lblCharacter.setBounds(player_x * 50, player_y * 50, 50, 50); // 옮겨진 캐릭터 그리기

		for (int i = 0; i < goal_cnt; i++) { // 밥그릇 그리기
			if (mapArray[GoalY[i]][GoalX[i]] == 2) { // 뼈다귀가 있다면(좌표가 겹친다면) 꽉찬 밥그릇
				lblBowl[i].setIcon(fullbowl);
			} else {
				lblBowl[i].setIcon(bowl); // 뼈다귀가 있다면 빈 밥그릇
			}
		}

		for (int i = 0; i < goal_cnt; i++) { // 뼈다귀 그리기
			flag = 0;
			for (int j = 0; j < goal_cnt; j++) {
				if (BoxX[i] == GoalX[j] && BoxY[i] == GoalY[j]) { // 밥그릇에 있다면 뼈다귀 없애기
					flag = 1;
					lblBox[i].setVisible(false);
				}
			}
			lblBox[i].setBounds(BoxX[i] * 50, BoxY[i] * 50, 50, 50); // 움직인 뼈다귀 그리기
			if (flag == 0)
				lblBox[i].setVisible(true); // 아니면 그냥 보이게 하기
		}

	} // view()

	public boolean isGameClear() { // 라운드 클리어 했는지 반환
		int Goal_Count = 0; // 꽉찬 밥그릇 갯수

		for (int i = 0; i < goal_cnt; i++) {
			if (mapArray[GoalY[i]][GoalX[i]] == 2) {
				Goal_Count++; // 목표지점에 상자가 들어가면 들어감표시.
			}
		}

		if (Goal_Count == goal_cnt) { // 밥그릇이 모두 꽉차있는지 여부 반환
			return true;
		} else
			return false;
	}

	public boolean isGameOver() { // 움직일 수 없는 상황에 도달했는지(박스가 ㄱ자 벽에 붙으면 게임 오버)

		boolean OverFlag = false;

		for (int i = 0; i < this.cnt; i++) { // 각 단계의 상자의 개수만큼 확인!!
			if (mapArray[BoxY[i] - 1][BoxX[i]] == 1) { // 상자 위가 벽
				if (mapArray[BoxY[i]][BoxX[i] + 1] == 1) { // 게임오버 조건에 충족되면.
					OverFlag = true; // 오버 미수입니다...
					for (int j = 0; j < this.cnt; j++) { // 그게 골인지점에 들어와있는지 확인합니다!
						if (BoxX[i] == GoalX[j] && BoxY[i] == GoalY[j]) // 만약 들어와있으면
							OverFlag = false; // 용의선상에서 제외!!
					}

					if (OverFlag) { // 게임오버가 확정되면!
						this.isGameOver = true; // 맞으면 트루!
						break; // 인연을 끊어버리기~!
					}
				} else if (mapArray[BoxY[i]][BoxX[i] - 1] == 1) { // 왼쪽도 확인
					OverFlag = true; // 오버 미수입니다...
					for (int j = 0; j < this.cnt; j++) { // 그게 골인지점에 들어와있는지 확인합니다!
						if (BoxX[i] == GoalX[j] && BoxY[i] == GoalY[j]) // 만약 들어와있으면
							OverFlag = false; // 용의선상에서 제외!!
					}

					if (OverFlag) { // 게임오버가 확정되면!
						this.isGameOver = true; // 맞으면 트루!
						break; // 인연을 끊어버리기~!

					}
				}
			} // if(상자 위쪽 확인)

			else if (mapArray[BoxY[i]][BoxX[i] + 1] == 1) { // 오른쪽 확인
				if (mapArray[BoxY[i] - 1][BoxX[i]] == 1) { // 위쪽도 확인
					OverFlag = true; // 오버 미수입니다...
					for (int j = 0; j < this.cnt; j++) { // 그게 골인지점에 들어와있는지 확인합니다!
						if (BoxX[i] == GoalX[j] && BoxY[i] == GoalY[j]) // 만약 들어와있으면
							OverFlag = false; // 용의선상에서 제외!!
					}

					if (OverFlag) { // 게임오버가 확정되면!
						this.isGameOver = true; // 맞으면 트루!
						break; // 인연을 끊어버리기~!
					}
				}

				else if (mapArray[BoxY[i] + 1][BoxX[i]] == 1) { // 아랫쪽도 확인
					OverFlag = true; // 오버 미수입니다...
					for (int j = 0; j < this.cnt; j++) { // 그게 골인지점에 들어와있는지 확인합니다!
						if (BoxX[i] == GoalX[j] && BoxY[i] == GoalY[j]) // 만약 들어와있으면
							OverFlag = false; // 용의선상에서 제외!!
					}

					if (OverFlag) { // 게임오버가 확정되면!
						this.isGameOver = true; // 맞으면 트루!
						break; // 인연을 끊어버리기~!
					}
				}
			} // if( 상자 오른쪽 확인 )

			else if (mapArray[BoxY[i] + 1][BoxX[i]] == 1) { // 아랫쪽 확인
				if (mapArray[BoxY[i]][BoxX[i] + 1] == 1) { // 오른쪽 확인
					OverFlag = true; // 오버 미수입니다...
					for (int j = 0; j < this.cnt; j++) { // 그게 골인지점에 들어와있는지 확인합니다!
						if (BoxX[i] == GoalX[j] && BoxY[i] == GoalY[j]) // 만약 들어와있으면
							OverFlag = false; // 용의선상에서 제외!!
					}

					if (OverFlag) { // 게임오버가 확정되면!
						this.isGameOver = true; // 맞으면 트루!
						break; // 인연을 끊어버리기~!
					}
				} else if (mapArray[BoxY[i]][BoxX[i] - 1] == 1) { // 왼쪽도 확인
					OverFlag = true; // 오버 미수입니다...
					for (int j = 0; j < this.cnt; j++) { // 그게 골인지점에 들어와있는지 확인합니다!
						if (BoxX[i] == GoalX[j] && BoxY[i] == GoalY[j]) // 만약 들어와있으면
							OverFlag = false; // 용의선상에서 제외!!
					}

					if (OverFlag) { // 게임오버가 확정되면!
						this.isGameOver = true; // 맞으면 트루!
						break; // 인연을 끊어버리기~!
					}
				}
			} // if( 상자 아랫쪽! )

			else if (mapArray[BoxY[i]][BoxX[i] - 1] == 1) { // 왼쪽 확인
				if (mapArray[BoxY[i] + 1][BoxX[i]] == 1) { // 아랫쪽 확인
					OverFlag = true; // 오버 미수입니다...
					for (int j = 0; j < this.cnt; j++) { // 그게 골인지점에 들어와있는지 확인합니다!
						if (BoxX[i] == GoalX[j] && BoxY[i] == GoalY[j]) // 만약 들어와있으면
							OverFlag = false; // 용의선상에서 제외!!
					}

					if (OverFlag) { // 게임오버가 확정되면!
						this.isGameOver = true; // 맞으면 트루!
						break; // 인연을 끊어버리기~!
					}
				} else if (mapArray[BoxY[i] - 1][BoxX[i]] == 1) { // 위쪽도 확인
					OverFlag = true; // 오버 미수입니다...
					for (int j = 0; j < this.cnt; j++) { // 그게 골인지점에 들어와있는지 확인합니다!
						if (BoxX[i] == GoalX[j] && BoxY[i] == GoalY[j]) // 만약 들어와있으면
							OverFlag = false; // 용의선상에서 제외!!
					}

					if (OverFlag) { // 게임오버가 확정되면!
						this.isGameOver = true; // 맞으면 트루!
						break; // 인연을 끊어버리기~!
					}
				}
			} // if( 상자 왼쪽! )

		} // for(i)

		return this.isGameOver;
	}

	public boolean getIsMovable() {
		return isMovable;
	} // 움직였는지 반환

	public void setIsMovable(boolean init) {
		isMovable = init;
	} // 움직임 여부 초기화
}