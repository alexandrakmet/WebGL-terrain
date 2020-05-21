import javax.imageio.ImageIO;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint("/terrain")
public class TerrainWebSocketServer {
    private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());
    private Heightmap heightmap = new Heightmap();

    @OnOpen
    public void onOpen (Session peer) throws IOException {
        peers.add(peer);
      //  peer.getBasicRemote().sendText(heightmap.getImageName());
    }

    @OnClose
    public void onClose (Session peer) {
        peers.remove(peer);
    }

    @OnMessage
   /* public String onMessage(String message) throws IOException {
        System.out.println(message);
        for(Session session: peers) session.getBasicRemote().sendText(heightmap.getImageName());
        return null;
    }*/

    public void onMessage(String message, Session mSession) throws IOException {
        System.out.println("Message: " + message);
       // if (message.equals("image")) {
            System.out.println("session: " + mSession);
            if (mSession != null) {
             /*   try {
                    File f = new File("C:\\Users\\Alexandra\\Desktop\\Studies\\3\\2\\OOP\\lab11111\\src\\main\\webapp\\image.png");
                    BufferedImage bi = ImageIO.read(f);
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    ImageIO.write(bi, "png", out);
                    ByteBuffer byteBuffer = ByteBuffer.wrap(out.toByteArray());
                    ByteBuffer encode = Base64.getEncoder().encode(byteBuffer);
                    mSession.getBasicRemote().sendBinary(encode);
                    out.close();
                    byteBuffer.clear();
                    encode.clear();

                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                mSession.getBasicRemote().sendText(heightmap.encoder());
            }
       // }
    }

}
