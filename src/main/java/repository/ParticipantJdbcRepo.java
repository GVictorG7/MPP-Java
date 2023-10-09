package repository;

import model.Participant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ParticipantJdbcRepo implements IRepository<Participant> {
    private final JdbcUtils jdbcUtils;

    public ParticipantJdbcRepo(Properties props) {
        jdbcUtils = new JdbcUtils(props);
    }

    @Override
    public int size() {
        Connection con = jdbcUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT COUNT (*) as [SIZE] FROM Participanti")) {
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

    public int numByCursa(int id) {
        Connection con = jdbcUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("SELECT COUNT (*) as [SIZE] FROM Participanti WHERE idCursa=?")) {
            preStmt.setInt(1, id);
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
    public void save(Participant participant) {
        Connection con = jdbcUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement("INSERT INTO Participanti VALUES (?,?,?,?,?)")) {
            preStmt.setInt(1, participant.getId());
            preStmt.setString(2, participant.getNume());
            preStmt.setString(3, participant.getEchipa());
            preStmt.setInt(4, participant.getCap());
            preStmt.setInt(5, participant.getIdCursa());
            preStmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Err DB " + ex);
        }
    }

    @Override
    public void delete(int id) {
        Connection con = jdbcUtils.getConnection();
        try (PreparedStatement prep = con.prepareStatement("DELETE FROM Participanti WHERE id=?")) {
            prep.setInt(1, id);
            prep.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("err DB " + ex);
        }
    }

    @Override
    public Participant findOne(int id) {
        Connection con = jdbcUtils.getConnection();
        try (PreparedStatement prep = con.prepareStatement("SELECT * FROM Participanti WHERE id=?")) {
            prep.setInt(1, id);
            try (ResultSet resultSet = prep.executeQuery()) {
                if (resultSet.next()) {
                    return new Participant(resultSet.getInt("id"), resultSet.getString("nume"), resultSet.getString("echipa"), resultSet.getInt("cap"), resultSet.getInt("idCursa"));
                }
            }
        } catch (SQLException ex) {
            System.out.println("err DB " + ex);
        }
        return null;
    }

    @Override
    public ArrayList<Participant> findAll() {
        Connection con = jdbcUtils.getConnection();
        ArrayList<Participant> operators = new ArrayList<>();
        try (PreparedStatement prep = con.prepareStatement("SELECT * FROM Participanti")) {
            try (ResultSet resultSet = prep.executeQuery()) {
                while (resultSet.next()) {
                    operators.add(new Participant(resultSet.getInt("id"), resultSet.getString("nume"), resultSet.getString("echipa"), resultSet.getInt("cap"), resultSet.getInt("idCursa")));
                }
            }
        } catch (SQLException ex) {
            System.out.println("err DB" + ex);
        }
        return operators;
    }

    public List<Participant> getParticipantiByEchipa(String echipa) {
        Connection con = jdbcUtils.getConnection();
        ArrayList<Participant> operators = new ArrayList<>();
        try (PreparedStatement prep = con.prepareStatement("SELECT * FROM Participanti WHERE echipa=?")) {
            prep.setString(1, echipa);
            try (ResultSet resultSet = prep.executeQuery()) {
                while (resultSet.next()) {
                    operators.add(new Participant(resultSet.getInt("id"), resultSet.getString("nume"), resultSet.getString("echipa"), resultSet.getInt("cap"), resultSet.getInt("idCursa")));
                }
            }
        } catch (SQLException ex) {
            System.out.println("err DB" + ex);
        }
        return operators;
    }
}
