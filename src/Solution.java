import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

class Solution {
    int[] sol;

    Queue<Integer> q = new LinkedList<>();
    boolean[] marked;

    public int[] gardenNoAdj(int N, int[][] paths) {
        int[][] adj = new int[N+1][4];
        if(paths.length == 0)
        {
            int[] a = new int[N];
            Arrays.fill(a,1);
            return a;
        }

        for(int j=1;j<=N;j++)
        {
            int c = 0;
            for(int i =0;i<paths.length;i++)
            {
                if(paths[i][0] == j)
                    adj[j][++c] = paths[i][1];
                else if(paths[i][1] == j)
                    adj[j][++c] = paths[i][0];
            }
        }
        return bfs(adj,N,1);

    }
    public int[] bfs(int[][]adj,int N,int x)
    { int c =0;
        if(x == 1){
            sol = new int[N];
            Arrays.fill(sol,0);

            marked = new boolean[N+1];
            Arrays.fill(marked, false);
            q.offer(adj[1][1]);
        }
        if(x > N)
            c = 1;
        else
            q.offer(adj[x][1]);

        while(!q.isEmpty())
        {
            int v = q.poll();
            marked[v] = true;
            if(v == 0)
            {
                c=1;
                break;
            }

            sol[v-1] = getCol(v,sol,N,adj);
            int[] g = getAdj(v,adj,N);
            if(g[1] == 0) {
                sol[v] = 1;
                continue;
            }
            for(int w : getAdj(v,adj,N))
            {

                if(w == 0)
                    continue;

                if(!marked[w])
                {
                    q.offer(w);
                    marked[w] = true;
                    sol[w-1] = getCol(w,sol,N,adj);
                }

            }
        }
        if(c == 1)
        {
            for(int i=0;i<N;i++)
            {
                if(sol[i] == 0) {
                    sol[i] =1;
                    bfs(adj,N,i+2);
                }
            }
        }
        else {
            for (int i = 0; i < N; i++) {
                if (sol[i] == 0)
                    bfs(adj, N, i + 1);
            }
        }
        return sol;

    }
    public int[] getAdj(int v, int[][] adj,int N)
    {
        int[] a = new int[N];
        a = adj[v];
        return a;
    }
    public int getCol(int w,int[] sol,int N,int[][] adj)
    {
        int col = 1;
        boolean[] check = {true,false,false,false,false};
        for(int x : getAdj(w,adj,N))
        {
            if(x == 0)
                continue;
            if(sol[x-1] != 0)
            {
                check[sol[x-1]] = true;
            }


        }
        for(int i =1;i<5;i++)
        {
            if(check[i] == false){
                col = i;
                break;
            }
        }
        return col;
    }
    public static void main(String[] args)
    {
        Solution s = new Solution();
        boolean[] marked = new boolean[2];
        System.out.println(marked[0]);
        System.out.println(marked[1]);
    }
}