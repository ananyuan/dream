package com.dream.service.wf;

import java.util.List;

import com.dream.base.Page;
import com.dream.model.wf.Node;

public interface NodeService {
	public int insert(Node node);

	public int update(Node node);

	public int delete(String id);
	
	public List<Node> findNodes(Page<?> page);

	public Node findNode(String id);
}
