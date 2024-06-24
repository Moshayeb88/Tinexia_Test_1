import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class VulnerableServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        storeUserInfo(username, password);

        String filename = request.getParameter("filename");
        String fileHash = getFileHash(filename);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        if (fileHash != null) {
            out.println("The SHA-256 hash of the file " + filename + " is: " + fileHash);
        }
        out.println("</body></html>");
    }

    private void storeUserInfo(String username, String password) {
        // Insecure storage of user credentials - This is an intentional vulnerability
        try (FileWriter fw = new FileWriter("user_info.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(username + ":" + password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFileHash(String filename) {
        try {
            FileInputStream fis = new FileInputStream(new File(filename));
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] byteArray = new byte[1024];
            int bytesCount = 0;
            while ((bytesCount = fis.read(byteArray)) != -1) {
                digest.update(byteArray, 0, bytesCount);
            }
            fis.close();

            byte[] bytes = digest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            System.out.println("File " + filename + " not found.");
            return null;
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
