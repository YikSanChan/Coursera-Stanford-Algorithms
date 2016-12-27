package part1.week4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by CYS on 2016/12/26.
 */
public class KargerMinCut {

    // adjacency list
    private Map<Integer, List<Integer>> adjList, clonedAdjList;
    // vertices
    private List<Integer> vertices, clonedVertices;
    private int minCut = Integer.MAX_VALUE;
    private Random rand = new Random();

    private void readData(String filename)
    {
        adjList = new HashMap<>();
        vertices = new ArrayList<>();
        try (Scanner input = new Scanner(new File(filename))) {
            while (input.hasNextLine())
            {
                // test data delimited by multiple spaces, exam data by \t.
                String[] nums = input.nextLine().split("[ +\\t]");
                List<Integer> adjVertices = new ArrayList<>();
                for (int i = 1; i < nums.length; i++)
                    adjVertices.add(Integer.parseInt(nums[i]));
                adjList.put(Integer.parseInt(nums[0]), adjVertices);
                vertices.add(Integer.parseInt(nums[0]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // deep copy of original adjList and vertices
        clonedAdjList = new HashMap<>();
        for (Map.Entry<Integer, List<Integer>> entry: adjList.entrySet())
        {
            Integer tmpVertex = new Integer(entry.getKey());
            List<Integer> tmpVertices = new ArrayList<>();
            tmpVertices.addAll(entry.getValue());
            clonedAdjList.put(tmpVertex, tmpVertices);
        }
        clonedVertices = new ArrayList<>();
        clonedVertices.addAll(vertices);
    }

    private void karger()
    {
        while (vertices.size() > 2)
        {
            // random select vertex u and v
            int uid = vertices.get(rand.nextInt(vertices.size()));
            List<Integer> uAdj = adjList.get(uid);
            int vid = uAdj.get(rand.nextInt(uAdj.size()));
            List<Integer> vAdj = adjList.get(vid);

            // iterate through v's linked vertices w
            for (int wid : vAdj)
            {
                List<Integer> wAdj = adjList.get(wid);
                // remove ALL, use while instead of if
                while (wAdj.remove(new Integer(vid)));
                wAdj.add(uid);
                uAdj.add(wid);
            }
            adjList.remove(vid);
            while (vertices.remove(new Integer(vid)));
            while (uAdj.remove(new Integer(uid)));
        }
        minCut = Math.min(minCut, adjList.get(vertices.get(0)).size());

        // reset adjList and vertices
        adjList.clear();
        vertices.clear();

        for (Map.Entry<Integer, List<Integer>> entry: clonedAdjList.entrySet())
        {
            Integer tmpVertex = new Integer(entry.getKey());
            List<Integer> tmpVertices = new ArrayList<>();
            tmpVertices.addAll(entry.getValue());
            adjList.put(tmpVertex, tmpVertices);
        }
        vertices.addAll(clonedVertices);
    }

    public static void main(String[] args)
    {
        KargerMinCut kmc = new KargerMinCut();
        kmc.readData("resource/KargerMinCut.txt");
        for (int i = 0; i < kmc.vertices.size() * kmc.vertices.size(); i++)
        {
            System.out.println("********************************");
            System.out.println("Iteration " + String.valueOf(i + 1));
            kmc.karger();
            System.out.println("Min Cut: " + kmc.minCut);
        }
        System.out.println(kmc.minCut);
    }

}
