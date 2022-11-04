/**
 * Copyright SCN Guichet Entreprises, Capgemini and contributors (2014-2016)
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
package org.formiz.core.impl;

import org.formiz.core.FormizMetadata;
import org.formiz.core.InputSource;
import org.formiz.core.SimpleElement;
import org.formiz.core.expr.IParser;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * This {@link FormizMetadata} implementation is a wrapper for another
 * implementation and supports reloading of Formiz elements.
 * <p>
 * {@link #setType(String)} have to be called with the target instance type
 * before initialisation.
 */
public class ReloadableMetadata implements FormizMetadata {

    private String clazz;
    private FormizMetadata currentMetadata = null;
    private IParser parser = null;
    private InputSource[] source;

    @Override
    public void addElement(SimpleElement el) {
        currentMetadata.addElement(el);
    }

    @Override
    public SimpleElement getElement(String group, String id) {
        return currentMetadata.getElement(group, id);
    }

    @Override
    public List<SimpleElement> getElementOfType(String className, String id) {
        return currentMetadata.getElementOfType(className, id);
    }

    @Override
    public List<SimpleElement> getElementsByGroup(String valuesGroup) {
        return currentMetadata.getElementsByGroup(valuesGroup);
    }

    @Override
    public IParser getParser() {
        return parser;
    }

    @Override
    public void init() throws IOException {
        try {
            FormizMetadata newMetadata = (FormizMetadata) Class.forName(clazz).getDeclaredConstructor().newInstance();
            newMetadata.setInputSources(source);

            newMetadata.setParser(parser);
            newMetadata.init();
            currentMetadata = newMetadata;
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            throw new IOException(e);
        } catch (InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Try to reload elements.
     * <p>
     * If an exception occurs, the current metadata instance is not modified.
     */
    public void reload() throws IOException {
        init();
    }

    @Override
    public void removeElement(SimpleElement el) {
        currentMetadata.removeElement(el);
    }

    @Override
    public void setInputSources(InputSource... source) {
        this.source = source;
    }

    @Override
    public void setParser(IParser parser) {
        this.parser = parser;
    }

    /**
     * Set the underling {@link FormizMetadata} instance Java type.
     *
     * @param clazz Java class name.
     */
    public void setType(String clazz) {
        this.clazz = clazz;
    }
}
