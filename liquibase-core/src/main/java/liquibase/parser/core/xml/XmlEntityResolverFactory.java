package liquibase.parser.core.xml;

import liquibase.Scope;
import liquibase.plugin.AbstractPluginFactory;

/**
 * Factory to find {@link XmlEntityResolver} plugins.
 */
public class XmlEntityResolverFactory extends AbstractPluginFactory<XmlEntityResolver> {

    public XmlEntityResolverFactory(Scope factoryScope) {
        super(factoryScope);
    }

    @Override
    protected Class<XmlEntityResolver> getPluginClass() {
        return XmlEntityResolver.class;
    }

    @Override
    protected int getPriority(XmlEntityResolver obj, Scope scope, Object... args) {
        return obj.getPriority((String) args[0], (String) args[1], (String) args[2], (String) args[3], scope);
    }

    public XmlEntityResolver getResolver(String name, String publicId, String baseURI, String systemId, Scope scope) {
        return this.getPlugin(scope, name, publicId, baseURI, systemId);
    }
}
