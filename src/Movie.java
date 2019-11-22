import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
/*
create table movie (
    id           char(3),
    title        varchar (100),
    company      varchar (50),
    releasedate  date,
    country      varchar (10),
    totalscreen  int,
    profit       numeric (15,2),
    totalnum     int,
    grade        varchar (50),
    primary key (id));
    )
*/

public class Movie {
    public static void main(String[] args){
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
            String sql = sb.append("create table movie (")
                    .append("id varchar(3),")
                    .append("title varchar(100),")
                    .append("company varchar(50),")
                    .append("releasedate date,")
                    .append("country varchar(10),")
                    .append("totalscreen int,")
                    .append("profit numeric(15,2),")
                    .append("totalnum int,")
                    .append("grade varchar(50),")
                    .append("primary key(id)")
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