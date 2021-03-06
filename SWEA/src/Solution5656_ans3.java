import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;

public class Solution5656_ans3 {

	static int N, W, H, res;
	private static int[] dr = { -1, 1, 0, 0 }, dc = { 0, 0, -1, 1 };
	static class Point{
		int r, c, cnt;

		public Point(int r, int c, int cnt) {
			this.r = r;
			this.c = c;
			this.cnt = cnt;
		}
		
	}

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {

			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken()); // 쏠 수 있는 구슬 개수
			W = Integer.parseInt(st.nextToken());
			H = Integer.parseInt(st.nextToken());
			
			int[][] map = new int[H][W];
			for (int r = 0; r < H; r++) {
				st = new StringTokenizer(br.readLine());
				for (int c = 0; c < W; c++) {
					map[r][c] = Integer.parseInt(st.nextToken());
				}
			}

			res = Integer.MAX_VALUE;
			go(0, map);
			
			System.out.println("#" + tc + " " + res);
		}

	}

	/**
	 * i번째 구슬을 떨어뜨리기
	 * 
	 * @param cnt 던져진 구슬의 개수
	 * @param map 이전 구슬까지의 2차원 배열
	 */
	private static boolean go(int cnt, int[][] map) {

		int result = getRemain(map);
		if(result == 0) {
			res = 0;
			return true;
		}
		
		// 구슬을 모두 던졌을 경우
		if(cnt == N) {
			// 남아있는 벽돌의 개수를 구하여 최솟값 갱신	
			res = Math.min(res, result);
			return false;
		}
		
		int[][] newMap = new int[H][W];
		// 모든 열에 구슬을 떨어뜨려보자.
		for (int c = 0; c < W; c++) {

			int r = 0;
			// 가장 위에 있는 벽돌 위치 찾기 (범위 안에 있고 빈 공간이면)
			while(r < H && map[r][c] == 0) ++r;

			// 벽돌이 없을 경우
			if(r == H) continue;
			
			// 벽돌이 있을 경우
			// 이전 구슬 상태로 배열에 복사하여 초기화
			copy(map, newMap);
			// 벽돌 터뜨리기
			boom(newMap, r, c);
			// 벽돌 내리기
			down(newMap);
			// 다음 구슬 처리
			if(go(cnt + 1, newMap)) return true;
		}
		
		return false;
	}
	
	static ArrayList<Integer> list = new ArrayList<>();
	private static void down(int[][] map) {
		
		for (int c = 0; c < W; c++) {
			
			int r;
			for (r = H - 1; r >= 0; r--) {
				// 벽돌이라면 list에 추가 후 빈 공간으로
				if(map[r][c] > 0) {
					list.add(map[r][c]);
					map[r][c] = 0;
				}
			}
			r = H;
			for (int b : list) {
				map[--r][c] = b;
			}
			list.clear();
		}
		
	}

	private static void boom(int[][] map, int r, int c) {
		
		Queue<Point> q = new LinkedBlockingDeque<>();
		// 크기가 1보다 클 경우만 queue에 추가
		if(map[r][c] > 1) q.add(new Point(r, c, map[r][c]));			
		map[r][c] = 0; // 벽돌 제거 처리(방문처리)
		
		while(!q.isEmpty()) {
			
			Point now = q.poll();
			
			if(now.cnt == 1) continue;
			
			for (int d = 0; d < 4; d++) {
				int nr = now.r, nc = now.c;
				
				for (int k = 1; k < now.cnt; k++) {
					nr += dr[d];
					nc += dc[d];
					
					if(nr >= 0 && nr < H && nc >= 0 && nc < W && map[nr][nc] != 0) {
						if(map[nr][nc] > 1) q.add(new Point(nr, nc, map[nr][nc]));
						map[nr][nc] = 0;
					}
				}
			}
		}
	}

	private static void copy(int[][] map, int[][] newMap) {
			
		for (int i = 0; i < H; i++) {
			for (int j = 0; j < W; j++) {
				newMap[i][j] = map[i][j];
			}
		}
		
	}

	private static int getRemain(int[][] map) {
		
		int cnt = 0;
		
		for (int i = 0; i < H; i++) {
			for (int j = 0; j < W; j++) {
				if(map[i][j] > 0) cnt++;
			}
		}
		
		return cnt;
	}

}