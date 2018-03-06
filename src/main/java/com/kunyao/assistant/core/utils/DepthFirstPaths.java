package com.kunyao.assistant.core.utils;

import java.util.Stack;

public class DepthFirstPaths {
	
	private boolean[] marked;// 记录是否被dfs访问过
	private int[] edgesTo;// 记录最后一个到当前节点的顶点
	private int s;// 搜索的起始点

	public DepthFirstPaths(Graph g, int s) {
		marked = new boolean[g.GetVerticals()];
		edgesTo = new int[g.GetVerticals()];
		this.s = s;
		dfs(g, s);
	}

	private void dfs(Graph g, int v) {
		marked[v] = true;
		for (int w : g.GetAdjacency(v)) {
			if (!marked[w]) {
				edgesTo[w] = v;
				dfs(g, w);
			}
		}
	}

	public boolean HasPathTo(int v) {
		return marked[v];
	}

	public Stack<Integer> PathTo(int v) {

		if (!HasPathTo(v))
			return null;
		Stack<Integer> path = new Stack<Integer>();

		for (int x = v; x != s; x = edgesTo[x]) {
			path.push(x);
		}
		path.push(s);
		return path;
	}
}
