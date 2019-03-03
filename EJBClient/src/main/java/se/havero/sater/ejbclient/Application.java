
package se.havero.sater.ejbclient;

import java.util.List;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import se.havero.sater.ejbhelloworld.LibrarySessionBean;
import se.havero.sater.ejbhelloworld.LIbrarySessionRemote;

/**
 *
 * @author johan
 */
public class Application {

    private static InitialContext context;

    public static void main(String[] args) throws NamingException {
        
        LIbrarySessionRemote libararySession = EJBFactory
                .createTextProcessorBeanFromJNDI("ejb:");
        System.out.println(libararySession.processText("sample text"));
        libararySession.addBook("Hitch hikers guide to the galaxy");
        libararySession.addBook("1983");
        List<String> shelf = libararySession.getShelf();
        shelf.forEach(s-> System.out.println(s));
        
    }

    private static class EJBFactory {

        private static LIbrarySessionRemote createTextProcessorBeanFromJNDI(String namespace) throws NamingException {
            return lookupTextProcessorBean(namespace);
        }

        private static LIbrarySessionRemote lookupTextProcessorBean(String namespace) throws NamingException {
            Context ctx = createInitialContext();
            
            String appName = "";
            //Name of the built jar, war EAP without file extension
            String moduleName = "EJBHelloWorld-1.0-SNAPSHOT";
            String distinctName = "";
            String beanName = LibrarySessionBean.class.getSimpleName();
            String viewClassName = LIbrarySessionRemote.class.getName();
            String lookUp= namespace
                    + appName + "/" + moduleName
                    + "/" + distinctName + "/" + beanName + "!" + viewClassName;
            return (LIbrarySessionRemote) ctx.lookup(lookUp);
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
