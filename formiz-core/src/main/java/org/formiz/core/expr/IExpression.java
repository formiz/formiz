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
package org.formiz.core.expr;

/**
 * A formiz expression.
 *
 */
public interface IExpression {

	/**
	 * Returns the underlying (internal) expression string.
	 * <p>
	 * Expression may have been changed during parsing. This returns the result,
	 * as it will be executed on evaluation.
	 *
	 */
	String getInternalText();

	/**
	 * Returns original expression string.
	 * <p>
	 * Expression as it was entered by the user, not the internal
	 * representation.
	 */
	String getText();

	/**
	 * Get expression value in the provided context.
	 *
	 * @param context
	 *            on which the expression should be evaluated.
	 * @return expression value.
	 */
	Object getValue(IContext context);

	/**
	 * Set the original expression string.
	 * <p>
	 * This method should not be called by users. It is reserved for expression
	 * parsers (IParser).
	 *
	 */
	void setText(String t);
}
