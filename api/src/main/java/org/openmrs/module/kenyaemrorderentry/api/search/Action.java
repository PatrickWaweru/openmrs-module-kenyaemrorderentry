/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.kenyaemrorderentry.api.search;

/**
 * Represents a function with a single parameter and no return value.
 * @param <TParm1> The first parameter class.
 */
public interface Action<TParm> {
	/**
	 * Executes the action.
	 * @param parameter The parameter.
	 */
	void apply(TParm parameter);
}
