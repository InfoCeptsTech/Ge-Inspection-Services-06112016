package com.ge.inspection.ir.model;

import java.util.List;

public class PhaseModel {
    private String title;
    private List<ImageModel> imageModel;
  
    
	public PhaseModel(String title, List<ImageModel> imageModel) {
		super();
		this.title = title;
		this.imageModel = imageModel;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<ImageModel> getImageModel() {
		return imageModel;
	}
	public void setImageModel(List<ImageModel> imageModel) {
		this.imageModel = imageModel;
	}
  
  
}
