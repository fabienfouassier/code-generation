package org.protege.owl.codegeneration;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.protege.editor.core.ModelManager;
import org.protege.editor.owl.model.OWLModelManager;
import org.protege.owl.codegeneration.names.NamingUtilities;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;


/**
 * @author ISEN
 *
 */
public class Renamer {
	
	private OWLModelManager manager;
	
	
	/**
	 * Constructor
	 * @param manager
	 */
	public Renamer( OWLModelManager manager) {
		super();
		this.manager = manager;
	}
	
	public Renamer() {
		
	}


	/**
	 * Gets the name of an {@link OWLClass}.
	 * @param owlClass
	 * @return
	 */
	public String getClassName(OWLClass owlClass) {
		String name = manager.getRendering(owlClass);
		name = NamingUtilities.convertToJavaIdentifier(name);
		name = convertInitialLetterToUpperCase(name);
		return name;
	}
	
	public String getInterfaceName(OWLClass owlClass) {
		String name = manager.getRendering(owlClass);
		name = NamingUtilities.convertToJavaIdentifier(name);
		name = NamingUtilities.convertInitialLetterToUpperCase(name);
		return name;
	}
	
	/**
	 * Gets the name of a {@link OWLObjectProperty} and change it in a Java identifier.
	 * @param owlObjectProperty
	 * @return
	 */
	public String getObjectPropertyName(OWLObjectProperty owlObjectProperty) {
		String name = manager.getRendering(owlObjectProperty);
		name = NamingUtilities.convertToJavaIdentifier(name);
		return name;
	}
	
	
	/**
	 * Gets the name of a {@link OWLDataProperty} and change it in a Java identifier .
	 * @param owlDataProperty
	 * @return
	 */
	public String getDataPropertyName(OWLDataProperty owlDataProperty) {
		String name = manager.getRendering(owlDataProperty);
		name = NamingUtilities.convertToJavaIdentifier(name);
		return name;
	}
	
	/**
	 * Changes the string in parameter by putting the first letter in uppercase.
	 * @param name
	 * @return 
	 */
	public String convertInitialLetterToUpperCase(String name) {
        if (name == null) {
            return null;
        }
        if (name.length() > 1) {
            return Character.toUpperCase(name.charAt(0)) + name.substring(1);
        } else {
            return name.toUpperCase();
        }
    }
	
	
	/**
	 * Gets all the super classes of an {@link OWLClass} and save them in a Collection.
	 * @param owlClass
	 * @return
	 */
	public Collection<OWLClass> getSuperClasses(OWLClass owlClass, OWLOntology ontology, OWLDataFactory factory) {
		Set<OWLClass> superClasses = new HashSet<OWLClass>();
		for (OWLClassExpression ce : owlClass.getSuperClasses(ontology.getImportsClosure())) {
			if (!ce.isAnonymous()) {
				superClasses.add(ce.asOWLClass());
			}
			else if (ce instanceof OWLObjectIntersectionOf) {
			    superClasses.addAll(getNamedConjuncts((OWLObjectIntersectionOf) ce));
			}
		}
		for (OWLClassExpression ce : owlClass.getEquivalentClasses(ontology.getImportsClosure())) {
		    if (ce instanceof OWLObjectIntersectionOf) {
                superClasses.addAll(getNamedConjuncts((OWLObjectIntersectionOf) ce));
            }
		}
		superClasses.remove(factory.getOWLThing());
		return superClasses;
	}
	
	
	/**
	 * Private method only used in the method getSuperClasses.
	 * @param ce
	 * @return
	 */
	private Collection<OWLClass> getNamedConjuncts(OWLObjectIntersectionOf ce) {
	    Set<OWLClass> conjuncts = new HashSet<OWLClass>();
	    for (OWLClassExpression conjunct : ce.getOperands()) {
	        if (!conjunct.isAnonymous()) {
	            conjuncts.add(conjunct.asOWLClass());
	        } 
	    }
	    return conjuncts;
	}
	
	
	/**
	 * Gets the IRI of an {@link OWLEntity}.
	 * @param entity
	 * @return
	 */
	/*public IRI getIRI(OWLEntity entity) {
		IRI replacement = replacementMap.get(entity);
        if (replacement != null) {
            return replacement;
        } else {
            return entity.getIRI();
        }
	}*/
}