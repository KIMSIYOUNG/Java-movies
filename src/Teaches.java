import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Teaches {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;

        String url = "jdbc:mysql://siyoung.cpnmkdktjh0e.ap-northeast-2.rds.amazonaws.com:3306/mydb?useSSL=false&characterEncoding=UTF-8";
        String id = "admin";
        String pw = "gksmf1004";

        try{
            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(url, id, pw);

            System.out.println("Successfully Connected!");

            //DB와 연결된 conn 객체로부터 Statement 객체 획득.
            stmt = conn.createStatement();

            //query 만들기
            StringBuilder sb = new StringBuilder();
            String sql = sb.append("create table teaches (")
                    .append("ID varchar(5),")
                    .append("course_id varchar(8),")
                    .append("sec_id varchar(8),")
                    .append("semester varchar(6),")
                    .append("year numeric(4,0),")
                    .append("primary key(ID,course_id,sec_id,semester,year)")
                    .append(");").toString();

            //query문 날리기
            stmt.execute(sql);
        }

        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            try{
                //자원 해제
                if(conn != null && !conn.isClosed())
                    conn.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

}
