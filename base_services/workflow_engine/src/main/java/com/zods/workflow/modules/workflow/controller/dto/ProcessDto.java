package com.zods.workflow.modules.workflow.controller.dto;

public class ProcessDto {
	String id;

	String deploymentId;

	String name;

	String resourceName;

	String key;

	String diagramresourceName;

	Integer version;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeploymentId() {
		return deploymentId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDiagramresourceName() {
		return diagramresourceName;
	}

	public void setDiagramresourceName(String diagramresourceName) {
		this.diagramresourceName = diagramresourceName;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
