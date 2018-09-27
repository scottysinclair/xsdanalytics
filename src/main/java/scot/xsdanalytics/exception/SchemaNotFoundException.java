package scot.xsdanalytics.exception;

import static scot.xsdanalytics.exception.MissingSchemaInfo.DependencyType;

import java.util.LinkedList;
import java.util.List;

public class SchemaNotFoundException extends XsdDefinitionException {

    private static final long serialVersionUID = 1L;

    /**
     * returns an exception which has the missing schemas from a and b
     * @param a can be null
     * @param b must not be null
     * @return the new exception or b is a is null
     */
    public static SchemaNotFoundException merge(SchemaNotFoundException a, SchemaNotFoundException b) {
        if (a == null) {
            return b;
        }
        return new SchemaNotFoundException(a, b);
    }



    private List<MissingSchemaInfo> missingSchemas;

    public SchemaNotFoundException(DependencyType dependencyType, String namespaceUri, String schemaLocation) {
        super("Could not resolve schema '" + schemaLocation + "' with namesaceUri='" + namespaceUri + "'");
        missingSchemas = new LinkedList<>();
        missingSchemas.add(new MissingSchemaInfo(dependencyType, namespaceUri, schemaLocation));
    }

    private SchemaNotFoundException(SchemaNotFoundException a, SchemaNotFoundException b) {
        super(concatMessages(a, b));
        missingSchemas = new LinkedList<>();
        missingSchemas.addAll( a.getMissingSchemas() );
        missingSchemas.addAll( b.getMissingSchemas() );
    }

    public List<MissingSchemaInfo> getMissingSchemas() {
        return missingSchemas;
    }

    private static String concatMessages(SchemaNotFoundException a, SchemaNotFoundException b) {
        StringBuilder sb = new StringBuilder();
        List<MissingSchemaInfo> all = new LinkedList<>(a.getMissingSchemas());
        all.addAll(b.getMissingSchemas());
        for (MissingSchemaInfo missingSchemaInfo: all) {
            missingSchemaInfo.appendExceptionMessage(sb);
            sb.append('\n');
        }
        sb.setLength(sb.length()-1);
        return sb.toString();
    }
}
