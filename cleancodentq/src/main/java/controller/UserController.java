package controller;

import view.View;

import java.sql.*;
import java.util.Scanner;

public class UserController {
    final String URL = "jdbc:mysql://localhost:3306/myuser";
    final String USERNAME = "root";
    final String PASSWORD = "123456";

    Scanner sc = new Scanner(System.in);

    public Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void editUser(){
        String sql = "update user set username = ?, email = ?, isLocked = ? where id = ?";
        String sqlFindOne = "select * from user where id = ?";
        Connection conn = getConnection();
        PreparedStatement ps =  null;
        try {
            ps = conn.prepareStatement(sqlFindOne);
            System.out.print("typing your id: ");
            int id = Integer.parseInt(sc.nextLine());
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                ps = conn.prepareStatement(sql);
                System.out.print("typing new username: ");
                String username = sc.nextLine();
                ps.setString(1, username);
                System.out.print("typing new email: ");
                String email = sc.nextLine();
                ps.setString(2, email);
                System.out.print("typing status: ");
                int status = Integer.parseInt(sc.nextLine());
                while (status != 0 && status != 1){
                    System.out.println("status 0 is account locked, status 1 is account available");
                    System.out.print("typing status: ");
                    status = Integer.parseInt(sc.nextLine());
                }
                ps.setInt(3, status);
                ps.setInt(4, id);
                ps.execute();
                System.out.println("updated");
                View.main(new String[1]);
            }else {
                System.out.println("account not exists");
                View.main(new String[1]);
            }
            View.main(new String[1]);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(ps != null){
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void deleteUser(){
        String sql = "update user set isLocked = ? where id = ?";
        Connection conn = getConnection();
        PreparedStatement ps =  null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, 0);
            System.out.print("typing id: ");
            String id = sc.nextLine();
            ps.setString(2, id);
            int number = ps.executeUpdate();
            if(number != 0){
                System.out.println("deleted");
            }else {
                System.out.println("user not found");
            }
            View.main(new String[1]);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(ps != null){
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void listUser(){
        String sql = "select * from user";
        Connection conn = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                System.out.println("| id:     "+rs.getInt("id"));
                System.out.println("| name:   "+rs.getString("username"));
                System.out.println("| email:  "+rs.getString("email"));
                System.out.println("| status: "+rs.getInt("isLocked"));
                System.out.println("-------------------------------------------");

            }

        }catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if(rs != null){
                    rs.close();
                }
                if(ps != null){
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void createUser() {
        String sql = "insert into user(username, email, isLocked) values (?,?,?)";
        Connection conn = getConnection();
        PreparedStatement ps =  null;
        try {
            ps = conn.prepareStatement(sql);
            System.out.print("typing username: ");
            String name = sc.nextLine();
            ps.setString(1, name);
            System.out.print("typing email: ");
            String email = sc.nextLine();
            ps.setString(2, email);
            ps.setInt(3, 1);
            ps.execute();
            System.out.println("created!");
        }
        catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Email already exists");
            View.main(new String[1]);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(ps != null){
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
