package org.protege.owl.codegeneration;


import static org.protege.owl.codegeneration.CodeGenerationPhase.CREATE_FACTORY_VM;
import static org.protege.owl.codegeneration.CodeGenerationPhase.CREATE_IMPLEMENTATION_VM;
import static org.protege.owl.codegeneration.CodeGenerationPhase.CREATE_INTERFACE_VM;
import static org.protege.owl.codegeneration.CodeGenerationPhase.CREATE_VOCABULARY_VM;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.protege.editor.owl.codegeneration.ProtegeNames;
import org.protege.editor.owl.model.OWLModelManager;
import org.semanticweb.owlapi.model.OWLClass;

/**
 * A class that can create Java interfaces in the Protege-OWL format
 * 
 * @author z.khan
 * 
 */
public class JavaCodeGenerator {
	public static final Logger LOGGER = Logger.getLogger(JavaCodeGenerator.class);

    private Worker worker;

    /**
     * Constructor
     */
    public JavaCodeGenerator(Worker worker) {
    	this.worker = worker;
        worker.initialize();
    }

    /**
     * Initiates the code generation
     * 
     * @throws IOException
     */
    public void createAll() throws IOException {
        Collection<OWLClass> owlClassList = worker.getOwlClasses();
        printVocabularyCode(owlClassList);
        printFactoryClassCode(owlClassList);
        for (OWLClass owlClass : owlClassList) {
            createInterface(owlClass);
            createImplementation(owlClass);
        }
    }

    /**
     * Generates interface code for the provided OWlClass
     * 
     * @param owlClass The class whose interface code is to generated
     * @throws IOException
     */
    private void createInterface(OWLClass owlClass) throws IOException {
        File baseFile = worker.getInterfaceFile(owlClass);
        FileWriter fileWriter = new FileWriter(baseFile);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printInterfaceCode(owlClass, printWriter);
        printWriter.close();
    }

    /**
     * Writes the interface code for the provided OWlClass to the PrintStream
     * 
     * @param interfaceName 
     * @param owlClass
     * @param printWriter
     * @throws IOException 
     */
    private void printInterfaceCode(OWLClass owlClass, PrintWriter printWriter) throws IOException {
    	//Collection<OWLObjectProperty> owlObjectProperties = worker.getObjectPropertiesForClass(owlClass);
        //Collection<OWLDataProperty> owlDataProperties = worker.getDataPropertiesForClass(owlClass);
        
    	//Map<SubstitutionVariable, String> substitutions = new EnumMap<SubstitutionVariable, String>(SubstitutionVariable.class);
    	
    	/*for (OWLObjectProperty owlObjectProperty : owlObjectProperties) {
			worker.configureAllSubstitutions(substitutions, owlClass, owlObjectProperty);
        }
        
        for (OWLDataProperty owlDataProperty : owlDataProperties) {
			worker.configureAllSubstitutions(substitutions, owlClass, owlDataProperty);
        }*/
        
        String template = worker.getTemplate(CREATE_INTERFACE_VM);
        Reader reader = new StringReader(template);
    	fillTemplate(printWriter, template, reader);
    	reader.close();
    }
    
    
    private void createImplementation(OWLClass owlClass) throws IOException {
        File baseFile = worker.getImplementationFile(owlClass);
        FileWriter fileWriter = new FileWriter(baseFile);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printImplementationCode(owlClass, printWriter);
        printWriter.close();
    }

    private void printImplementationCode(OWLClass owlClass, PrintWriter printWriter) throws IOException {
        //Collection<OWLObjectProperty> owlObjectProperties = worker.getObjectPropertiesForClass(owlClass);
        //Collection<OWLDataProperty> owlDataProperties = worker.getDataPropertiesForClass(owlClass);
        
    	//Map<SubstitutionVariable, String> substitutions = new EnumMap<SubstitutionVariable, String>(SubstitutionVariable.class);
                
    	/*for (OWLObjectProperty owlObjectProperty : owlObjectProperties) {
			worker.configureAllSubstitutions(substitutions, owlClass, owlObjectProperty);
        }
        
        for (OWLDataProperty owlDataProperty : owlDataProperties) {
			worker.configureAllSubstitutions(substitutions, owlClass, owlDataProperty);
        }*/
                
        String template = worker.getTemplate(CREATE_IMPLEMENTATION_VM);
        Reader reader = new StringReader(template);
        fillTemplate(printWriter, template, reader);
        reader.close();
    }

    /** Initializes the vocabulary code generation 
     * @param owlClassList
     * @throws IOException
     */
    private void printVocabularyCode(Collection<OWLClass> owlClassList) throws IOException {
        File vocabularyFile = worker.getVocabularyFile();
        FileWriter vocabularyfileWriter = new FileWriter(vocabularyFile);
        PrintWriter vocabularyPrintWriter = new PrintWriter(vocabularyfileWriter);
    	//Map<SubstitutionVariable, String> substitutions = new EnumMap<SubstitutionVariable, String>(SubstitutionVariable.class);
            	
    	/*for (OWLClass owlClass : owlClassList) {
        	Collection<OWLObjectProperty> owlObjectProperties = worker.getObjectPropertiesForClass(owlClass);
            Collection<OWLDataProperty> owlDataProperties = worker.getDataPropertiesForClass(owlClass);

        	for (OWLObjectProperty owlObjectProperty : owlObjectProperties) {
    			worker.configureAllSubstitutions(substitutions, owlClass, owlObjectProperty);
    		}
        	
        	for (OWLDataProperty owlDataProperty : owlDataProperties) {
    			worker.configureAllSubstitutions(substitutions, owlClass, owlDataProperty);
        	}
        }*/
    	
        String template = worker.getTemplate(CREATE_VOCABULARY_VM);
        Reader reader = new StringReader(template);
        fillTemplate(vocabularyPrintWriter, template, reader);
        vocabularyPrintWriter.close();
        reader.close();
    }
    
    /** Initializes the code generation for factory classes 
     * @param owlClassList
     * @throws IOException
     */
    private void printFactoryClassCode(Collection<OWLClass> owlClassList) throws IOException {
        FileWriter factoryFileWriter = null;
        PrintWriter factoryPrintWriter = null;
        File factoryFile = worker.getFactoryFile();
        factoryFileWriter = new FileWriter(factoryFile);
        factoryPrintWriter = new PrintWriter(factoryFileWriter);
        
    	//Map<SubstitutionVariable, String> substitutions = new EnumMap<SubstitutionVariable, String>(SubstitutionVariable.class);
    	
    	/*for (OWLClass owlClass : owlClassList) {
			worker.configureAllSubstitutions(substitutions, owlClass, null);
			
    	}*/

    	String template = worker.getTemplate(CREATE_FACTORY_VM);
    	Reader reader = new StringReader(template);
    	fillTemplate(factoryPrintWriter, template, reader);
    	factoryPrintWriter.close();
    	reader.close();
    }
	
	private void fillTemplate(PrintWriter writer, String template, Reader reader) {
		VelocityEngine ve = new VelocityEngine();
		ve.init();
		VelocityContext context = new VelocityContext();
		initializeContext(context);
		Velocity.evaluate(context, writer, "velocity string replacement", reader);
	}
	
	private void initializeContext(VelocityContext context) {
		context.put("root", worker.getInference());
		context.put("options", new CodeGenerationOptions());
		context.put("renamer", new Renamer());
	}
	// the root works a little but only for recovering the owlClasses.
	// the options works only for getting the IRI of the class.
}