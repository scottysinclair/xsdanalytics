package scott.xsdanalytics;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import scot.xsdanalytics.exception.XsdDefinitionException;

import static scott.xsdanalytics.SchemaElements.*;

import java.util.List;

/*
*
* The complexContent element defines extensions or restrictions on a complex type that contains mixed content or elements only
* <complexContent
* id=ID
* mixed=true|false
* any attributes
* >
*
* (annotation?,(restriction|extension))
*
* </complexContent>
 */
public class XsdComplexContent implements XsdNode {

    private final XsdNode parent;

    private final Element domElement;

    public XsdComplexContent(XsdNode parent, Element domElement) {
        this.parent = parent;
        this.domElement = domElement;
    }

    @Override
    public Node getDomNode() {
      return domElement;
    }

    @Override
    public XsdDefinition getXsdDefinition() {
        return parent.getXsdDefinition();
    }

    public List<XsdDocumentation> getDocumentation() throws XsdDefinitionException {
        List<XsdDocumentation> documentation = Common.getDocumentation(domElement);
        if (!documentation.isEmpty()) {
            return documentation;
        }
        return getRestrictionOrExtension().getDocumentation();
    }


    public List<XsdElement> getChildTargetElements() throws XsdDefinitionException {
        return getRestrictionOrExtension().getChildTargetElements();
    }

    @Override
    public List<XsdAttribute> getAttributes() throws XsdDefinitionException {
        return getRestrictionOrExtension().getAttributes();
    }

    /**
     * @return the XsdExtension child declaration, if it exists.
     */
    public XsdExtension getExtension() throws XsdDefinitionException {
        return (XsdExtension) Common.getXsdNode(this, domElement, EXTENSION);
    }

    private XsdNode getRestrictionOrExtension() throws XsdDefinitionException {
        XsdNode xsdNode = Common.getXsdNode(this, domElement, RESTRICTION, EXTENSION);
        if (xsdNode == null) {
            throw new XsdDefinitionException("ComplexContext must be either a restriction or extension");
        }
        return xsdNode;
    }

    @Override
    public XsdNode getParent() {
        return parent;
    }
}
