package com.dream.service.wf;

import java.util.List;

import com.dream.base.Page;
import com.dream.dao.wf.NodeDao;
import com.dream.model.wf.Node;

public class NodeServiceImpl implements NodeService {

	private NodeDao nodeDao;
	
	@Override
	public int insert(Node node) {
		return this.nodeDao.insert(node);
	}

	@Override
	public int update(Node node) {
		return this.nodeDao.update(node);
	}

	@Override
	public int delete(String id) {
		return this.nodeDao.delete(id);
	}

	@Override
	public List<Node> findNodes(Page<?> page) {
		return this.nodeDao.findNodes(page);
	}

	@Override
	public Node findNode(String id) {
		return this.nodeDao.findNode(id);
	}

	public NodeDao getNodeDao() {
		return nodeDao;
	}

	public void setNodeDao(NodeDao nodeDao) {
		this.nodeDao = nodeDao;
	}

}
