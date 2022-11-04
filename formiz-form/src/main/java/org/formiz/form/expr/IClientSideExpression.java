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
package org.formiz.form.expr;

import java.util.Set;

/**
 *
 * This is a form expression and can be evaluated on both the client side and
 * the server side.
 *
 * @author Nicolas Richeton
 *
 */
public interface IClientSideExpression extends IServerSideExpression {
	/**
	 * Get the Javascript expression which can be evaluated in a browser.
	 * <p>
	 * It is recommended to replace the server side expressions by they current
	 * value.
	 *
	 * @return Javascript expression (ready to use).
	 */
	String getAsJavascript();

	/**
	 * If the list is not empty, this expression depends on other values
	 * (fields) to be evaluated.
	 * <p>
	 * This method returns only the global dependencies, which are usually
	 * displayed on other/previous pages. This means that the value of these
	 * dependencies will probably not change.
	 *
	 * <p>
	 * These values can be optimized on the server side to reduce the cost of
	 * evaluation on the client side.
	 *
	 * @see #getDependencies()
	 * @see #getLocalDependencies()
	 *
	 * @return global (other screens/pages) dependencies set
	 */
	Set<String> getGlobalDependencies();

	/**
	 * If the list is not empty, this expression depends on other values
	 * (fields) to be evaluated.
	 * <p>
	 * This method returns only the local dependencies, which are usually
	 * displayed on the same screen. This means that the value of these
	 * dependencies are more likely change immediately when interacting with the
	 * form.
	 *
	 * <p>
	 * This requires to evaluate the expression on the client side.
	 *
	 * @see #getDependencies()
	 * @see #getGlobalDependencies()
	 *
	 * @return local (same screen/page) dependencies set
	 */
	Set<String> getLocalDependencies();

}
