package coursework;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MainPanel extends JPanel implements Runnable {
	/* KEY FOR BOARD USE
	 * POSITIVE = WHITES, NEGATIVE = BLACKS
	 *
	 * 1 = PAWN, 2 = ROOK, 3 = BISHOP, 4 = KNIGHT, 5 = QUEEN, 6 = KING 
	 * USE [COLUMN] [ROW] */
	private int[][] board = new int[8][8];
	
	private ImageIcon boardImg;
	private ImageIcon wPawn, wRook, wBishop, wKnight, wQueen, wKing;
	private ImageIcon bPawn, bRook, bBishop, bKnight, bQueen, bKing;
	
	private Thread game;

	public MainPanel() {
		boardImg = new ImageIcon(getClass().getResource("/coursework/gfx/board.png"));
		wPawn = new ImageIcon(getClass().getResource("/coursework/gfx/white_pawn.png"));
		wRook = new ImageIcon(getClass().getResource("/coursework/gfx/white_rook.png"));
		wBishop = new ImageIcon(getClass().getResource("/coursework/gfx/white_bishop.png"));
		wKnight = new ImageIcon(getClass().getResource("/coursework/gfx/white_knight.png"));
		wQueen = new ImageIcon(getClass().getResource("/coursework/gfx/white_queen.png"));
		wKing = new ImageIcon(getClass().getResource("/coursework/gfx/white_king.png"));
		bPawn = new ImageIcon(getClass().getResource("/coursework/gfx/black_pawn.png"));
		bRook = new ImageIcon(getClass().getResource("/coursework/gfx/black_rook.png"));
		bBishop = new ImageIcon(getClass().getResource("/coursework/gfx/black_bishop.png"));
		bKnight = new ImageIcon(getClass().getResource("/coursework/gfx/black_knight.png"));
		bQueen = new ImageIcon(getClass().getResource("/coursework/gfx/black_queen.png"));
		bKing = new ImageIcon(getClass().getResource("/coursework/gfx/black_king.png"));
		initBoard();
		repaint();
		setFocusable(true);
		setVisible(true);
		game = new Thread(this);
		game.start();
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.GREEN);
		g.drawImage(boardImg.getImage(), 0, 0, 510, 510, null);
		for(int col = 0; col <=7; col++) {
			for(int row = 0; row <=7; row++) {
				if(board[col][row] != 0) {
					ImageIcon i = wPawn;
					switch(board[col][row]) {
					//whites
					case 1:
						i = wPawn;
						break;
					case 2:
						i = wRook;
						break;
					case 3:
						i = wBishop;
						break;
					case 4:
						i = wKnight;
						break;
					case 5:
						i = wQueen;
						break;
					case 6:
						i = wKing;
						break;
					//blacks
					case -1:
						i = bPawn;
						break;
					case -2:
						i = bRook;
						break;
					case -3:
						i = bBishop;
						break;
					case -4:
						i = bKnight;
						break;
					case -5:
						i = bQueen;
						break;
					case -6:
						i = bKing;
						break;
					}
					g.drawImage(i.getImage(), 80 + 44*col, 386 - 44*row, 44, 44, null);
				}
			}
		}
	}

	@Override
	public void run() {
		repaint();
		try {
			game.sleep(40);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private ArrayList<String> getPossibleMoves(int[][] b, int col, int row) {
		ArrayList<String> possibleMoves = new ArrayList<String>();
		int playerSign = getSign(b[col][row]);
		// PAWN
		if (Math.abs(b[col][row]) == 1) {
			int a = row + b[col][row];
			if (a >= 0 && a < 8) {
				if (b[col][a] == 0)
					possibleMoves.add(col + "," + a);
				if (col - 1 >= 0)
					if (b[col - 1][a] != 0 && getSign(b[col - 1][a]) != playerSign)
						possibleMoves.add(col + "," + a);
				if (col + 1 < 8)
					if (b[col - 1][a] != 0 && getSign(b[col + 1][a]) != playerSign)
						possibleMoves.add(col + "," + a);
			}
		}
		// ROOK & QUEEN
		if (Math.abs(b[col][row]) == 2 || Math.abs(b[col][row]) == 5) {
			for (int r = row + 1; r < 8; r++) {
				if (b[col][r] == 0) {
					possibleMoves.add(col + "," + r);
				} else if (getSign(b[col][r]) == -playerSign) {
					possibleMoves.add(col + "," + r);
					break;
				} else {
					break;
				}
			}
			for (int r = row - 1; r >= 0; r--) {
				if (b[col][r] == 0) {
					possibleMoves.add(col + "," + r);
				} else if (getSign(b[col][r]) == -playerSign) {
					possibleMoves.add(col + "," + r);
					break;
				} else {
					break;
				}
			}
			for (int c = col + 1; c < 8; c++) {
				if (b[c][row] == 0) {
					possibleMoves.add(c + "," + row);
				} else if (getSign(b[c][row]) == -playerSign) {
					possibleMoves.add(c + "," + row);
					break;
				} else {
					break;
				}
			}
			for (int c = col - 1; c >= 0; c--) {
				if (b[c][row] == 0) {
					possibleMoves.add(c + "," + row);
				} else if (getSign(b[c][row]) == -playerSign) {
					possibleMoves.add(c + "," + row);
					break;
				} else {
					break;
				}
			}
		}
		// BISHOP & QUEEN
		if (Math.abs(b[col][row]) == 3 || Math.abs(b[col][row]) == 5) {
			// up right
			for (int m = 1; m < 8; m++) {
				if (col + m > 7 || row + m > 7) {
					break;
				} else {
					if (b[col + m][row + m] == 0) {
						possibleMoves.add((col + m) + "," + (row + m));
					} else if (getSign(b[col + m][row + m]) == -playerSign) {
						possibleMoves.add((col + m) + "," + (row + m));
						break;
					} else {
						break;
					}
				}
			}
			// up left
			for (int m = 1; m < 8; m++) {
				if (col - m < 0 || row + m > 7) {
					break;
				} else {
					if (b[col - m][row + m] == 0) {
						possibleMoves.add((col - m) + "," + (row + m));
					} else if (getSign(b[col - m][row + m]) == -playerSign) {
						possibleMoves.add((col - m) + "," + (row + m));
						break;
					} else {
						break;
					}
				}
			}
			// down right
			for (int m = 1; m < 8; m++) {
				if (col + m > 7 || row - m < 0) {
					break;
				} else {
					if (b[col + m][row - m] == 0) {
						possibleMoves.add((col + m) + "," + (row - m));
					} else if (getSign(b[col + m][row - m]) == -playerSign) {
						possibleMoves.add((col + m) + "," + (row - m));
						break;
					} else {
						break;
					}
				}
			}
			// down left
			for (int m = 1; m < 8; m++) {
				if (col - m < 0 || row - m < 0) {
					break;
				} else {
					if (b[col - m][row - m] == 0) {
						possibleMoves.add((col - m) + "," + (row - m));
					} else if (getSign(b[col - m][row - m]) == -playerSign) {
						possibleMoves.add((col - m) + "," + (row - m));
						break;
					} else {
						break;
					}
				}
			}
		}
		// KNIGHT
		if (Math.abs(b[col][row]) == 4) {
			if (row + 2 < 8) {
				if (col - 1 >= 0) {
					if (b[col - 1][row + 2] == 0 || getSign(b[col - 1][row + 2]) != playerSign) {
						possibleMoves.add((col - 1) + "," + (row + 2));
					}
				}
				if (col + 1 < 8) {
					if (b[col + 1][row + 2] == 0 || getSign(b[col + 1][row + 2]) != playerSign) {
						possibleMoves.add((col + 1) + "," + (row + 2));
					}
				}
			}
			if (row + 1 < 8) {
				if (col - 2 >= 0) {
					if (b[col - 2][row + 1] == 0 || getSign(b[col - 2][row + 1]) != playerSign) {
						possibleMoves.add((col - 2) + "," + (row + 1));
					}
				}
				if (col + 2 < 8) {
					if (b[col + 2][row + 1] == 0 || getSign(b[col + 2][row + 1]) != playerSign) {
						possibleMoves.add((col + 2) + "," + (row + 1));
					}
				}
			}
			if (row - 2 >= 0) {
				if (col - 1 >= 0) {
					if (b[col - 1][row - 2] == 0 || getSign(b[col - 1][row - 2]) != playerSign) {
						possibleMoves.add((col - 1) + "," + (row - 2));
					}
				}
				if (col + 1 < 8) {
					if (b[col + 1][row - 2] == 0 || getSign(b[col + 1][row - 2]) != playerSign) {
						possibleMoves.add((col + 1) + "," + (row - 2));
					}
				}
			}
			if (row - 1 >= 0) {
				if (col - 2 >= 0) {
					if (b[col - 2][row - 1] == 0 || getSign(b[col - 2][row - 1]) != playerSign) {
						possibleMoves.add((col - 2) + "," + (row - 1));
					}
				}
				if (col + 2 < 8) {
					if (b[col + 2][row - 1] == 0 || getSign(b[col + 2][row - 1]) != playerSign) {
						possibleMoves.add((col + 2) + "," + (row - 1));
					}
				}
			}
		}
		// KING
		if (Math.abs(b[col][row]) == 6) {
			boolean up = row + 1 < 8;
			boolean down = row - 1 >= 0;
			boolean left = col - 1 >= 0;
			boolean right = col + 1 < 8;
			if (up) {
				if (b[col][row + 1] == 0 || getSign(b[col][row + 1]) != playerSign) {
					possibleMoves.add((col) + "," + (row + 1));
				}
				if (left) {
					if (b[col - 1][row + 1] == 0 || getSign(b[col - 1][row + 1]) != playerSign) {
						possibleMoves.add((col - 1) + "," + (row + 1));
					}
				}
				if (right) {
					if (b[col + 1][row + 1] == 0 || getSign(b[col + 1][row + 1]) != playerSign) {
						possibleMoves.add((col + 1) + "," + (row + 1));
					}
				}
			}
			if (left) {
				if (b[col - 1][row] == 0 || getSign(b[col - 1][row]) != playerSign) {
					possibleMoves.add((col - 1) + "," + (row + 1));
				}
			}
			if (right) {
				if (b[col + 1][row] == 0 || getSign(b[col + 1][row]) != playerSign) {
					possibleMoves.add((col + 1) + "," + (row + 1));
				}
			}
			if (down) {
				if (b[col][row - 1] == 0 || getSign(b[col][row - 1]) != playerSign) {
					possibleMoves.add((col) + "," + (row - 1));
				}
				if (left) {
					if (b[col - 1][row - 1] == 0 || getSign(b[col - 1][row - 1]) != playerSign) {
						possibleMoves.add((col - 1) + "," + (row - 1));
					}
				}
				if (right) {
					if (b[col + 1][row - 1] == 0 || getSign(b[col + 1][row - 1]) != playerSign) {
						possibleMoves.add((col + 1) + "," + (row - 1));
					}
				}
			}
		}
		for (String s : possibleMoves) {
			int tBoard[][] = b;
			tBoard[Integer.parseInt(s.split(",")[0])][Integer.parseInt(s.split(",")[1])] = tBoard[col][row];
			tBoard[col][row] = 0;
			if (isInCheck(tBoard, playerSign)) {
				possibleMoves.remove(s);
			}
		}
		return possibleMoves;
	}

	public boolean isInCheck(int[][] b, int kingSign) {
		int kingC = 0, kingR = 0;
		for (int c = 0; c < 8; c++) {
			for (int r = 0; r < 8; r++) {
				if (b[c][r] == 6 * kingSign) {
					kingC = c;
					kingR = r;
				}
			}
		}
		for (int c = 0; c < 8; c++) {
			for (int r = 0; r < 8; r++) {
				if (b[c][r] != 0 && getSign(b[c][r]) == -1 * kingSign) {
					if (getPossibleMoves(b, c, r).contains(kingC + "," + kingR)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private int getSign(int a) {
		return a / Math.abs(a);
	}

	
	private void initBoard() {
		//create pawns
		for(int i = 0; i<=7; i++) {
			board[i][1] = 1; //white
			board[i][6] = -1; //black
		}
		//create rooks
		board[0][0] = 2; //white
		board[7][0] = 2;
		board[0][7] = -2; //black
		board[7][7] = -2;
		//create bishops
		board[2][0] = 3; //white
		board[5][0] = 3;
		board[2][7] = -3; //black
		board[5][7] = -3;
		//create knights
		board[1][0] = 4; //white
		board[6][0] = 4;
		board[1][7] = -4; //black
		board[6][7] = -4;
		//create queens
		board[3][0] = 5; //white
		board[4][7] = -5; //black
		//create kings
		board[4][0] = 6; //white
		board[3][7] = -6; //black
	}
}