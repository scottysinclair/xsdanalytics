package scott.xsdanalytics;

import scot.xsdanalytics.exception.InvalidXsdException;

/**
 * Supported schema elements
 *
 *
 */
public enum SchemaElements {
    ELEMENT("element"),
    ATTRIBUTE("attribute"),
    ATTRIBUTEGROUP("attributeGroup"),
    ALL("all"),
    ANY("any"),
    CHOICE("choice"),
    SIMPLETYPE("simpleType"),
    SIMPLECONTENT("simpleContent"),
    COMPLEXTYPE("complexType"),
    COMPLEXCONTENT("complexContent"),
    EXTENSION("extension"),
    RESTRICTION("restriction"),
    GROUP("group"),
    SEQUENCE("sequence");

    /**
     *
     * @param localName
     * @return the SchemaElement matching the localName
     * @throws InvalidXsdException if the local name is not in the enum
     */
    public static SchemaElements toSchemaElement(String localName) throws InvalidXsdException {
        for (SchemaElements se: values()) {
            if (se.getLocalName().equals(localName)) {
                return se;
            }
        }
        throw new InvalidXsdException("'" + localName + "' is not supported in XSD definition");
    }

    private final String localName;

    private SchemaElements(String localName) {
        this.localName = localName;
    }

    public String getLocalName() {
        return localName;
    }
}
