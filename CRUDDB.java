
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CRUDDB {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/app_musica";
    private static final String DB_USER = "root"; 
    private static final String DB_PASSWORD = ""; 

    
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    
    public void inserirMusica(String nome, String cantor) throws SQLException {
        String sql = "INSERT INTO musicas (nome, cantor) VALUES (?, ?)";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, cantor);
            stmt.executeUpdate();
        }
    }

    
    public boolean excluirMusica(int id) throws SQLException {
        String sql = "DELETE FROM musicas WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }

    
    public List<String> listarMusicas() throws SQLException {
        String sql = "SELECT * FROM musicas";
        List<String> lista = new ArrayList<>();
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String cantor = rs.getString("cantor");
                lista.add(id + ". " + nome + " - " + cantor);
            }
        }
        return lista;
    }

    
    public boolean alterarMusica(int id, String novoNome, String novoCantor) throws SQLException {
        String sql = "UPDATE musicas SET nome = ?, cantor = ? WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novoNome);
            stmt.setString(2, novoCantor);
            stmt.setInt(3, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
}