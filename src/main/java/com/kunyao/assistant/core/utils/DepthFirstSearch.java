package com.kunyao.assistant.core.utils;

public class DepthFirstSearch {
	
	private boolean[] marked;// 记录顶点是否被标记
	private int count;// 记录查找次数

	public DepthFirstSearch(Graph g, int v) {
		marked = new boolean[g.GetVerticals()];
		dfs(g, v);
	}

	private void dfs(Graph g, int v) {
		marked[v] = true;
		count++;
		for (int vertical : g.GetAdjacency(v)) {
			if (!marked[vertical])
				dfs(g, vertical);
		}
	}

	public boolean IsMarked(int vertical) {
		return marked[vertical];
	}

	public int Count() {
		return count;
	}
}
