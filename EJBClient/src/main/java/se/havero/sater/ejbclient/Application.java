
package se.havero.sater.ejbclient;

import java.util.List;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import se.havero.sater.ejbhelloworld.TextProcessorBean;
import se.havero.sater.ejbhelloworld.TextProcessorRemote;

/**
 *
 * @author johan
 */
public class Application {

    private static InitialContext context;

    public static void main(String[] args) throws NamingException {
        
        TextProcessorRemote textProcessor = EJBFactory
                .createTextProcessorBeanFromJNDI("ejb:");
        System.out.println(textProcessor.processText("sample text"));
        textProcessor.addBook("Hitch hikers guide to the galaxy");
        textProcessor.addBook("Cooking book");
        List<String> shelf = textProcessor.getShelf();
        shelf.forEach(s-> System.out.println(s));
        
    }

    private static class EJBFactory {

        private static TextProcessorRemote createTextProcessorBeanFromJNDI(String namespace) throws NamingException {
            return lookupTextProcessorBean(namespace);
        }

        private static TextProcessorRemote lookupTextProcessorBean(String namespace) throws NamingException {
            Context ctx = createInitialContext();
            
            String appName = "";
            //Name of the built jar, war EAP without file extension
            String moduleName = "EJBHelloWorld-1.0-SNAPSHOT";
            String distinctName = "";
            String beanName = TextProcessorBean.class.getSimpleName();
            String viewClassName = TextProcessorRemote.class.getName();
            String lookUp= namespace
                    + appName + "/" + moduleName
                    + "/" + distinctName + "/" + beanName + "!" + viewClassName;
            return (TextProcessorRemote) ctx.lookup(lookUp);
        }

        private static Context createInitialContext() throws NamingException {
            
            Properties jndiProperties = new Properties();
            jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY,
                    "org.jboss.naming.remote.client.InitialContextFactory");
            jndiProperties.put(Context.URL_PKG_PREFIXES,
                    "org.jboss.ejb.client.naming");
            jndiProperties.put(Context.PROVIDER_URL,
                    "http-remoting://localhost:8080");
            jndiProperties.put("jboss.naming.client.ejb.context", true);
            return new InitialContext(jndiProperties);
        }
    }
}
