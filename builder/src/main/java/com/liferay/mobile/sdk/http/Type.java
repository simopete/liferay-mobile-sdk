/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.sdk.http;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Peter Simon
 */
public class Type {

	public Type(JSONObject jsonObj) throws JSONException {
		type = jsonObj.getString("type");

		JSONArray properties = jsonObj.getJSONArray("properties");

		for (int i = 0; i < properties.length(); i++) {
			JSONObject property = properties.getJSONObject(i);
			this.properties.add(new Property(property));
		}
	}

	public List<Property> getProperties() {
		return properties;
	}

	public String getType() {
		return type;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}

	public void setType(String type) {
		this.type = type;
	}

	private List<Property> properties = new ArrayList<Property>();
	private String type;

}