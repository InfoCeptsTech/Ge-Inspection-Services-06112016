package com.ge.inspection.ir.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(Include.NON_NULL)
public class MediaModel {
    private String title;
    @JsonProperty("img")
    private List<ImageModel> imageModel;
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
	public MediaModel(String title, List<ImageModel> imageModel) {
		this.title = title;
		this.imageModel = imageModel;
	}
	public MediaModel(String title) {
		super();
		this.title = title;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MediaModel other = (MediaModel) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

  
   
}
