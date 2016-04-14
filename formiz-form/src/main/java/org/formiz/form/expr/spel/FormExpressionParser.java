/**
 *  Copyright SCN Guichet Entreprises, Capgemini et contributeurs, (2014-2015)
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

package org.formiz.form.expr.spel;

import java.util.HashSet;
import java.util.regex.Pattern;

import org.formiz.core.expr.IExpression;
import org.formiz.core.expr.IParser;
import org.formiz.core.expr.impl.ParseException;
import org.formiz.form.expr.spel.replace.GenericReplace;
import org.formiz.form.expr.spel.replace.ParentReplace;

/**
 * current ->
 *
 * main object ->
 *
 * ajax -> method @ajax
 *
 * parent -> méthod #getParent
 *
 * Other objects -> Bean resolver @name
 *
 * Context variables
 *
 * @author Nicolas Richeton
 *
 */
public class FormExpressionParser implements IParser {
	private Pattern BEAN_REF;
	private Pattern CURRENT_BEAN_REF;

	private String currentName = "current";
	private String fieldName = "field";
	private String javascriptTemplate = "recupererValeur('$1')"; //$NON-NLS-1$
	private String objectName = "object";

	private String parentName = "parent";

	private IParser parser;

	public FormExpressionParser(IParser parser) {
		this.parser = parser;
	}

	/**
	 * Get the current javascript value template.
	 *
	 * @return javascript expression template.
	 */
	public String getJavascriptTemplate() {
		return javascriptTemplate;
	}

	@Override
	public void init() {
		BEAN_REF = Pattern.compile(objectName + "\\[([A-Za-z0-9_\\.]+)\\]");
		CURRENT_BEAN_REF = Pattern.compile(currentName + "\\[([A-Za-z0-9_\\.]+)\\]");
	}

	@Override
	public IExpression parseExpression(String expressionString) {
		String e = expressionString;

		GenericReplace objectReplace = new GenericReplace(BEAN_REF, "$1");
		e = objectReplace.perform(e);

		GenericReplace currentReplace = new GenericReplace(CURRENT_BEAN_REF, "$1");
		e = currentReplace.perform(e);

		e = new ParentReplace().perform(e);

		try {
			ServerSideFormExpression fe = new ServerSideFormExpression(parser.parseExpression(e));
			fe.setDependencies(new HashSet<String>(objectReplace.getMatchedItems()));
			fe.setText(expressionString);
			return fe;
		} catch (ParseException e1) {
			e1.setOriginalExpression(expressionString);
			throw e1;
		}
	}

	public IExpression parseExpression(String expressionString, boolean clientSide) {

		// If server side, with the main method.
		if (!clientSide) {
			return parseExpression(expressionString);
		}

		String e = expressionString;

		GenericReplace objectReplace = new GenericReplace(BEAN_REF, "$1");
		e = objectReplace.perform(e);

		GenericReplace currentReplace = new GenericReplace(CURRENT_BEAN_REF, "$1");
		e = currentReplace.perform(e);

		e = new ParentReplace().perform(e);

		try {
			ClientSideFormExpression fe = new ClientSideFormExpression(parser.parseExpression(e));
			fe.setDependencies(new HashSet<String>(objectReplace.getMatchedItems()));
			fe.setText(expressionString);

			return fe;
		} catch (ParseException e1) {
			e1.setOriginalExpression(expressionString);
			throw e1;
		}
	}

	/**
	 * Defines the javascript expression template used to retrieve a field
	 * value.
	 * <p>
	 * The templace includes '$1' which should be replaces by field name.
	 * <p>
	 * This value must be set before initialization ({@link #init()}) and is
	 * used during element loading.
	 *
	 *
	 * @param jsTemplate
	 *            The javascript template.
	 */

	public void setJavascriptTemplate(String jsTemplate) {
		javascriptTemplate = jsTemplate;

	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
}
