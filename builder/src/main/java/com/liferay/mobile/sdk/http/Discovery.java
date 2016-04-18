/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.sdk.http;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Bruno Farache
 */
public class Discovery {

	public Discovery(String json) throws JSONException {
		JSONObject jsonObj = new JSONObject(json);

		_basePath = jsonObj.getString("basePath");

		if (jsonObj.has("discover")) {
			JSONArray jsonArray = jsonObj.getJSONArray("discover");

			for (int i = 0; i < jsonArray.length(); i++) {
				_discover.add(jsonArray.getString(i));
			}
		}

		JSONArray actions = jsonObj.getJSONArray("services");

		for (int i = 0; i < actions.length(); i++) {
			Action action = new Action(actions.getJSONObject(i));

			_actions.add(action);
		}

		JSONArray types = jsonObj.getJSONArray("types");

		for (int i = 0; i < types.length(); i++) {
			Type type = new Type(types.getJSONObject(i));

			_types.add(type);
		}
	}

	public List<Action> getActions() {
		return Collections.unmodifiableList(_actions);
	}

	public String getBasePath() {
		return _basePath;
	}

	public ArrayList<String> getDiscover() {
		return _discover;
	}

	public List<Type> getTypes() {
		return _types;
	}

	private ArrayList<Action> _actions = new ArrayList<Action>();
	private String _basePath;
	private ArrayList<String> _discover = new ArrayList<String>();
	private List<Type> _types = new ArrayList<Type>();

}