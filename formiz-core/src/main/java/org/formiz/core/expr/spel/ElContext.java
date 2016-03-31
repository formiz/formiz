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

package org.formiz.core.expr.spel;

import java.lang.reflect.Method;
import java.util.Map;

import org.formiz.core.expr.IContext;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class ElContext implements IContext {

	public class Context {
		/**
		 * Main object we are working on.
		 */
		private Object object;
		/**
		 * Current object we are looking at. Can be the main object or one of
		 * its children.
		 */
		private Object root;

		public Object getObject() {
			return object;
		}

		public Object getRoot() {
			return root;
		}

		/**
		 * @param object
		 *            Main object we are working on.
		 * @param root
		 *            if we are looking at a children of the main object, use it
		 *            as root. Can be null, in that case null = object
		 */
		public void setObject(Object object, Object root) {
			this.object = object;
			this.root = root;
			if (root == null) {
				this.root = object;
			}
		}

	}

	private Context ctx;

	private StandardEvaluationContext spelContext;

	public ElContext() {
		ctx = new Context();
		spelContext = new StandardEvaluationContext();
	}

	@Override
	public Object getObject() {
		return ctx.getObject();
	}

	@Override
	public Object getRoot() {
		return ctx.getRoot();
	}

	public StandardEvaluationContext getSpelContext() {
		return spelContext;
	}

	@Override
	public void registerFunction(String name, Method method) {
		spelContext.registerFunction(name, method);
	}

	public void setBeanResolver(BeanResolver br) {
		spelContext.setBeanResolver(br);
	}

	/**
	 * @param object
	 *            Main object we are working on.
	 * @param root
	 *            if we are looking at a children of the main object, use it as
	 *            root. Can be null, in that case null = object
	 */
	@Override
	public void setObject(Object object, Object root) {
		ctx.setObject(object, root);
		spelContext.setRootObject(this.getRoot());

	}

	@Override
	public void setVariable(String name, Object value) {
		spelContext.setVariable(name, value);
	}

	@Override
	public void setVariables(Map<String, Object> variables) {
		spelContext.setVariables(variables);
	}
}
