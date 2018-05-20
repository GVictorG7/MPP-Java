package repo;

import domain.Operator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class OperatorJdbcRepo implements IRepository<Operator> {
    private JdbcUtils jdbcUtils;

    public OperatorJdbcRepo(Properties props) {
        jdbcUtils = new JdbcUtils(props);
    }

    @Override
    public int size() {
        Connection con = jdbcUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT COUNT (*) as [SIZE] FROM Operatori")) {
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    return result.getInt("SIZE");
                }
            }
        } catch (SQLException ex) {
            System.out.println("Err DB " + ex);
        }
        return 0;
    }

    @Override
    public void save(Operator operator) {
        Connection con = jdbcUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("INSERT INTO Operatori VALUES (?,?,?)")) {
            preStmt.setInt(1, operator.getId());
            preStmt.setString(2, operator.getName());
            preStmt.setString(3, operator.getPass());
            int res = preStmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Err DB " + ex);
        }
    }

    @Override
    public void delete(int id) {
        Connection con = jdbcUtils.getConnection();
        try (PreparedStatement prep = con.prepareStatement("DELETE FROM Operatori WHERE id=?")) {
            prep.setInt(1, id);
            int res = prep.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("err DB " + ex);
        }
    }

    @Override
    public Operator findOne(int id) {
        Connection con = jdbcUtils.getConnection();
        try (PreparedStatement prep = con.prepareStatement("SELECT * FROM Operatori WHERE id=?")) {
            prep.setInt(1, id);
            try (ResultSet resultSet = prep.executeQuery()) {
                if (resultSet.next()) {
                    return new Operator(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("pass"));
                }
            }
        } catch (SQLException ex) {
            System.out.println("err DB " + ex);
        }
        return null;
    }

    @Override
    public ArrayList<Operator> findAll() {
        Connection con = jdbcUtils.getConnection();
        ArrayList<Operator> operators = new ArrayList<>();
        try (PreparedStatement prep = con.prepareStatement("SELECT * FROM Operatori")) {
            try (ResultSet resultSet = prep.executeQuery()) {
                while (resultSet.next()) {
                    operators.add(new Operator(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("pass")));
                }
            }
        } catch (SQLException ex) {
            System.out.println("err DB" + ex);
        }
        return operators;
    }
}
