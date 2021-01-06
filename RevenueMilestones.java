import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;


/**
 * Revenue Milestones
 * https://www.facebookrecruiting.com/portal/coding_practice_question/?problem_id=192049171861831
 */
public class RevenueMilestones {


    /**
     * Given an array of the revenue on each day, 
     * and an array of milestones Facebook wants to reach, 
     * return an array containing the days on which Facebook reached every milestone.
     */
    static int[] getMilestoneDays(int[] revenues, int[] milestones) {

        // **** initialization ****
        int[] ans = Arrays.stream(new int[milestones.length]).map(i -> -1).toArray();

        int revenue = 0;

        PriorityQueue<String> pq = new PriorityQueue<String>(new Comparator<String>() {
            public int compare(String str1, String str2) {
                String s1 = str1.substring(0, str1.indexOf(","));
                String s2 = str2.substring(0, str2.indexOf(","));
                Integer v1 = Integer.parseInt(s1);
                Integer v2 = Integer.parseInt(s2);
                if (v1 > v2)
                    return 1;
                if (v1 == v2)
                    return 0;
                else
                    return -1; 
            }
        });

        for (int i = 0; i < milestones.length; i++) {
            String s = "" + milestones[i] + "," + i;
            pq.add(s);
        }

        // **** traverse revenue array ****
        for (int r = 0; r < revenues.length; r++) {

            // **** update revenue ****
            revenue += revenues[r];

            // **** update the ans array with milestones that have been reached ****
            while (!pq.isEmpty()) {

                // **** extract milestone and index ****
                String[] strs = pq.peek().split(",");
                int milestone = Integer.parseInt(strs[0]);
                int m = Integer.parseInt(strs[1]);
    
                // **** check if done ****
                if (revenue < milestone)
                    break;

                // **** ****
                ans[m] = (r + 1);
               
                // **** remove lowest value milestone ****
                pq.remove();
            }
        }

        // **** return answer **** 
        return ans;
    }




    /**
     * Given an array of the revenue on each day, 
     * and an array of milestones Facebook wants to reach, 
     * return an array containing the days on which Facebook reached every milestone.
     */
    static int[] getMilestoneDays1(int[] revenues, int[] milestones) {

        // **** initialization ****
        int sum     = 0;
        int[] ans   = Arrays.stream(new int[milestones.length]).map(i -> -1).toArray();

        HashMap<Integer, Integer> idx = new HashMap<>();
        for (int i = 0; i < milestones.length; i++)
            idx.put(milestones[i], i);

        Arrays.sort(milestones);

        // **** traverse all revenues ****
        for (int m = 0, d = 0; d < revenues.length; d++) {

            // **** update the sum with today's revenue ****
            sum += revenues[d];

            // **** start from the current smallest milestone ****
            while ((m < milestones.length) && (sum >= milestones[m])) {

                // **** set day for milestone ****
                ans[idx.get(milestones[m])] = d + 1;

                // **** increment milestone index ****
                m++;
            }

        }

        // **** return answer **** 
        return ans;
    }


    /**
     * Test scaffolding
     * !!! NOT PART OF SOLUTION !!!
     * 
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        
        // **** open buffered reader ****
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // **** read revenue ****
        int[] revenues = Arrays.stream(br.readLine().split(", ")).mapToInt(Integer::parseInt).toArray();
 
        // **** read milestones ****
        int[] milestones = Arrays.stream(br.readLine().split(", ")).mapToInt(Integer::parseInt).toArray();

        // **** close buffered reader ****
        br.close();

        // ???? ????
        System.out.println("main <<<   revenues: " + Arrays.toString(revenues));
        System.out.println("main <<< milestones: " + Arrays.toString(milestones));

        // **** compute and display results ****
        System.out.println("main <<< output: " + Arrays.toString(getMilestoneDays(revenues, milestones)));
        System.out.println("main <<< output: " + Arrays.toString(getMilestoneDays1(revenues, milestones)));
    }

}