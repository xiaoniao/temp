package com.kunyao.assistant.core.utils;

import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

public class BreadthFirstSearch {
	private boolean[] marked;
	private int[] edgeTo;
	private int sourceVetical;// Source vertical

	public BreadthFirstSearch(Graph g, int s) {
		marked = new boolean[g.GetVerticals()];
		edgeTo = new int[g.GetVerticals()];
		this.sourceVetical = s;
		bfs(g, s);
	}

	private void bfs(Graph g, int s) {
		Queue<Integer> queue = new LinkedBlockingQueue<>();
		marked[s] = true;
		queue.add(s);
		while (queue.element() != 0) {
			int v = queue.poll();
			for (int w : g.GetAdjacency(v)) {
				if (!marked[w]) {
					edgeTo[w] = v;
					marked[w] = true;
					queue.add(w);
				}
			}
		}
	}

	public boolean HasPathTo(int v) {
		return marked[v];
	}

	public Stack<Integer> PathTo(Integer v) {
		if (!HasPathTo(v))
			return null;

		Stack<Integer> path = new Stack<Integer>();
		for (int x = v; x != sourceVetical; x = edgeTo[x]) {
			path.push(x);
		}
		path.push(sourceVetical);
		return path;
	}
}
