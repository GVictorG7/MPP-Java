package repo;

import domain.Cursa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CurseJdbcRepo implements IRepository<Cursa> {
    private JdbcUtils jdbcUtils;

    public CurseJdbcRepo(Properties properties) {
        jdbcUtils = new JdbcUtils(properties);
    }

    @Override
    public int size() {
        Connection con = jdbcUtils.getConnection();
        try (PreparedStatement prep = con.prepareStatement("SELECT COUNT (*) as [SIZE] FROM Curse")) {
            try (ResultSet resultSet = prep.executeQuery()) {
                if (resultSet.next()) return resultSet.getInt("SIZE");
            }
        } catch (SQLException ex) {
            System.out.println("err db" + ex);
        }
        return 0;
    }

    @Override
    public void save(Cursa cursa) {
        Connection con = jdbcUtils.getConnection();
        try (PreparedStatement prep = con.prepareStatement("INSERT INTO Curse VALUES (?,?,?)")) {
            prep.setInt(1, cursa.getId());
            prep.setInt(2, cursa.getCap());
            prep.setInt(3, cursa.getNrPart());
            int res = prep.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("err db" + ex);
        }
    }

    @Override
    public void delete(int id) {
        Connection con = jdbcUtils.getConnection();
        try (PreparedStatement prep = con.prepareStatement("DELETE FROM Curse WHERE id=?")) {
            prep.setInt(1, id);
            int res = prep.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("err db" + ex);
        }
    }

    @Override
    public Cursa findOne(int id) {
        Connection con = jdbcUtils.getConnection();
        try (PreparedStatement prep = con.prepareStatement("SELECT * FROM Curse WHERE id=?")) {
            prep.setInt(1, id);
            try (ResultSet res = prep.executeQuery()) {
                if (res.next()) return new Cursa(res.getInt("id"), res.getInt("cap"), res.getInt("nrPart"));
            }
        } catch (SQLException ex) {
            System.out.println("err db" + ex);
        }
        return null;
    }

    @Override
    public List<Cursa> findAll() {
        Connection con = jdbcUtils.getConnection();
        ArrayList<Cursa> curse = new ArrayList<>();
        try (PreparedStatement prep = con.prepareStatement("SELECT * FROM Curse")) {
            try (ResultSet res = prep.executeQuery()) {
                while (res.next()) {
                    //System.out.println(res.toString());
                    curse.add(new Cursa(res.getInt("id"), res.getInt("cap"), res.getInt("nrPart")));
                }
            }
        } catch (SQLException ex) {
            System.out.println("err db" + ex);
        }
        return curse;
    }

    public ArrayList<Cursa> getCurseByCap(int cap) {
        ArrayList<Cursa> curse = new ArrayList<>();
        Connection con = jdbcUtils.getConnection();
        try (PreparedStatement prep = con.prepareStatement("SELECT * FROM Curse WHERE cap=>")) {
            prep.setInt(1, cap);
            try (ResultSet res = prep.executeQuery()) {
                while (res.next()) {
                    curse.add(new Cursa(res.getInt("id"), res.getInt("cap"), res.getInt("nrPart")));
                }
            }
        } catch (SQLException ex) {
            System.out.println("err db" + ex);
        }
        return curse;
    }
}
