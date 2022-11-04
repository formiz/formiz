/**
 *  Copyright SCN Guichet Entreprises, Capgemini and contributors (2014-2016)
 * <p>
 * This software is a computer program whose purpose is to [describe
 * functionalities and technical features of your software].
 * <p>
 * This software is governed by the CeCILL  license under French law and
 * abiding by the rules of distribution of free software.  You can  use,
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info".
 * <p>
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability.
 * <p>
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
 * <p>
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 */

package org.formiz.core.expr.impl;

import org.formiz.core.expr.IExpression;
import org.formiz.core.expr.IParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExpressionParserUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExpressionParserUtil.class);

	/**
	 * Parse expression using parser.
	 * <p>
	 * On error, adds id/group information to the exception.
	 *
	 */
	public static IExpression parse(String id, String group, IParser parser, String expression) {
		try {
			return parser.parseExpression(expression);
		} catch (ParseException e) {
			String message = "Error while parsing expression for ID:" + id + " GROUP:" + group + " expr:\""
					+ e.getOriginalExpression() + "\" expanded to:\"" + e.getExpression() + "\"";
			LOGGER.info(message, e);
			throw new IllegalArgumentException(message, e);
		}

	}

	private ExpressionParserUtil() {
	}

}
