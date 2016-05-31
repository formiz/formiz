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
package org.formiz.core.expr.js;

import java.util.Collections;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.formiz.core.expr.IContext;
import org.formiz.core.expr.IExpression;

/**
 * Javascript-based expression.
 *
 * @author Nicolas Richeton
 *
 */
public class JsExpression implements IExpression {

	private final ScriptEngine engine = new ScriptEngineManager().getEngineByExtension("js");

	private final String expr;

	private String text;

	final String enhancedExpr;

	private final CompiledScript compiledExpr;

	public JsExpression(final String e) {
		expr = e;
		enhancedExpr = "with(Object.bindProperties({}, formizRoot)){ " + expr + " }";

		try {
			if (engine instanceof Compilable) {
				compiledExpr = ((Compilable) engine).compile(enhancedExpr);
			} else {
				compiledExpr = null;
			}
		} catch (final ScriptException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public String getInternalText() {
		return expr;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public Object getValue(final IContext context) {
		final JsContext ctx = (JsContext) context;

		final Bindings bindings = ctx.getBindings();
		if (null == ctx.getRoot()) {
			bindings.put("formizRoot", Collections.emptyMap());
		}

		try {
			if (null != compiledExpr) {
				return compiledExpr.eval(ctx.getBindings());
			} else {
				return engine.eval(enhancedExpr, ctx.getBindings());
			}
		} catch (final ScriptException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void setText(final String text) {
		this.text = text;
	}

}
