import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static Connection conn = null;
    private static PreparedStatement pstmt = null;
    private static Statement stmt = null;

    private static String url = "jdbc:mysql://siyoung.cpnmkdktjh0e.ap-northeast-2.rds.amazonaws.com:3306/mydb?useSSL=false&characterEncoding=UTF-8";
    private static String id = "admin";
    private static String pw = "gksmf1004";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("==========================");
            System.out.print("(0) 종료\n(1) 릴레이션 생성 및 데이터 삽입\n" +
                    "(2) 제목을 이용한 검색\n(3) 관객수를 이용한 검색\n(4) 개봉일을 이용한 검색\n원하는 번호를 입력하시오\n");
            System.out.println("==========================");
            int input = Integer.parseInt(sc.nextLine().trim());
            //TODO 프로그램 종료
            if (input == 0)
                break;
            //TODO 릴레이션 및 데이터 생성
            else if (input == 1) {
                System.out.println("sql문을 통한 데이터삽입은 1번을, 이외의 경우는은 2번을 눌러주세요.");
                //TODO SQL 직접 입력(텍스트파일 복사용)
                if (Integer.parseInt(sc.nextLine().trim()) == 1) {
                    while (true) {
                        System.out.print("입력값 : ");
                        String sql = sc.nextLine();
                        if (sql.equals("0")) {
                            System.out.println("sql문을 통한 데이터삽입은 1번을, 이외의 경우는은 2번을 눌러주세요.");
                            break;
                        } else {
                            createData(sql, "x");
                            System.out.println("입력 성공");
                            System.out.println("릴레이션 생성을 그만 두려면 0을 입력해주세요");
                        }
                    }
                    //TODO 데이터 입력 및 가공을 통한 insert
                } else {
                    while (true) {
                        System.out.println("삽입하고자 하는 데이터를 입력해주세요.");
                        String data = sc.nextLine().trim();
                        if (data.equals("0"))
                            break;
                        else {
                            String[] split = data.split("\\|");
                            System.out.println(Arrays.toString(split));
                            createData("x", data);
                            System.out.println("데이터 삽입을 그만 두려면 0을 입력해주세요.");
                        }
                    }
                }

            }
            //TODO 제목을 이용한 검색
            else if (input == 2) {
                while (true) {
                    System.out.print("사용자 입력 : ");
                    String condition = sc.nextLine().trim();
                    if (condition.equals("0"))
                        break;
                    else
                        selectDataByTitle(condition);
                    System.out.println("\n검색을 그만 하고자 하면 0을 입력해주세요.");
                }

            }
            //TODO 관객수를 이용한 검색
            else if (input == 3) {
                while (true) {
                    System.out.print("사용자 입력 : ");
                    int condition = Integer.parseInt(sc.nextLine());
                    if (condition == 0)
                        break;
                    else
                        selectDataByNumber(condition);
                    System.out.println("\n검색을 그만 하고자 하면 0을 입력해주세요.");
                }
            }
            //TODO 개봉일을 이용한 검색
            else if (input == 4) {
                while (true) {
                    System.out.println("두 날짜를 ,를 기준으로 입력해주세요.");
                    System.out.print("사용자 입력 : ");
                    String condition = sc.nextLine().trim();
                    String[] split = condition.trim().split(",");
                    if (condition.equals("0"))
                        break;
                    else
                        selectDataByDate(split[0], split[1]);
                    System.out.println("\n검색을 그만 하고자 하면 0을 입력해주세요.");
                }
            } else System.out.println("올바른 숫자를 입력해주세요.");
        }
    }

    //TODO 날짜 기준으로 검색
    private static void selectDataByDate(String condition1, String condition2) {

        try {
            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(url, id, pw);
            //DB와 연결된 conn 객체로부터 Statement 객체 획득.
            stmt = conn.createStatement();
            //query 만들기
            StringBuilder sb = new StringBuilder();
            String SQL = sb.append("select * from movie where releasedate between date(")
                    .append("'" + condition1 + "') ")
                    .append("and date(")
                    .append("'" + condition2 + "')+1").toString();
            ResultSet resultSet = stmt.executeQuery(SQL);
            ResultSetMetaData metaData = resultSet.getMetaData();
            while (resultSet.next()) {
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String columnValue = resultSet.getString(i);
                    System.out.print("|" + columnValue);
                }
                System.out.println("");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                //자원 해제
                if (conn != null && !conn.isClosed())
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //TODO 관객수를 기준으로 검색
    private static void selectDataByNumber(int condition) {

        try {
            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(url, id, pw);
            //DB와 연결된 conn 객체로부터 Statement 객체 획득.
            stmt = conn.createStatement();
            //query 만들기
            StringBuilder sb = new StringBuilder();
            String SQL = sb.append("select * from movie where totalnum >")
                    .append(condition)
                    .append("").toString();
            ResultSet resultSet = stmt.executeQuery(SQL);
            ResultSetMetaData metaData = resultSet.getMetaData();
            while (resultSet.next()) {
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String columnValue = resultSet.getString(i);
                    System.out.print("|" + columnValue);
                }
                System.out.println("");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                //자원 해제
                if (conn != null && !conn.isClosed())
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //TODO 제목을 기준으로 선택
    public static void selectDataByTitle(String condition) {

        try {
            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(url, id, pw);
            //DB와 연결된 conn 객체로부터 Statement 객체 획득.
            stmt = conn.createStatement();
            //query 만들기
            StringBuilder sb = new StringBuilder();
            String SQL = sb.append("select * from movie where title like")
                    .append("'%")
                    .append(condition)
                    .append("%'").toString();
            ResultSet resultSet = stmt.executeQuery(SQL);
            ResultSetMetaData metaData = resultSet.getMetaData();
            while (resultSet.next()) {
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String columnValue = resultSet.getString(i);
                    System.out.print("|" + columnValue);
                }
                System.out.println("");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                //자원 해제
                if (conn != null && !conn.isClosed())
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //TODO 릴레이션 및 데이터 삽입
    public static void createData(String sql, String data) {

        try {
            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(url, id, pw);
            //DB와 연결된 conn 객체로부터 Statement 객체 획득.
            stmt = conn.createStatement();
            //query 만들기
            if (data.equals("x")) {
                stmt.execute(sql);
            } else {
                String SQL = "insert into movie values(?, ?, ?, ?, ?,?,?,?,?)";
                String[] split = data.split("\\|");
                pstmt = conn.prepareStatement(SQL);
                for (int i = 1; i < split.length; i++)
                    pstmt.setString(i, split[i]);
                pstmt.executeUpdate();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                //자원 해제
                if (conn != null && !conn.isClosed())
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

