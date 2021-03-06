import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

// 2차원 visited 배열로는 불가능

public class BOJ1600_v3 {

	static int K, W, H, res = -1, map[][], visited[][];
	static int[] dx = { 1, 0, -1, 0, -1, -2, -2, -1, 1, 2, 1, 2 };
	static int[] dy = { 0, 1, 0, -1, -2, -1, 1, 2, -2, -1, 2, 1 };

	static class State {
		// x좌표, y좌표, 이동 거리, 말처럼 뛴 횟수
		int x,y,dist,jump;

		public State(int x, int y, int dist, int jump) {
			this.x = x;
			this.y = y;
			this.dist = dist;
			this.jump = jump;
		}
		
	}
	
	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		K = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		W = Integer.parseInt(st.nextToken());	// 가로
		H = Integer.parseInt(st.nextToken());	// 세로
		map = new int[H][W];
		visited = new int[H][W];
		
		for (int i = 0; i < H; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < W; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		Queue<State> q = new LinkedList<>();
		visited[0][0] = 0;
		q.add(new State(0, 0, 0, 0));
		while(!q.isEmpty()) {
			State now = q.poll();
			// 도착지에 도달
			if(now.x == H-1 && now.y == W-1) {
				res = now.dist;
				break;
			}
			
			// 말처럼 뛸 수 있는지 확인
			int animal = now.jump >= K ? 4 : 12;
			// 이동해보자
			for (int d = 0; d < animal; d++) {
				int xx = now.x + dx[d];
				int yy = now.y + dy[d];
				
				// 범위 초과 시 pass
				if(xx < 0 || yy < 0 || xx >= H || yy >= W) continue;
				// 장애물이 있을 경우 pass
				if(map[xx][yy] == 1) continue;
				// 원숭이처럼 이동할 경우
				if(d < 4) {
					// 이미 왔던 곳이라면
					if(visited[xx][yy] <= now.jump) continue;
					visited[xx][yy] = now.jump;
					q.add(new State(xx, yy, now.dist + 1, now.jump));
				}
				// 말처럼 이동할 경우
				else {
					// 이미 왔던 곳이라면 pass
					if(visited[xx][yy] <= now.jump + 1) continue;
					visited[xx][yy] = now.jump + 1;
					q.add(new State(xx, yy, now.dist + 1, now.jump + 1));
				}
			}
		}
		
		System.out.println(res);
	}
}
