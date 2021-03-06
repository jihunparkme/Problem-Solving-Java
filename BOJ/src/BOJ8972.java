import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ8972 {

	static int R, C, IR, IC;
	static int[] dr = { 0, 1, 1, 1, 0, 0, 0, -1, -1, -1 }, dc = { 0, -1, 0, 1, -1, 0, 1, -1, 0, 1 };
	static int map[][][];
	static Queue<Robot> q;

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		map = new int[2][R][C];
		q = new LinkedList<>();

		for (int r = 0; r < R; r++) {
			String input = br.readLine();
			for (int c = 0; c < C; c++) {
				char now = input.charAt(c);
				// 종수의 위치
				if (now == 'I') {
					IR = r;
					IC = c;
				} // 이상한 아두이노의 위치
				else if (now == 'R') {
					map[0][r][c] = 1;
					q.add(new Robot(r, c));
				}
			}
		}
		
		String command = br.readLine();
		
		int res = process(command);
		// 중간에 게임이 끝나는 경우
		if (res > 0) {
			System.out.println("kraj " + res);
		} else {
			res *= -1;
			map[res][IR][IC] = -1;
			for (int r = 0; r < R; r++) {
				for (int c = 0; c < C; c++) {
					if(map[res][r][c] == 0) System.out.print('.');
					else if(map[res][r][c] == -1) System.out.print('I');
					else if(map[res][r][c] == 1) System.out.print('R');
				}
				System.out.println();
			}
		}
	}

	private static int process(String command) {
		
		
		int idx = 0;
		for (int i = 0; i < command.length(); i++) {
			// 이전 턴에서의 map 상태를 복사
			copy(idx);
			
			// 1. 종두이노가 이동
			int com = command.charAt(i) - '0';
			IR += dr[com];
			IC += dc[com];

			// 2. 종두이노가 이상한 아두이노가 있는 칸으로 이동한 경우 게임 종료
			if (map[idx][IR][IC] == 1) return i + 1;

			// 3. 이상한 아두이노는 8가지 방향 중에서 종수의 아두이노와 가장 가까워지는 방향으로 한 칸 이동한다.  
			int size = q.size();
			Robot mLoc = new Robot(0, 0);
			while (size-- > 0) {
				
				Robot now = q.poll();
				// 아두이노끼리 폭발이 일어난 자리라면 pass
				if(map[idx][now.r][now.c] == 0) continue;
				
				int mDist = Integer.MAX_VALUE;
				for (int d = 1; d <= 9; d++) {
					int rr = now.r + dr[d];
					int cc = now.c + dc[d];
					// 범위
					if (rr < 0 || cc < 0 || rr >= R || cc >= C) continue;
					// |r1-r2| + |s1-s2|가 가장 작아지는 방향으로 이동
					int dist = Math.abs(IR - rr) + Math.abs(IC - cc);
					if (mDist > dist) {						
						mDist = dist;
						mLoc = new Robot(rr, cc);
					}
				}
				// 이동할 좌표
				q.add(mLoc);
				map[idx^1][mLoc.r][mLoc.c]++;
				
				map[idx^1][now.r][now.c]--;
				
				// 4. 이상한 아두이노가 종두이노가 있는 칸으로 이동한 경우에는 게임 종료
				if (mLoc.r == IR && mLoc.c == IC) return i + 1;
			}

			// 5. 같은 칸에 있는 이상한 아두이노는 모두 파괴
			for (int r = 0; r < R; r++) {
				for (int c = 0; c < C; c++) {
					if(map[idx^1][r][c] > 1) map[idx^1][r][c] = 0;
				}
			}
			// 새로운 map을 Main map으로 전환
			idx ^= 1;
		}
		
		return idx * -1;
	}

	private static void copy(int idx) {
		
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				map[idx^1][i][j] = map[idx][i][j];
			}
		}
		
	}

	static class Robot {
		int r, c;

		public Robot(int r, int c) {
			this.r = r;
			this.c = c;
		}

	}

}