package webcube; /**
 * Created by satyakb on 11/15/14.
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.glyptodon.guacamole.GuacamoleException;
import org.glyptodon.guacamole.net.GuacamoleSocket;
import org.glyptodon.guacamole.net.GuacamoleTunnel;
import org.glyptodon.guacamole.net.InetGuacamoleSocket;
import org.glyptodon.guacamole.protocol.ConfiguredGuacamoleSocket;
import org.glyptodon.guacamole.protocol.GuacamoleConfiguration;
import org.glyptodon.guacamole.servlet.GuacamoleHTTPTunnelServlet;
import org.glyptodon.guacamole.servlet.GuacamoleSession;

public class WebcubeTunnelServlet
        extends GuacamoleHTTPTunnelServlet {

    @Override
    protected GuacamoleTunnel doConnect(HttpServletRequest request)
            throws GuacamoleException {

        System.out.println(request.getPathInfo().substring(1));
        // Create our configuration
        GuacamoleConfiguration config = new GuacamoleConfiguration();
        config.setProtocol("vnc");
        config.setParameter("hostname", request.getPathInfo().substring(1));
        config.setParameter("port", "5901");
        config.setParameter("password", "test");

        // Connect to guacd - everything is hard-coded here.
        System.out.println("herewego");
        GuacamoleSocket socket = new ConfiguredGuacamoleSocket(
                new InetGuacamoleSocket("10.8.67.247", 4822),
                config
        );
        System.out.println("niggawemadeit");
        // Establish the tunnel using the connected socket
        GuacamoleTunnel tunnel = new GuacamoleTunnel(socket);

        // Attach tunnel to session
        HttpSession httpSession = request.getSession(true);
        GuacamoleSession session = new GuacamoleSession(httpSession);
        session.attachTunnel(tunnel);

        // Return pre-attached tunnel
        return tunnel;

    }

}
