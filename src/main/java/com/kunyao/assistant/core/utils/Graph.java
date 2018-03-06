package com.kunyao.assistant.core.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class Graph {

	/**
	 * 0
	 * 1
	 * 2
	 * 3
	 * 4
	 * 5
	 */
	public static void main(String[] args) {
		
		int totalVertical = 6;
		Graph graph = new Graph(totalVertical);
		graph.AddEdge(0, 1);
		graph.AddEdge(0, 2);
		graph.AddEdge(0, 3);
		graph.AddEdge(0, 4);
		graph.AddEdge(5, 3);
		
		for (int i = 0; i < totalVertical; i++) {
			System.out.print(i + ":");
			List<Integer> list = graph.GetAdjacency(i);
			if (list.size() == 0) {
				System.out.print("*");
			} else {
				for (Integer integer : list) {
					System.out.print(integer + " ");
				}
			}
			System.out.println();
		}
		System.out.println("---------");
		
		System.out.println(graph.removePoint(0));
		System.out.println("冲突个数：" + graph.GetEdges());
		
		for (int i = 0; i < totalVertical; i++) {
			System.out.print(i + ":");
			List<Integer> list = graph.GetAdjacency(i);
			if (list .size() == 0) {
				System.out.print("*");
			} else {
				for (Integer integer : list) {
					System.out.print(integer + " ");
				}
			}
			System.out.println();
		}
	}

	private int verticals; // 顶点个数
	private int edges; // 边的个数
	
	/**
	 * 0 : 1,2,5,6
	 * 3 : 4,5
	 * 4 : 5,6
	 * 
	 * 表示一个图的结构
	 */
	private List<Integer>[] adjacency;// 顶点联接列表 记录顶点的连接数 例如

	public Graph(int vertical) {
		this.verticals = vertical;
		this.edges = 0;

		adjacency = new List[vertical];
		for (int v = 0; v < vertical; v++) {
			adjacency[v] = new ArrayList<Integer>(); // 顶点：集合
		}
	}

	// 获得顶点个数
	public int GetVerticals() {
		return verticals;
	}

	// 获得边个数 边的个数代表有冲突的个数
	public int GetEdges() {
		return edges;
	}

	// 添加边
	public void AddEdge(int verticalStart, int verticalEnd) {
		adjacency[verticalStart].add(verticalEnd);
		adjacency[verticalEnd].add(verticalStart);
		edges++;
	}
	
	// 删除边
	public void removeEdge(int verticalStart, int verticalEnd) {
		adjacency[verticalStart].remove(Integer.valueOf(verticalEnd));
		adjacency[verticalEnd].remove(Integer.valueOf(verticalStart));
		edges--;
	}
	
	// 删除顶点
	public Set<Integer> removePoint(int root) {
		Set<Integer> set = new HashSet<>();
		
		// 找到第一集目录
		List<Integer> firstChild = copy(adjacency[root]);
		
		// 找到第一集目录下面的第二级目录
		for (Integer first : firstChild) {
			// 删除和第一级目录的边
			removeEdge(root, first);
			
			set.add(first);
			
			List<Integer> secondChild = copy(adjacency[first]);
			for (Integer second : secondChild) {
				removeEdge(first, second);
			}
		}
		return set;
	}
	
	private List<Integer> copy(List<Integer> list) {
		List<Integer> verticals = new ArrayList<>();
		for (Integer integer : list) {
			verticals.add(integer);
		}
		return verticals;
	}
	
	// 获得有冲突的索引
	public Set<Integer> getUsedPoint() {
		Set<Integer> set = new HashSet<>();
		for (int i = 0; i < adjacency.length; i++) {
			set.addAll(adjacency[i]);
		}
		return set;
	}

	// 获得连接关系
	public List<Integer> GetAdjacency(int vetical) {
		return adjacency[vetical];
	}
}
