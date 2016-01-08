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

import org.formiz.core.expr.IExpression;
import org.formiz.core.expr.IParser;

/**
 * This parser implements a basic English native language support.
 *
 *
 *
 */
public class EnglishExpressionParser implements IParser {

	private IParser parser;

	public EnglishExpressionParser(IParser parser) {
		this.parser = parser;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.formiz.core.expr.IParser#init()
	 */
	@Override
	public void init() {
		// No init required
	}

	@Override
	public IExpression parseExpression(String expressionString) {
		String e = expressionString;
		e = replaceVraiFaux(e);
		e = replaceEquals(e);
		e = replaceEtOu(e);
		e = replaceInferieurSuperieur(e);
		e = replaceAbsent(e);
		try {
			IExpression parseExpression = parser.parseExpression(e);
			parseExpression.setText(expressionString);
			return parseExpression;
		} catch (ParseException e1) {
			e1.setOriginalExpression(expressionString);
			throw e1;
		}
	}

	private String replaceAbsent(String e) {
		e = e.replace(" is empty", "==null");
		e = e.replace(" is not empty", "!= null");
		return e;
	}

	private String replaceEquals(String e) {
		e = e.replace(" is equal to ", " == ");
		e = e.replace(" is not equal to ", " != ");
		return e;
	}

	private String replaceEtOu(String e) {
		e = e.replace(" AND ", " && ");
		e = e.replace(" OR ", " || ");
		return e;
	}

	private String replaceInferieurSuperieur(String e) {
		e = e.replace(" is less than ", " < ");
		e = e.replace(" is greater than ", " > ");
		return e;
	}

	private String replaceVraiFaux(String e) {
		e = e.replace(" is TRUE", " == true");
		e = e.replace(" is FALSE", " == false");
		e = e.replace(" TRUE", " true");
		e = e.replace(" FALSE", " false");
		return e;
	}

}
