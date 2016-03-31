/**
 *  Copyright SCN Guichet Entreprises, Capgemini and contributors (2014-2016)
 *
 * This software is a computer program whose purpose is to [describe
 * functionalities and technical features of your software].
 *
 * This software is governed by the CeCILL  license under French law and
 * abiding by the rules of distribution of free software.  You can  use,
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info".
 *
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability.
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or
 * data to be ensured and,  more generally, to use and operate it in the
 * same conditions as regards security.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 */
package org.formiz.core.expr.impl;

import static org.apache.commons.lang3.StringUtils.substring;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.formiz.core.expr.IContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class injects functions and dynamic variables in a FormizContext.
 * <p>
 * These objects are configured in the context builder on startup and can be
 * applied on the Formiz context before expression evaluation.
 *
 */
public class ContextBuilder {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContextBuilder.class);

	// Input set using spring.
	private Map<String, String> functions;
	// After processing
	private Map<String, Method> methods;

	private Map<String, Method> providers;
	private Map<String, String> variableProviders;

	/**
	 * Configure Formiz context with functions and variables.
	 */
	public void build(IContext context) {
		for (String key : methods.keySet()) {
			context.registerFunction(key, methods.get(key));
		}

		for (String key : providers.keySet()) {
			try {
				context.setVariable(key, providers.get(key).invoke(null));
			} catch (IllegalArgumentException e) {
				LOGGER.warn(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				LOGGER.warn(e.getMessage(), e);
			} catch (InvocationTargetException e) {
				LOGGER.warn(e.getMessage(), e);
			}
		}
	}

	private void getMethods(Map<String, String> config, Map<String, Method> target) throws ClassNotFoundException {
		if (config == null) {
			return;
		}

		for (String key : config.keySet()) {
			String methodFullName = config.get(key);

			String clazz = substring(methodFullName, 0, methodFullName.lastIndexOf('.'));
			String methodName = substring(methodFullName, methodFullName.lastIndexOf('.') + 1);

			Method[] allMethods = Class.forName(clazz).getMethods();
			Method method = null;
			for (Method m : allMethods) {
				if (StringUtils.equals(m.getName(), methodName)) {
					method = m;
					break;
				}
			}

			if (method == null) {
				throw new IllegalArgumentException("Method " + methodName + " not found on class " + clazz);
			}

			LOGGER.info("Registered method {} -> {}.{}(...)", key, clazz, methodName);

			target.put(key, method);
		}
	}

	public void init() throws ClassNotFoundException {
		methods = new HashMap<String, Method>();
		getMethods(functions, methods);

		providers = new HashMap<String, Method>();
		getMethods(variableProviders, providers);
	}

	public void setFunctions(Map<String, String> functions) {
		this.functions = functions;
	}

	public void setVariableProviders(Map<String, String> variableProviders) {
		this.variableProviders = variableProviders;
	}

}
