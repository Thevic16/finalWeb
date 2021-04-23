package edu.pucmm.eict.controllers;

import java.lang.reflect.Method;
import javax.xml.ws.Endpoint;
import com.sun.net.httpserver.HttpContext;
import org.eclipse.jetty.http.spi.HttpSpiContextHandler;
import org.eclipse.jetty.http.spi.JettyHttpContext;
import org.eclipse.jetty.http.spi.JettyHttpServer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

import edu.pucmm.eict.services.soap.FormWebService;
import edu.pucmm.eict.utils.BaseController;
import io.javalin.Javalin;

public class SoapController extends BaseController {

  public SoapController(Javalin app) {
    super(app);
  }

  @Override
  public void applyRoutes() {
    // TODO Auto-generated method stub
    Server server = app.server().server();
    ContextHandlerCollection contextHandlerCollection = new ContextHandlerCollection();
    server.setHandler(contextHandlerCollection);

    // Contexto donde estoy agrupando.
    try {
      HttpContext context = build(server, "/ws");

      // El o los servicios que estoy agrupando en ese contexto
      FormWebService wsa = new FormWebService();
      Endpoint endpoint = Endpoint.create(wsa);
      endpoint.publish(context);
      // Para acceder al wsdl en http://localhost:7000/ws/EstudianteWebServices?wsdl
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   *
   * @param server
   * @param contextString
   * @return
   * @throws Exception
   */
  private HttpContext build(Server server, String contextString) throws Exception {
    JettyHttpServer jettyHttpServer = new JettyHttpServer(server, true);
    JettyHttpContext ctx = (JettyHttpContext) jettyHttpServer.createContext(contextString);
    Method method = JettyHttpContext.class.getDeclaredMethod("getJettyContextHandler");
    method.setAccessible(true);
    HttpSpiContextHandler contextHandler = (HttpSpiContextHandler) method.invoke(ctx);
    contextHandler.start();
    return ctx;
  }

}