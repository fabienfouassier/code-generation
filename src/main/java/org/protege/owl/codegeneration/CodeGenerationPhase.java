package org.protege.owl.codegeneration;

public enum CodeGenerationPhase {
	CREATE_INTERFACE_VM("interface.vm"),
	CREATE_IMPLEMENTATION_VM("implementation.vm"),
	CREATE_VOCABULARY_VM("vocabulary.vm"),
	CREATE_FACTORY_VM("factory.vm")
	;
	
	private String templateName;
	
	private CodeGenerationPhase(String templateName) {
		this.templateName = templateName;
	}
	
	public String getTemplateName() {
		return templateName;
	}
}
