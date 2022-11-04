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

package org.formiz.form.expr.spel.replace;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Remplace les patterns parent[ X.Y ] par getParent(X).Y;
 *
 */
public class ParentReplace {

	/**
	 * parent\\[([A-Za-z0-9_\\.]+)\\. Ex formalite[reseauCFE]!='A' &&
	 * parent[dirigeant.conjointPresence]=='oui'
	 */
	protected static final Pattern FORM_REF_PARENT = Pattern.compile("parent\\[([A-Za-z0-9_\\.]+)\\.");

	/**
	 * formalite.reseauCFE!='A' &&
	 * getParent('dirigeant').conjointPresence=='oui'
	 */
	protected static final String FORM_REF_PARENT_STATIC = "getParent('$1').";

	/**
	 * (stack\\.\\?\\[class.name\\.contains\\(#capitalize\\(
	 * '[A-Za-z0-9_\\.]+'\\)\\)\\]\\[0\\]\\.[A-Za-z0-9_\\.]+)\\] Ex :
	 * formalite.reseauCFE!='A' &&
	 * stack.?[class.name=='dirigeant'].conjointPresence]=='oui'
	 */
	private static final Pattern FORM_REF_PARENT_STATIC_2 = Pattern
			.compile("(getParent\\('[A-Za-z0-9_\\.]+'\\)\\.[A-Za-z0-9_\\.]+)\\]");
	private static final String VAR_1 = "$1"; //$NON-NLS-1$

	/**
	 * Execute les remplassement sur l'expression
	 * 
	 * @param expr
	 *            expression a reformatter
	 */
	public String perform(String expr) {
		String result;
		// gestion des parents
		Matcher m = FORM_REF_PARENT.matcher(expr);
		result = m.replaceAll(FORM_REF_PARENT_STATIC);

		m = FORM_REF_PARENT_STATIC_2.matcher(result);
		result = m.replaceAll(VAR_1);

		return result;
	}
}
