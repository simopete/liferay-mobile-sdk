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

package com.liferay.mobile.sdk.android;

import com.liferay.mobile.sdk.BaseBuilder;
import com.liferay.mobile.sdk.http.Action;
import com.liferay.mobile.sdk.http.Discovery;
import com.liferay.mobile.sdk.http.Type;
import com.liferay.mobile.sdk.util.LanguageUtil;
import com.liferay.mobile.sdk.util.Validator;
import com.liferay.mobile.sdk.velocity.VelocityUtil;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.WordUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.tools.generic.EscapeTool;

/**
 * @author Bruno Farache
 */
public class AndroidBuilder extends BaseBuilder {

	@Override
	public void build(
			Discovery discovery, List<Action> actions, String packageName,
			int version, String filter, String destination)
			throws Exception {

		StringBuilder sb = new StringBuilder();

		if (Validator.isNotNull(destination)) {
			sb.append(destination);
			sb.append("/android/");
		}

		sb.append("src/gen/java");
		destination = sb.toString();

		List<VelocityContext> typeVelocityContexts = getTypeVelocityContexts(discovery, packageName, version);

		String modelTemplatePath = "templates/android/model.vm";

		for (VelocityContext typeVelocityContext : typeVelocityContexts) {
			String filePath = getServiceFilePath(typeVelocityContext, destination);
			VelocityUtil.generate(typeVelocityContext, modelTemplatePath, filePath, true);
		}

		VelocityContext serviceContext = getServiceVelocityContext(
				discovery, actions, packageName, version, filter);

		String serviceTemplatePath = "templates/android/service.vm";
		String filePath = getServiceFilePath(serviceContext, destination);

		VelocityUtil.generate(serviceContext, serviceTemplatePath, filePath, true);
	}

	protected List<Action> excludeMethods(
			String className, List<Action> actions) {

		if (!className.equals("DDLRecordService")) {
			return actions;
		}

		List<Action> filteredActions = new ArrayList<Action>();

		boolean addRecord = true;

		for (Action action : actions) {
			String path = action.getPath();

			if (!path.equals("/ddl.ddlrecord/add-record") || addRecord) {
				filteredActions.add(action);
				addRecord = false;
			}
		}

		return filteredActions;
	}

	protected VelocityContext getBaseVelocityContext(Discovery discovery) {
		VelocityContext context = new VelocityContext();

		JavaUtil javaUtil = new JavaUtil();

		context.put(BYTE_ARRAY, LanguageUtil.BYTE_ARRAY);
		context.put(DISCOVERY, discovery);
		context.put(ESCAPE_TOOL, new EscapeTool());
		context.put(UPLOAD_DATA, JavaUtil.UPLOAD_DATA);
		context.put(INTEGER, JavaUtil.INTEGER);
		context.put(JSON_OBJECT_WRAPPER, JavaUtil.JSON_OBJECT_WRAPPER);
		context.put(LANGUAGE_UTIL, javaUtil);
		context.put(VOID, LanguageUtil.VOID);

		return context;
	}

	protected String getServiceFilePath(
			VelocityContext context, String destination) {

		String packageName = (String)context.get(PACKAGE);
		String className = (String)context.get(CLASS_NAME);

		String packagePath = packageName.replace(".", "/");

		StringBuilder sb = new StringBuilder();

		sb.append(destination);
		sb.append("/");
		sb.append(packagePath);
		sb.append("/");

		File file = new File(sb.toString());
		file.mkdirs();

		sb.append(className);
		sb.append(".java");

		return sb.toString();
	}

	protected VelocityContext getServiceVelocityContext(
			Discovery discovery, List<Action> actions, String packageName,
			int version, String filter) {

		VelocityContext context = getBaseVelocityContext(discovery);

		JavaUtil javaUtil = (JavaUtil)context.get(LANGUAGE_UTIL);

		StringBuilder sb = new StringBuilder(packageName);

		sb.append(".v");
		sb.append(version);
		sb.append(".");
		sb.append(filter);

		packageName = sb.toString();

		String className = javaUtil.getServiceClassName(filter);

		context.put(CLASS_NAME, className);
		context.put(ACTIONS, excludeMethods(className, actions));
		context.put(PACKAGE, packageName);

		return context;
	}

	protected List<VelocityContext> getTypeVelocityContexts(
			Discovery discovery, String packageName, int version) {
		List<VelocityContext> contexts = new ArrayList<VelocityContext>();

		List<Type> types = discovery.getTypes();

		if (!types.isEmpty()) {
			VelocityContext baseContext = getBaseVelocityContext(discovery);

			JavaUtil javaUtil = (JavaUtil)baseContext.get(LANGUAGE_UTIL);

			StringBuilder sb = new StringBuilder(packageName);

			sb.append(".v");
			sb.append(version);
			sb.append(".model");

			packageName = sb.toString();

			baseContext.put(PACKAGE, packageName);

			for (Type type : types) {
				VelocityContext context = (VelocityContext)baseContext.clone();

				String typeClass = type.getType();

				int lastIndexOf = typeClass.lastIndexOf('.');

				String className;

				if (lastIndexOf != -1) {
					className = typeClass.substring(lastIndexOf + 1);
				} else {
					className = typeClass;
				}

				className = WordUtils.capitalize(className);

				context.put(CLASS_NAME, className);
				context.put(PROPERTIES, type.getProperties());

				contexts.add(context);
			}
		}

		return contexts;
	}

	protected static final String INTEGER = "INTEGER";

	protected static final String PACKAGE = "package";

	protected static final String PROPERTIES = "properties";

	protected static final String UPLOAD_DATA = "UPLOAD_DATA";

}