import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOj14468 {

	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = br.readLine();
		
		boolean[] check = new boolean[26];
		int[] alpa;
		int cnt = 0;
		for (int d = 0; d < input.length(); d++) {
			
			int now = input.charAt(d) - 'A';
			// 확인한 소 체크
			check[now] = true;
			// 소의 출입 확인
			alpa = new int[26];
			alpa[now]++;
			
			// 나가는 점이 나올 때까지 반복
			for (int nd = d + 1; nd < input.length(); nd++) {
				
				int next = input.charAt(nd) - 'A';
				// 등장하는 소 확인
				alpa[next]++;

				// 나가는 점이 나왔을 때, 아직 나오지 않은 소 확인
				if(next == now) {
					
					for (int i = 0; i < 26; i++) 
						if(!check[i] && alpa[i] == 1) cnt++;
					
					break;
				}
			}
		}		

		System.out.println(cnt);
	}

}
