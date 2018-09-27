package scott.xsdanalytics;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import scot.xsdanalytics.exception.XsdDefinitionException;

import static scott.xsdanalytics.SchemaElements.*;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * The extension element extends an existing simpleType or complexType element
 *
 * <extension
 * id=ID
 * base=QName
 * any attributes
 * >

 * (annotation?,((group|all|choice|sequence)?,
 * ((attribute|attributeGroup)*,anyAttribute?)))
 *
 * </extension>
 */
public class XsdExtension implements XsdNode {
    private final XsdNode parent;
    private final Element domElement;

    public XsdExtension(XsdNode parent, Element domElement) {
        this.parent = parent;
        this.domElement = domElement;
    }

    @Override
    public XsdDefinition getXsdDefinition() {
        return parent.getXsdDefinition();
    }

    @Override
    public Node getDomNode() {
      return domElement;
    }

    @Override
    public List<XsdDocumentation> getDocumentation() throws XsdDefinitionException {
        return Common.getDocumentation(domElement);
    }

    @Override
    public List<XsdAttribute> getAttributes() throws XsdDefinitionException {
        String base = domElement.getAttribute("base");
        if (base.isEmpty()) {
            throw new XsdDefinitionException("base must be specified on the extension element");
        }
        XsdNode xsdType = getXsdDefinition().findType( new QualifiedName(getXsdDefinition(), base, true) );

        /*
         * add the attributes from the base type
         */
        List<XsdAttribute> attributes = new LinkedList<>();
        attributes.addAll( xsdType.getAttributes() );

        attributes.addAll( Common.getAttributes(this, domElement, ATTRIBUTE, ATTRIBUTEGROUP) );
        return attributes;
    }

    @Override
    public List<XsdElement> getChildTargetElements() throws XsdDefinitionException {
        String base = domElement.getAttribute("base");
        if (base.isEmpty()) {
            throw new XsdDefinitionException("base must be specified on the extension element");
        }
        XsdNode xsdType = getXsdDefinition().findType( new QualifiedName(getXsdDefinition(), base, true) );
        /*
         * add the elements from the base type
         */
        List<XsdElement> elements = new LinkedList<>();
        elements.addAll( xsdType.getChildTargetElements() );

        /*
         * then add any other elements from our own definition
         */
        //use (group|all|choice|sequence)? to get child elements
        elements.addAll( Common.getChildElementsFromOneOf(this, domElement, GROUP, ALL, CHOICE, SEQUENCE) );
        return elements;
    }

    @Override
    public XsdNode getParent() {
        return parent;
    }

    /**
     * @return the XsdSequence child if it contains one, null otherwise.
     * @throws XsdDefinitionException
     */
    public XsdSequence getSequence() throws XsdDefinitionException {
        return (XsdSequence) Common.getXsdNode(this, domElement, SEQUENCE);
    }
}
